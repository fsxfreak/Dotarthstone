package com.aplc.dotarthstone;

import java.util.ArrayList;

public class NeutralCards
{
    public static ArrayList<Card> cards;

    public static void buildCards()
    {
        cards = new ArrayList<Card>();

        cards.add(new Card("Grunt",             1, 1, 0));
        cards.add(new Card("Shieldbearer",      1, 1, 1));
        //  cards.get(cards.size() - 1).addEffect((new Effect()).setEffect(Effect.ON_DAMAGED));
        cards.add(new Card("Archer",            1, 2, 1));
        //  cards.get(cards.size() - 1).addEffect((new Effect()).setEffect(Effect.ON_SUMMON));
        cards.add(new Card("Doctor",            1, 2, 1));
        //  cards.get(cards.size() - 1).addEffect((new Effect()).setEffect(Effect.ON_SUMMON));
        cards.add(new Card("Boar",              2, 3, 2));
        cards.add(new Card("Cat",               3, 2, 2));
        cards.add(new Card("Dagger Juggler",    2, 3, 2));
        //  cards.get(cards.size() - 1).addEffect((new Effect()).setEffect(Effect.ON_SUMMON_MINION));
        cards.add(new Card("Hunter",            1, 2, 2));
        //  cards.get(cards.size() - 1).addEffect((new Effect()).setEffect(Effect.ON_SUMMON));
        cards.add(new Card("Greater Priest",    3, 3, 3));
        //  cards.get(cards.size() - 1).addEffect((new Effect()).setEffect(Effect.ON_SUMMON));
        cards.add(new Card("Rifleman",          2, 2, 3));
        //  cards.get(cards.size() - 1).addEffect((new Effect()).setEffect(Effect.ON_SUMMON));
        cards.add(new Card("Leader",            2, 2, 3));
        //  cards.get(cards.size() - 1).addEffect((new Effect()).setEffect(Effect.ON_SUMMON));
        cards.add(new Card("Crusader",          3, 1, 3));
        //  cards.get(cards.size() - 1).addEffect((new Effect()).setEffect(Effect.ON_DAMAGED));
        cards.add(new Card("Abominable Snowman",4, 5, 4));
        cards.add(new Card("A-Bomb",            4, 4, 4));
        //  cards.get(cards.size() - 1).addEffect((new Effect()).setEffect(Effect.ON_DAMAGED));
        cards.add(new Card("Turtle",            2, 7, 4));
        cards.add(new Card("Commando",          4, 2, 4));
        //  cards.get(cards.size() - 1).addEffect((new Effect()).setEffect(Effect.ON_SUMMON));
        cards.add(new Card("Knight",            4, 4, 4));
        //  cards.get(cards.size() - 1).addEffect((new Effect()).setEffect(Effect.ON_SUMMON));
        cards.add(new Card("Brute",             4, 4, 5));
        //  cards.get(cards.size() - 1).addEffect((new Effect()).setEffect(Effect.ON_SUMMON));
        cards.add(new Card("Ogre",              6, 7, 6));
        cards.add(new Card("Molten Beast",      9, 5, 7));
        cards.add(new Card("Golem",             7, 7, 7));
        cards.add(new Card("Gladiator",         7, 10, 8));
    }
}