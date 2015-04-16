import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import math.geom2d.Point2D;
import math.geom2d.Vector2D;

public class Vehicle {
	
	private static final double MIN_SPEED = 2.0;
	private static final double MAX_SPEED = 10.0;
	
	private double globalX, globalY;
	double rotation;

	private Wheels wheelFl;
	private Wheels wheelFr;
	private Wheels wheelBl;
	private Wheels wheelBr;

	private Sensors leftSensor;
	private Sensors rightSensor;
	private boolean isCrossed;
	private BodyOfCar car;
	private double speed;

	public Vehicle(int globalX, int globalY, boolean isCrossed) {

		this.speed = MIN_SPEED;
		this.globalX = globalX;
		this.globalY = globalY;

		Random r = new Random();
		this.rotation = r.nextInt(360);

		leftSensor = new Sensors(20, 3, 12, 4);

		rightSensor = new Sensors(20, 13, 12, 4);

		wheelFl = new Wheels(new Point(14, 0), 5, Color.RED);

		wheelFr = new Wheels(new Point(14, 24), 5, Color.RED);

		wheelBl = new Wheels(new Point(4, 0), 5, Color.RED);

		wheelBr = new Wheels(new Point(4, 24), 5, Color.RED);

		this.isCrossed = isCrossed;
		if (isCrossed) {
			car = new BodyOfCar(0, 0, 20, 20, Color.GREEN);
		} else {
			car = new BodyOfCar(0, 0, 20, 20, Color.BLUE);
		}
	}

	public void onMove(ArrayList<LightSource> lights, boolean canCaptureLights) {
		if (!lights.isEmpty()) {
			for (int i = 0; i < lights.size(); i++) {

				if(lights.get(i).isCaptured()){
					continue;
				}
				Point p = lights.get(i).getCenter();
				int intensity = lights.get( i ).getIntensity();
				
				double vecX = (p.x - (this.globalX-10.0));
				double vecY = (p.y - (this.globalY-10.0));
				
				double distance = Math.sqrt(Math.pow(vecX, 2)
						+ Math.pow(vecY, 2));
				if (distance < (intensity-50)) {

					Vector2D bot = new Vector2D(Math.cos(Math.toRadians(rotation))*2.0, 
							Math.sin(Math.toRadians(rotation))*2.0);
					Vector2D light = new Vector2D(vecX, vecY);
					
					double dotBL = bot.dot( light );
					double crossBL = bot.cross( light);
					
					double angle = Math.toDegrees(Math.atan2( crossBL , dotBL ));
					
					boolean speedChange = false;
					
					if(isCrossed){
						if(angle < 0.0 && angle > -80.0){
							rotation -= 3.0;
							speedChange = true;
							break;
						}else if(angle > 0.0 && angle < 80.0){
							rotation += 3.0;
							speedChange = true;
							break;
						}
						if(Point2D.distance( bot.getX(), bot.getY(), light.getX(), light.getY() ) < 18.0 && canCaptureLights){
							lights.get(i).captureLight();
							if(speed < MAX_SPEED){
								speed += 0.5;
							}
						}
						
					}else{
						double deflect = ((intensity - distance) * 10.0) / intensity;
						if(angle < 0.0 && angle > -60.0){
							rotation += deflect;
							speedChange = true;
						}else if(angle > 0.0 && angle < 60.0){
							rotation -= deflect;
							speedChange = true;
						}
					}
					
					if(speedChange && speed >= MIN_SPEED){
						speed -= (MIN_SPEED-0.5);
					}
					
				}
			}
		}

		double x = speed * Math.cos(Math.toRadians(rotation));
		double y = speed * Math.sin(Math.toRadians(rotation));

		this.globalX += x;
		this.globalY += y;

		if (globalX > 800)
			globalX = 0;
		if (globalY > 600)
			globalY = 0;
		if (globalX < 0)
			globalX = 800;
		if (globalY < 0)
			globalY = 600;
		
		if(speed < MIN_SPEED){
			speed = MIN_SPEED;
		}

	}

	public void onDraw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.translate(-10, -10);
		g2d.translate(globalX, globalY);
		g2d.rotate(Math.toRadians(rotation));
		leftSensor.onDraw(g);
		rightSensor.onDraw(g);
		car.onDraw(g);
		wheelFl.onDraw(g);
		wheelFr.onDraw(g);
		wheelBl.onDraw(g);
		wheelBr.onDraw(g);
		g2d.rotate(Math.toRadians(-rotation));
		g2d.translate(-globalX, -globalY);
		g2d.translate(10, 10);

	}
}
