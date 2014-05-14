import java.util.ArrayList;

public static class Board
{
	//0th index contains the hero that owns the cards on the board
	public ArrayList<Character> left;
	public ArrayList<Character> right;

	public static ArrayList<Card> getCards(Hero forHero)
	{
		ArrayList<Card> cards = null;

		if (left.get(0).equals(forHero))
		{
			cards = (ArrayList<Card>)left.clone();
			cards.remove(0);
		}
		else if (right.get(0).equals(forHero))
		{
			cards = (ArrayList<Card>)right.clone();
			cards.remove(0);
		}

		return cards;
	}

	public static ArrayList<Card> getAllCards()
	{
		ArrayList<Card> leftCards = getCards(left);
		ArrayList<Card> rightCards = getCards(right);

		leftCards.addAll(rightCards);

		return leftCards;
	} 

	public static void remove(Character character)
	{
		if (character.equals(left.get(0)))
		{
			//TODO GAME OVER
		}
		else if (character.equals(right.get(0)))
		{
			//TODO GAME OVER
		}

		left.remove(character);
		right.remove(character);
	}
}