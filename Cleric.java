public class Cleric extends Hero
{
	public Cleric(String name
				 , int health
				 , int damage
				 , int armor
				 , int mana
				 , ArrayList<Card> initialCards)
	{
		super(name, health, damage, armor, mana, initialCards);
	}

	private void constructDeck()
	{
		
	}

	protected void heroPower(Character target)
	{
		target.heal(2);
	}
}