package com.aplc.dotarthstone;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

public class Game
{
	private boolean isLeftTurn = true;
	private boolean waitingForTurn = false;
	private Hero left;
	private Hero right;

	private final String email;
	private final String password;
	private Properties propertiesSend;
	private Properties propertiesReceive;

	private int currentTurn = 0;
	
	private Hashtable<String, String> emailCommands;
	private Store readStore = null;
	private Session sendSession = null;
	private boolean textIsHtml = false;

	public Game(final String email, final String password) 
	{
		this.email = email;
		this.password = password;
		
		emailCommands = new Hashtable<String, String>();
		propertiesSend = new Properties();
		propertiesSend.put("mail.smtp.host", "smtp.gmail.com");
		propertiesSend.put("mail.smtp.socketFactory.port", "465");
		propertiesSend.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		propertiesSend.put("mail.smtp.auth", "true");
		propertiesSend.put("mail.smtp.port", "465");
		
		propertiesReceive = new Properties();
		propertiesReceive.put("mail.store.protocol", "imaps");
	}

	public void sendEmail(String emailTo, String subject, String messageText)
	{
		System.out.println("sending mail");
		
		if (sendSession == null)
		{
			sendSession = Session.getDefaultInstance(propertiesSend,
					new Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(email, password);
						}
			});
		}
		
		
		try 
		{
			Message message = new MimeMessage(sendSession);
			message.setFrom(new InternetAddress(email));
			message.setRecipients(Message.RecipientType.TO
				, InternetAddress.parse(emailTo));
			message.setSubject(subject);
			message.setText(messageText);
			
			Transport.send(message);
			
		} 
		catch (MessagingException e) 
		{
			throw new RuntimeException(e);
		}
	}
	
	private Message[] readEmail()
	{
		Session session = Session.getInstance(propertiesReceive, null);
		Message[] messages = null;
		
		try
		{
			if (readStore == null || !readStore.isConnected())
			{
				readStore = session.getStore();
				readStore.connect("imap.gmail.com", email, password);
			}
			
			Folder folder = readStore.getFolder("INBOX");
			folder.open(Folder.READ_WRITE);
			
			messages = folder.getMessages();
		}
		catch (MessagingException e)
		{
			e.printStackTrace();
		}
		
		return messages;	
	}
	
	//from http://www.oracle.com/technetwork/java/javamail/faq/index.html#mainbody
	private String getText(Part p) throws MessagingException, IOException
	{
		if (p.isMimeType("text/*")) {
            String s = (String)p.getContent();
            textIsHtml = p.isMimeType("text/html");
            return s;
        }

        if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart)p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                	String s = getText(bp);
                	if (s != null)
                		return s;
                } else if (bp.isMimeType("text/html")) {
                    continue;
                } else {
                    return getText(bp);
                }
            }
            return text;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart)p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i));
                if (s != null)
                    return s;
            }
        }

        return null;
	}
	
	private String getEmailFromSender(Address addressOther)
	{
		String address = addressOther.toString();
		
		int beginIndex = address.indexOf("<") + 1;
		int endIndex = address.length() - 1;
		return address.substring(beginIndex, endIndex);
	}
	
	//this should be the big dog
	//come up with actions and verify if actions are valid
	@SuppressWarnings("null")
	public void parseEmail() 
	{
		Message[] messages = readEmail();
		for (Message e : messages)
		{
			try 
			{
				//assume all input is sane. school project, so I AINT GOT TIME FO DAT
				if (((String)(e.getSubject())).equals("leftHero"))
				{
					String sender = getEmailFromSender(e.getFrom()[0]);
					String leftEmail = emailCommands.get("register").split(" ")[0];
					System.out.println("Sender: " + sender + " Email:" + leftEmail);
					
					if (!(sender.equals(leftEmail)))
					{
						System.out.println(e.getSubject());
						System.out.println(sender + " trying to register as " + leftEmail);
						e.setFlag(Flags.Flag.DELETED, true);
						continue;
					}
				}
				if (((String)(e.getSubject())).equals("rightHero"))
				{
					String sender = getEmailFromSender(e.getFrom()[0]);
					String rightEmail = emailCommands.get("register").split(" ")[1];
					System.out.println("Sender: " + sender + " Email:" + rightEmail);
					
					if (!(sender.equals(rightEmail)))
					{
						System.out.println(e.getSubject());
						System.out.println(sender + " trying to register as " + rightEmail);
						e.setFlag(Flags.Flag.DELETED, true);
						continue;
					}
				}
					
				
				emailCommands.put((String)(e.getSubject()).toLowerCase(), (String)(getText(e)));
				e.setFlag(Flags.Flag.DELETED, true);
				
				readStore.close();
			}
			catch (Exception except) 
			{
				except.printStackTrace();
			}
			
		}
	}

	public void startGame() 
	{
		left.awardCards(3);	//left is always first
		right.awardCards(4);
		right.awardCards("Coin");
		
		System.out.println("Starting game with heroes: " 
						+ left.getName() + " and "
						+ right.getName() + ".");
		
		currentTurn++;
	}

	public void runGame() 
	{
		parseEmail();
		
		if (emailCommands.containsKey("register") 
		 && emailCommands.containsKey("leftHero")
		 && emailCommands.containsKey("rightHero"))
		{
			String[] emails = emailCommands.get("register").split(" ");
			String leftHeroChoice = emailCommands.get("leftHero");
			String rightHeroChoice = emailCommands.get("rightHero");
			
			if (leftHeroChoice.equalsIgnoreCase("witchDoctor"))
				left = new WitchDoctor();
			else if (leftHeroChoice.equalsIgnoreCase("cleric"))
				left = new Cleric();
			else if (leftHeroChoice.equalsIgnoreCase("warrior"))
				left = new Warrior();
			left.email = emails[0];
			
			if (rightHeroChoice.equalsIgnoreCase("witchDoctor"))
				right = new WitchDoctor();
			else if (rightHeroChoice.equalsIgnoreCase("cleric"))
				right = new Cleric();
			else if (rightHeroChoice.equalsIgnoreCase("warrior"))
				right = new Warrior();
			right.email = emails[1];
			
			startGame();
			
			emailCommands.remove("register");
			emailCommands.remove("leftHero");
			emailCommands.remove("rightHero");
			return;
		}
		else if (currentTurn == 0)
		{
			//this means we dont have a register or hero pick
			//and that we havent started game yet, so we wait
			System.out.println(emailCommands.get("register"));
			System.out.println("Waiting");
			return;
		}

		System.out.println("before isleftturn");
		
		if (isLeftTurn)
		{
			if (!waitingForTurn)
			{
				left.awardCards(1);
				waitingForTurn = true;
			}
				
			if (emailCommands.contains("leftActions"))
			{
				//Action[] actions = ssdfdsf;
				//left.doAction(actions);
				waitingForTurn = false;
				emailCommands.remove("leftActions");
				isLeftTurn = false;
			}
			else
			{
				System.out.println("Waiting for left");
				return;
			}
		}
		else
		{
			if (!waitingForTurn)
			{
				right.awardCards(1);
				waitingForTurn = true;
			}
			if (emailCommands.contains("rightActions"))
			{
				//Action[] actions = ssdfdsf;
				//left.doAction(actions);
				waitingForTurn = false;
				emailCommands.remove("rightActions");
				isLeftTurn = true;
			}
			else
			{
				System.out.println("Waiting for right");
				return;
			}
		}

		System.out.println("before incrementturn");
		
		if (isLeftTurn)
			currentTurn++;
	}
}