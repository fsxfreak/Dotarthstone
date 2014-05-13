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
	}

	protected void heroPower(Character target)
	{
		target.hurt(2);
		hurt(target.getDamage());
	}
}