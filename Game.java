public class Game
{
	private boolean isLeftTurn = true;
	private Hero left;
	private Hero right;

	private String email;
	private String password;

	private int currentTurn = 1;

	public Game(String email, String password) 
	{
		this.email = email;
		this.password = password;

		totalTurns = 0;
	}

	//this should be the big dog
	//come up with actions and verify if actions are valid
	public void parseEmail() {}

	public void startGame() 
	{
		left.awardCards(3);	//left is always first
		right.awardCards(4);
		right.awardCards("Coin");
	}

	public void runGame() 
	{
		//parseEmail() get actions. for now, test code below
		/*if (parseEmail() == "register player1@email.com player2@email.com")
		{
			left = new Hero(..);
			left.email = player1@email.com;
			right.email = player2@email.com; 
			startGame();
			return;
		}*/

		if (isLeftTurn)
		{
			left.awardCards(1);
		}
		else
		{
			right.awardCards(1);
		}

		if (!isLeftTurn)
			currentTurn++;

		isLeftTurn = !isLeftTurn;
	}
}