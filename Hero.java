import java.util.ArrayList;

public abstract class Hero extends Character
{
	protected int armor;
	protected int mana;
	protected ArrayList<Card> cardsInHand;

	public Hero(String name
			  , int health
			  , int damage
			  , int armor
			  , int mana
			  , ArrayList<Card> initialCards)
	{
		super(name, health, damage)

		this.armor = armor;
		this.mana = mana;
		cardsInHand = initialCards;
	}

	protected abstract void heroPower(Character target);
	protected void addArmor(int amount) {}
	protected void useCard(Card card) {}

	public void doAction(Action[] actions) {}
}