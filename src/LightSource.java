import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class LightSource
{

	private Point center;

	private int radius;
	
	private int intensity;
	
	private boolean isCaptured;
	
	private static Color COLOR = new Color(1.0f, 1.0f, 1.0f, 0.1f);

	public LightSource( Point center, int radius, int intensity )
	{
		this.center = new Point( center.x - radius, center.y - radius );
		this.radius = radius;
		this.intensity = intensity;
		isCaptured = false;
	}
	
	public int getIntensity(){
		return this.intensity;
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
	
	public void captureLight(){
		this.isCaptured = true;
	}
	
	public boolean isCaptured(){
		return this.isCaptured;
	}

	public void onDraw( Graphics g, int index )
	{
		if(isCaptured){
			return;
		}
		g.setColor( COLOR );
		g.fillOval( center.x-(intensity/2)+radius, center.y-(intensity/2)+radius, intensity, intensity );
		
		g.setColor( Color.YELLOW );
		g.fillOval( center.x, center.y, radius * 2, radius * 2 );
	}

}