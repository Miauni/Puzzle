package ale.route.firstgame.model;

import java.util.Observer;

public interface Game
{
	boolean isFinished();
	
	void makeMove(int x, int y);
	
	int size();
	
	int getValue(int x, int y);
	
	void addObserver(Observer o);

}
