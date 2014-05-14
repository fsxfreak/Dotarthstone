public class WitchDoctor extends Hero
{
	public WitchDoctor(String name
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
		ArrayList<Card> myCards = board.getCards(this);
		ArrayList<Card> otherCards = board.getCards(this);
		if (myCards.contains(target))
		{
			target.heal(1);
		}
		else if (otherCards.contains(target))
		{
			target.hurt(1);
		}
	}
}