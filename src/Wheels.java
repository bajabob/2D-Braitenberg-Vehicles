import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Wheels
{

	private Point center;

	private int radius;
	
	private Color color;

	public Wheels( Point center, int radius, Color color )
	{
		this.center = new Point( center.x - radius, center.y - radius );
		this.radius = radius;
		this.color = color;
	}
	
	
	public void setCenter(Point _center){
		center = _center;
	}
	
	public Point getCenter(){
		return center;
	}
	
	public void setRadius(int _radius){
		radius = _radius;
	}
	public int getRadius(){
		return radius;
	}

	public void onDraw( Graphics g )
	{
		g.setColor( color );
		g.fillOval( center.x, center.y, radius * 2, radius * 1 );
	}

}