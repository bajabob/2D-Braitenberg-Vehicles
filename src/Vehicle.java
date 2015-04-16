import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import math.geom2d.Vector2D;

public class Vehicle {
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

	// 10 , 47
	public Vehicle(int globalX, int globalY, boolean isCrossed) {

		this.globalX = globalX;
		this.globalY = globalY;

		Random r = new Random();
		this.rotation = r.nextInt(360);
		//rotation = 45;

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

	public void onMove(ArrayList<LightSource> lights) {
		if (!lights.isEmpty()) {
			for (int i = 0; i < lights.size(); i++) {

				Point p = lights.get(i).getCenter();

				double vecX = (p.x - this.globalX);
				double vecY = (p.y - this.globalY);
				
				double distance = Math.sqrt(Math.pow(vecX, 2)
						+ Math.pow(vecY, 2));
				if (distance < 100) {

					
					
					Vector2D bot = new Vector2D(Math.cos( Math.toRadians(rotation))*100, Math.sin( Math.toRadians(rotation))*100);
					Vector2D light = new Vector2D(vecX, vecY);
					
					double dotBL = bot.dot( light );
					double crossBL = bot.cross( light);
					
					double angle = Math.toDegrees(Math.atan2( crossBL , dotBL ));
					
					double deflect = ((100 - distance) * 10.0) / 100;
					
					if(angle < 0.0 && angle > -40.0){
						rotation += deflect;
					}else if(angle > 0.0 && angle < 40.0){
						rotation -= deflect;
					}
					
				}
			}
		}

		double x = 2.0 * Math.cos(Math.toRadians(rotation));
		double y = 2.0 * Math.sin(Math.toRadians(rotation));

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
