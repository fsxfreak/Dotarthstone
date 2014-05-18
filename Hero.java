package com.aplc.dotarthstone;

import java.util.ArrayList;

public abstract class Hero extends Character
{
	public String email;

	protected int armor;
	protected int mana;
	protected ArrayList<Card> cardsInHand;
	protected ArrayList<Card> deck;

	protected boolean isLeftSide = false;
	
	public boolean endTurn = false;
	
	public Hero(String name, boolean leftSide)
	{
		super(name, 30, 0);

		this.mana = 1;
		this.isLeftSide = leftSide;
		
		deck = new ArrayList<Card>();
		cardsInHand = new ArrayList<Card>();
	}

	protected abstract void constructDeck();
	protected abstract void heroPower(Character target);
	protected void addArmor(int amount) { armor += amount; }
	
	protected void useCard(String card) 
	{
		boolean haveCard = false;
		for (Card e : cardsInHand)
		{
			if (e.getName().equals(card))
			{
				Board.addCard(e, isLeftSide);
			}
		}
	}

	public void awardCards(int numCards)
	{
		for (int i = 0; i < numCards; i++)
		{
			int cardIndex = (int)(Math.random() * deck.size());
			cardsInHand.add(deck.get(cardIndex));
			deck.remove(deck.get(cardIndex));
		}
	}

	public void awardCards(String cardName)
	{
		//TODO check correctness
		if (cardName.equals("Coin"))
		{
			cardsInHand.add(new Card("Coin", 0, 0, 0));
		}

		for (Card e : deck)
		{
			if (e.getName().equals(cardName))
			{
				cardsInHand.add(e);
				deck.remove(e);
			}
		}
	}

	public void doAction(ArrayList<Action> actions) 
	{
		for (Action act : actions)
		{
			System.out.println(act.functionName);
			
			if (act.functionName.equals("playcard"))
			{
				System.out.println("Playing card: " + act.args[0]);
				useCard(act.args[0]);
			}
			else if (act.functionName.equals("hurt"))
			{
				System.out.println("Damaging: " + act.args[1] + " with " + act.args[0]);
				
				String originName = act.args[0];
				String target = act.args[1];
				
				ArrayList<Card> boardCards = Board.getCards(isLeftSide);
				for (Card e : boardCards)
				{
					if (e.getName().equals(originName))
					{
						//don't worry, my eye is twitching too
						ArrayList<Card> otherCards = Board.getCards(!isLeftSide);
						for (Card f : otherCards)
						{
							if (f.getName().equals(target))
							{
								f.hurt(e.getDamage(), e);
							}
						}
					}
				}
			}
			else if (act.functionName.equals("heropower"))
			{
				System.out.println("hero powering " + act.args[0]);
				
				ArrayList<Character> everything = Board.getEverything();
				for (Character e : everything)
				{
					if (e.getName().equals(act.args[0]))
					{
						heroPower(e);
					}
				}
			}
			else if (act.functionName.equals("end"))
			{
				endTurn = true;
				System.out.println("Ending turn.");
			}
		}
	}
	
	public void setMana(int amount)
	{
		mana = amount;
		if (mana > 10)
			mana = 10;
	}
	
	public int getMana()
	{
		return mana;
	}
	
	public ArrayList<Card> getHand()
	{
		return cardsInHand;
	}
}