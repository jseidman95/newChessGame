package gameBoard;

//this is a simple class which keeps track of coordinates
public class GridPoint
{
	private int x;
	private int y;
	
	public GridPoint()
	{
		x = 0;
		y = 0;
	}
	
	public GridPoint(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void updatePosition(int x,int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
}
