package com.aplc.dotarthstone;

import java.util.ArrayList;

public class Board
{
	//0th index contains the hero that owns the cards on the board
	public static ArrayList<Character> left;
	public static ArrayList<Character> right;

	@SuppressWarnings("unchecked")
	public static ArrayList<Card> getCards(Hero forHero)
	{
		ArrayList<Card> cards = null;

		if (left.get(0).equals(forHero))
		{
			cards = (ArrayList<Card>)left.clone();
			cards.remove(0);
		}
		else if (right.get(0).equals(forHero))
		{
			cards = (ArrayList<Card>)right.clone();
			cards.remove(0);
		}

		return cards;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Card> getCards(boolean forLeftSide)
	{
		ArrayList<Card> cards = null;
		
		if (forLeftSide)
		{
			cards = (ArrayList<Card>)left.clone();
			cards.remove(0);
		}
		else
		{
			cards = (ArrayList<Card>)right.clone();
			cards.remove(0);
		}
		return cards;
	}

	public static ArrayList<Card> getAllCards()
	{
		ArrayList<Card> leftCards = getCards((Hero)left.get(0));
		ArrayList<Card> rightCards = getCards((Hero)right.get(0));

		leftCards.addAll(rightCards);

		return leftCards;
	}
	
	public static ArrayList<Character> getEverything()
	{
		ArrayList<Character> everything = new ArrayList<Character>();
		everything.addAll(left);
		everything.addAll(right);
		
		return everything;
	}

	public static void remove(Character character)
	{
		if (character.equals(left.get(0)))
		{
			//TODO GAME OVER
		}
		else if (character.equals(right.get(0)))
		{
			//TODO GAME OVER
		}

		left.remove(character);
		right.remove(character);
	}
	
	public static void addCard(Card card, boolean forSide)
	{
		if (forSide)
			left.add(card);
		else
			right.add(card);
	}
}