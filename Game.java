package com.aplc.dotarthstone;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
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
	public void parseEmail() 
	{
		Message[] messages = readEmail();
		for (Message e : messages)
		{
			try 
			{
				String sender = getEmailFromSender(e.getFrom()[0]);
				String leftEmail = null;
				String rightEmail = null;
				if (emailCommands.get("register") != null)
				{
					leftEmail = emailCommands.get("register").split(" ")[0].replaceAll("\\s+", "");
					rightEmail = emailCommands.get("register").split(" ")[1].replaceAll("\\s+", "");
				}

				//assume all input is sane. school project, so I AINT GOT TIME FO DAT
				if (emailCommands.get("register") != null)
				{
					if (((String)(e.getSubject())).contains("left"))
					{
						if (!(sender.equals(leftEmail)))
						{
							e.setFlag(Flags.Flag.DELETED, true);
							continue;
						}
					}
					if (((String)(e.getSubject())).contains("right"))
					{
						if (!(sender.equals(rightEmail)))
						{
							e.setFlag(Flags.Flag.DELETED, true);
							continue;
						}
					}
				}
				
				emailCommands.put((String)(e.getSubject()).toLowerCase().replaceAll("\n","")
								, (String)(getText(e)).replaceAll("\n",""));
				e.setFlag(Flags.Flag.DELETED, true);
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
		
		Board.left = new ArrayList<Character>();
		Board.left.add(left);
		Board.right = new ArrayList<Character>();
		Board.right.add(right);
		
		System.out.println("Starting game with heroes: " 
						+ left.getName() + " and "
						+ right.getName() + ".");
		
		currentTurn++;
	}

	public void runGame() 
	{
		parseEmail();
		try {
			readStore.close();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		if (emailCommands.containsKey("register") 
		 && emailCommands.containsKey("lefthero")
		 && emailCommands.containsKey("righthero"))
		{
			String[] emails = emailCommands.get("register").split(" ");
			String leftHeroChoice = emailCommands.get("lefthero").replaceAll("\\s++", "");
			String rightHeroChoice = emailCommands.get("righthero").replaceAll("\\s++", "");
			
			if (leftHeroChoice.equalsIgnoreCase("witchdoctor"))
				left = new WitchDoctor(true);
			else if (leftHeroChoice.equalsIgnoreCase("cleric"))
				left = new Cleric(true);
			else if (leftHeroChoice.equalsIgnoreCase("warrior"))
				left = new Warrior(true);
			left.email = emails[0];
			
			if (rightHeroChoice.equalsIgnoreCase("witchdoctor"))
				right = new WitchDoctor(false);
			else if (rightHeroChoice.equalsIgnoreCase("cleric"))
				right = new Cleric(false);
			else if (rightHeroChoice.equalsIgnoreCase("warrior"))
				right = new Warrior(false);
			right.email = emails[1];
			
			startGame();
			
			emailCommands.remove("register");
			emailCommands.remove("lefthero");
			emailCommands.remove("righthero");
			return;
		}
		else if (currentTurn == 0)
		{
			//this means we dont have a register or hero pick
			//and that we havent started game yet, so we wait
			System.out.println(emailCommands.get("register"));
			System.out.println(emailCommands.get("lefthero"));
			System.out.println(emailCommands.get("righthero"));
			return;
		}

		if (isLeftTurn)
		{
			if (!waitingForTurn)
			{
				left.awardCards(1);
				left.setMana(currentTurn);
				waitingForTurn = true;
				
				String message = constructMessage();
				
				sendEmail(left.email, "dotarthstone - Your Turn",  message);
			}
				
			if (emailCommands.containsKey("leftactions"))
			{
				ArrayList<Action> actions = buildActions(emailCommands.get("leftactions").split(" "));
				
				left.doAction(actions);
				emailCommands.remove("leftactions");
			}
			else if (left.endTurn)
			{
				waitingForTurn = false;
				isLeftTurn = false;
				left.endTurn = false;
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
				right.setMana(currentTurn);
				waitingForTurn = true;
				
				String message = constructMessage();
				
				sendEmail(right.email, "dotarthstone - Your Turn",  message);
			}
			
			if (emailCommands.containsKey("rightactions"))
			{
				ArrayList<Action> actions = buildActions(emailCommands.get("rightactions").split(" "));
				
				right.doAction(actions);
				emailCommands.remove("rightactions");
			}
			else if (right.endTurn)
			{
				waitingForTurn = false;
				isLeftTurn = true;
				right.endTurn = false;
			}
			else
			{
				System.out.println("Waiting for right");
				return;
			}
		}
		
		if (isLeftTurn)
			currentTurn++;
	}
	
	private ArrayList<Action> buildActions(String[] acts)
	{
		ArrayList<Action> actions = new ArrayList<Action>();
		
		for (String e : acts)
		{
			System.out.println(e);
		}
		
		for (int i = 0; i < acts.length; i++)
		{
			if (acts[i].replaceAll("\\s+", "").equals("playcard"))
			{
				actions.add(new Action("playcard", new String[] { acts[i + 1] }));
			}
			else if (acts[i].replaceAll("\\s+", "").equals("hurt"))
			{
				actions.add(new Action("hurt", new String[] { acts[i + 1], acts[i + 2] }));
			}
			else if (acts[i].replaceAll("\\s+", "").equals("heropower"))
			{
				actions.add(new Action("heropower", new String[] { acts[i + 1] }));
			}
			else if (acts[i].replaceAll("\\s+", "").equals("end"))
			{
				actions.add(new Action("end", new String[] {}));
				System.out.println("adding end");
			}
		}
		
		return actions;
	}
	
	private String constructMessage()
	{
		String message = "Your hand: ";
		
		ArrayList<Card> cards = null;
		if (isLeftTurn)
			cards = left.getHand();
		else
			cards = right.getHand();
		
		for (Card e : cards)
		{
			message += e.getCardInfo() + ", ";
		}
		
		if (isLeftTurn)
			message += "\nMana: " + left.getMana();
		else
			message += "\nMana: " + right.getMana();
		
		message += "\nYour side of the board: ";
		ArrayList<Card> yourCards = null;
		if (isLeftTurn)
			yourCards = Board.getCards(left);
		else
			yourCards = Board.getCards(right);
		for (Card e : yourCards)
		{
			message += e.getCardInfo() + ", ";
		}
		
		message += "\nTheir side of the board: ";
		ArrayList<Card> otherCards = null;
		if (isLeftTurn)
			otherCards = Board.getCards(right);
		else
			otherCards = Board.getCards(left);
		for (Card e : otherCards)
		{
			message += e.getCardInfo() + ", ";
		}
		
		return message;
	}
}