package com.aplc.dotarthstone;

import java.util.ArrayList;

public class Cleric extends Hero
{
    public static ArrayList<Card> clericCards;

    public Cleric(boolean leftSide)
    {
        super("Cleric", leftSide);
        constructDeck();
    }

    protected void constructDeck()
    {
        if (clericCards == null || clericCards.size() <= 0)
            buildCards();
        if (NeutralCards.cards == null || NeutralCards.cards.size() <= 0)
            NeutralCards.buildCards();

        ArrayList<Card> pool = new ArrayList<Card>();
        pool.addAll(clericCards);
        pool.addAll(NeutralCards.cards);
        
        int max = pool.size();
        deck = new ArrayList<Card>();
        for (int i = 0; i < 25; i++)
        {
            int randIndex = (int)(Math.random() * max);
            deck.add(pool.get(randIndex));
        }
    }

    protected void heroPower(Character target)
    {
        target.heal(2);
    }

    public static void buildCards()
    {
        clericCards = new ArrayList<Card>();
        //TODO add spells
    }
}