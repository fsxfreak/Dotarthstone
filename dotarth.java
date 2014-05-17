package com.aplc.dotarthstone;

public class dotarth
{
	/**
	*	dotarth [email@domain] [password] [(check-inbox-every) seconds]
	*/
	public static void main(String[] args)
	{
		String email = args[0];
		String password = args[1];
		int intervalMilliseconds = Integer.parseInt(args[2]) * 1000;
		
		Game game = new Game(email, password);
		game.sendEmail("leonjcheung@gmail.com", "dotarthstone", "test");
		
		long lastMilliTime = System.currentTimeMillis() - intervalMilliseconds;
			
		while (true)
		{
			if (lastMilliTime + intervalMilliseconds <= System.currentTimeMillis())
			{
				lastMilliTime = System.currentTimeMillis();
				game.runGame();
			}
				
		}
	}
}