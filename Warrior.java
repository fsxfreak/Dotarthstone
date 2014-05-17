package com.aplc.dotarthstone;

import java.util.ArrayList;

public class Warrior extends Hero
{
    public static ArrayList<Card> warriorCards;

    public Warrior()
    {
        super("Warrior");
        constructDeck();
    }

    protected void constructDeck()
    {
        if (warriorCards == null || warriorCards.size() <= 0)
            buildCards();
        if (NeutralCards.cards == null || NeutralCards.cards.size() <= 0)
            NeutralCards.buildCards();

        ArrayList<Card> pool = new ArrayList<Card>();
        pool.addAll(warriorCards);
        pool.addAll(NeutralCards.cards);

        int max = pool.size();
        for (int i = 0; i < 25; i++)
        {
            int randIndex = (int)(Math.random() * max);
            deck.add(pool.get(randIndex));
        }
    }

    protected void heroPower(Character target)
    {
        target.hurt(2, this);
        hurt(target.getDamage(), target);
    }

    public static void buildCards()
    {
        warriorCards = new ArrayList<Card>();
        warriorCards.add(new Card("Berserker",  3, 7, 5));
        //  warriorCards.get(cards.size() - 1).addEffect((new Effect()).setEffect(Effect.ON_DAMAGED));
        warriorCards.add(new Card("Taskmaster", 2, 2, 3));
        //  warriorCards.get(cards.size() - 1).addEffect((new Effect()).setEffect(Effect.ON_ATTACK));
    }
}