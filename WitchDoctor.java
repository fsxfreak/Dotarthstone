package com.aplc.dotarthstone;

import java.util.ArrayList;

public class WitchDoctor extends Hero
{
    public static ArrayList<Card> witchDoctorCards;

    public WitchDoctor(boolean leftSide)
    {
        super("witchdoctor", leftSide);
        constructDeck();
    }

    protected void constructDeck()
    {
        if (witchDoctorCards == null || witchDoctorCards.size() <= 0)
            buildCards();
        if (NeutralCards.cards == null || NeutralCards.cards.size() <= 0)
            NeutralCards.buildCards();

        ArrayList<Card> pool = new ArrayList<Card>();
        pool.addAll(witchDoctorCards);
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
        ArrayList<Card> myCards = Board.getCards(isLeftSide);
        ArrayList<Card> otherCards = Board.getCards(!isLeftSide);
        if (myCards.contains(target))
        {
            target.heal(1);
        }
        else if (otherCards.contains(target))
        {
            target.hurt(1, this);
        }
    }

    public static void buildCards()
    {
        witchDoctorCards = new ArrayList<Card>();
        //TODO add spells
    }
}