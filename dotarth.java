import java.time.Clock;
import java.time.ZoneId;

public class dotarth
{
	/**
	*	dotarth [email@domain] [password] [(check-inbox-every) seconds]
	*/
	public static void main(String[] args)
	{
		String email = args[1];
		String password = args[2];
		int interval = Integer.parseInt(args[3]);

		Game game = new Game(email, pass);
		Clock clock = Clock.tickSeconds(ZoneId.systemDefault());
		Instant fromTime = clock.instant().minus(interval, ChronoUnit.SECONDS);

		while (true)
		{
			if (clock.instant().compareTo(fromTime.plus(interval, ChronoUnit.SECONDS)) >= 0)
			{
				fromTime = clock.instant();
				game.runGame();
			}
				
		}
	}
}