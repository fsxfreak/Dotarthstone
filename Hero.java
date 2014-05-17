package com.aplc.dotarthstone;

import java.util.ArrayList;

public abstract class Hero extends Character
{
	public String email;

	protected int armor;
	protected int mana;
	protected ArrayList<Card> cardsInHand;
	protected ArrayList<Card> deck;

	public Hero(String name)
	{
		super(name, 30, 0);

		this.mana = 1;
		deck = new ArrayList<Card>();
		cardsInHand = new ArrayList<Card>();
	}

	protected abstract void constructDeck();
	protected abstract void heroPower(Character target);
	protected void addArmor(int amount) { armor += amount; }
	protected void useCard(Card card) {}

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

	public void doAction(Action[] actions) 
	{
		for (Action act : actions)
		{
			//parse actions, call appropriate functions with their parameters
		}
	}
	
	public void giveMana(int amount)
	{
		mana += amount;
		if (mana > 10)
			mana = 10;
	}
}