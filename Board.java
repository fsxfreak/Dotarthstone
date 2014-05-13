import java.util.ArrayList;

public static class Board
{
	public ArrayList<Character> left;
	public ArrayList<Character> right;

	public static ArrayList<Character> getCards(Hero forHero)
	{
		if (left.equals(forHero))
		{
			ArrayList<Character> cards = new ArrayList<Character>(left);
			cards.remove(0);
			return cards;
		}
		else if (right.equals(forHero))
		{
			ArrayList<Character> cards = new ArrayList<Character>(right);
			cards.remove(0);
			return cards;
		}

		return null;
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