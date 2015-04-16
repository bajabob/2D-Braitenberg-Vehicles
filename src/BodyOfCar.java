import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class BodyOfCar
{

	private int xCoord;

	private int yCoord;
	
	private int width;
	
	private int height;
	
	private Color color;

	public BodyOfCar(int x, int y, int width, int height, Color color )
	{
		this.xCoord = x;
		this.yCoord = y;
		this.width = width;
		this.height = height;
		this.color = color;
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
		g.setColor( color );
		g.fillRect( xCoord, yCoord, width, height );
	}

}