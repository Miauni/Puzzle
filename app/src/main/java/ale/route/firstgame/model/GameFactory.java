package ale.route.firstgame.model;

public class GameFactory
{
	public static Game createGame(int size)
	{
		return new GameEngine(size);
	}
}
