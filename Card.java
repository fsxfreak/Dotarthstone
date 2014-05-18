package com.aplc.dotarthstone;

import java.util.ArrayList;

public class Card extends Character
{
	private int manaCost;
	private ArrayList<Type> effects;

	public Card(String name, int health, int damage, int manaCost)
	{
		super(name, health, damage);
		this.manaCost = manaCost;
	}

	protected void addEffect(Type effect) { effects.add(effect); }
	
	public String getCardInfo()
	{
		return getName() + "[" + health + ", " + damage + "]";
	}
}