import java.util.ArrayList;

public abstract class Hero extends Character
{
	public String email;

	protected int armor;
	protected int mana;
	protected ArrayList<Card> cardsInHand;
	protected ArrayList<Card> deck;

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

	protected abstract void constructDeck();
	protected abstract void heroPower(Character target);
	protected void addArmor(int amount) { armor += amount}
	protected void useCard(Card card) {}

	public void awardCards(int numCards)
	{
		for (int i = 0; i < numCards; i++)
		{
			int cardIndex = Math.random() * deck.size();
			cardsInHand.add(deck.get(cardIndex));
			deck.remove(deck.get(cardIndex));
		}
	}

	public void awardCards(String cardName)
	{
		//TODO check correctness
		if (cardName.equals("Coin"))
		{
			cardsInHand.add(new Coin());
		}

		for (Card e : deck)
		{
			if (e.getName().equals(cardName))
			{
				cardsInHand.add(e);
				deck.remove(e);
			}
		}
	}

	public void doAction(Action[] actions) 
	{
		for (Action act : actions)
		{
			//parse actions, call appropriate functions with their parameters
		}
	}
}