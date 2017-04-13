package ale.route.firstgame.model;

import android.util.Log;

import java.util.Observable;

public class GameEngine extends Observable implements Game
{
	public int[][] data;
	private int emptyX, emptyY;
	
	public GameEngine(int size)
	{
		data = new int[size][size];
		
		shuffle();
	}
	public int[][] getData(){
        return data;
    }
	
	private void shuffle()
	{
		int size = data.length;
		int k=1;
		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++)
				if(i != size -1 || j != size -1)
					data[i][j] = k++;
		
		data[size-1][size-2] = 0;
		data[size-1][size-1] = size * size - 1;
		emptyX = size - 1;
		emptyY = size - 2;
		
		int nrMoves=50;
		while(nrMoves>=0){
			int x=(int)(Math.random()*size-1);
			int y=(int)(Math.random()*size-1);
			if(checkCoord(x,y))
				if(!isEmpty(x, y) && possibleMove(x, y))
					makeMove(x, y);
			nrMoves--;
		}
	}

	@Override
	public boolean isFinished()
	{
		int size = data.length;
		
		int k=1;
		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++)
			{
				if(i != size -1 || j != size -1)
					if(data[i][j] != k)
						return false;
				k++;
			}
		
		return true;
	}

	@Override
	public void makeMove(int x, int y)
	{
		checkCoordinates(x, y);
		
		if(isEmpty(x, y))
			Log.d("","Cell is already empty");
			//System.out.println("Cell is already empty");
			//throw new RuntimeException("Cell is already empty");
		
		if(!possibleMove(x, y))
			Log.d("","Move not possible" );
			//System.out.println("Move not possible");
			//throw new RuntimeException("Move not possible");
		
		if( x == emptyX)
		{
			if(y < emptyY)
			{
				for(int i=emptyY-1;i>=y;i--)
					data[x][i+1] = data[x][i];
				data[x][y] = 0;
				emptyX = x;
				emptyY = y;
			}
			else
			{
				for(int i=emptyY +1 ; i<= y; i++)
					data[x][i-1] = data[x][i];
				data[x][y] = 0;
				emptyX = x;
				emptyY = y;
			}
		}
		else
			if(y == emptyY)
			{
				if(x<emptyX){
					for(int i=emptyX-1;i>=x;i--)
						data[i+1][y]=data[i][y];
					data[x][y]=0;
					emptyX=x;
					emptyY=y;
				}
				else if(x>emptyX)
				{
					for(int i=emptyX+1;i<=x;i++)
						data[i-1][y]=data[i][y];
					data[x][y]=0;
					emptyX=x;
					emptyY=y;
				}
			}
		
		setChanged();
		notifyObservers();	
	}

	@Override
	public int size()
	{
		return data.length;
	}

	@Override
	public int getValue(int x, int y)
	{
		checkCoordinates(x, y);
		return data[x][y];
	}
	
	private void checkCoordinates(int x, int y)
	{
		int size = data.length;
		if(x < 0 || x>= size)
			Log.d("","Coordinates not ok");
			//throw new RuntimeException("Coordinates not ok");
		if(y < 0 || y>= size)
			Log.d("","Coordinates not ok");
			//throw new RuntimeException("Coordinates not ok");
		
		
	}
	private boolean checkCoord(int x, int y){
		int size = data.length;
		if(x < 0 || x>= size)
			return false;
		if(y < 0 || y>= size)
			return false;
		return true;
	}
	public boolean isEmpty(int x, int y){
		if(x == emptyX && y == emptyY)
			return true;
		return false;
	}
	
	public boolean possibleMove(int x, int y){
		if(x != emptyX && y != emptyY)
			return false;
		return true;
		
	}
	
}
