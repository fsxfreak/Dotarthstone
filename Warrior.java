public class Warrior extends Hero
{
	public Warrior(String name
				 , int health
				 , int damage
				 , int armor
				 , int mana
				 , ArrayList<Card> initialCards)
	{
		super(name, health, damage, armor, mana, initialCards);
		constructDeck();
	}

	private void constructDeck()
	{
		//TODO - construct deck of 25 cards from default cards
		//deck.add()
	}

	protected void heroPower(Character target)
	{
		target.hurt(2);
		hurt(target.getDamage());
	}


}