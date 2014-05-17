package com.aplc.dotarthstone;

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
	private Properties properties;

	private int currentTurn = 0;
	
	private Hashtable<String, String> emailCommands;

	public Game(final String email, final String password) 
	{
		this.email = email;
		this.password = password;
		
		emailCommands = new Hashtable<String, String>();
		properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
	}

	public void sendEmail(String emailTo, String subject, String messageText)
	{
		System.out.println("sending mail");
		
		Session mailSession = Session.getInstance(properties,
				new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(email, password);
					}
		});
		
		try 
		{
			Message message = new MimeMessage(mailSession);
			message.setFrom(new InternetAddress(email));
			message.setRecipients(Message.RecipientType.TO
				, InternetAddress.parse(emailTo));
			message.setSubject(subject);
			message.setText(messageText);
			
		} 
		catch (MessagingException e) 
		{
			throw new RuntimeException(e);
		}
	}
	
	private String[] readEmail()
	{
		return null;
	}
	
	//this should be the big dog
	//come up with actions and verify if actions are valid
	public void parseEmail() 
	{
		
		
		//if (parse == register)
		//{
			String email1 = "leonjcheung";
			String email2 = "andrw_pollack";
			
			emailCommands.put("register", email1 + " " + email2);
		//}
		//else if (parse == leftHero)
		//{
			String hero = "witchDoctor";
			emailCommands.put("leftHero", hero);
		//}
		//else if (parse == rightHero)
		//{
			/*String*/ hero = "cleric";
			emailCommands.put("rightHero", hero);	
		//}
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