import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;


public class Sensors
{

	private int xCoord;

	private int yCoord;
	
	private int width;
	
	private int height;
	

	public Sensors(int x, int y, int width, int height )
	{
		this.xCoord = x;
		this.yCoord = y;
		this.width = width;
		this.height = height;
	}
	
	public int getX(){
		return xCoord;
	}
	public int getY(){
		return yCoord;
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	


	public void onDraw( Graphics g )
	{
		g.setColor( Color.RED );
		g.fillRect( xCoord, yCoord, width, height );
	}

}