import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
// store all the light points
// create car and place it

// tuesday create moving car before class

public class Main
{
	 
	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;
	
	
    private static JButton incVehicle1, decVehicle1, incVehicle2, decVehicle2;
    private static JButton randomLights;
    
    private static JLabel numOfVehicles1, numOfVehicles2;
    
	private static BackgroundDisplay displayPanel;
	private static lightPopulation lightPopulation;
	

	private static class BackgroundDisplay extends JPanel
	{
				
		public Stack<Vehicle> vehicles;

		public BackgroundDisplay(){
			vehicles = new Stack<Vehicle>();
		}
		
		public void onMove(ArrayList<LightSource>lights){
			for(int i = 0; i < vehicles.size(); i++){
				vehicles.get(i).onMove(lights);
			}
			this.repaint();
		}
		
		public void paintComponent( Graphics g )
		{
			super.paintComponent( g );
			g.setColor( Color.BLACK );
			g.fillRect( 0, 0, 800, 600 );
			for(int i = 0; i < lightPopulation.lights.size(); i++){
				lightPopulation.lights.get(i).onDraw(g, i);
			}
			
			for(int i = 0; i < vehicles.size(); i++){
				vehicles.get(i).onDraw(g);
			}
			
		}
	}

	public static Point randomPoint(){
		Random rand = new Random();
		int  n = rand.nextInt(WIDTH) + 1;
		int  m = rand.nextInt(HEIGHT) + 1;
		Point origin = new Point(n,m);
		
		return origin;
	}
	
	public static int randomInt(){
		Random rand = new Random();
		int x = rand.nextInt(300) + 50;
		
		return x;
	}
	
	private static void updateLabels(){
		
	}
	private static class lightPopulation implements ActionListener{
		
		public ArrayList<LightSource> lights;
		public ArrayList<Point> coordinateOfPoints;
		
		public lightPopulation(){
			lights = new ArrayList<LightSource>();
			coordinateOfPoints= new ArrayList<Point>();
		}
		public void lightRandomPoint(){
			lights.clear();
			coordinateOfPoints.clear();

			for(int i = 0; i < randomInt(); i++){
				Point x = randomPoint();				
				this.addLight(x);
			}
		}
		
		public void addLight(Point p){
			lights.add(new LightSource(p, 3, Color.YELLOW));
			coordinateOfPoints.add(p);
		}
		
		@Override
		public void actionPerformed(ActionEvent e){
			JButton button = (JButton) e.getSource();
		
			
			if( button.equals(randomLights))		
			{
				lightRandomPoint();
			}
			displayPanel.repaint();
		}
			
	}
	
	private static class robotPopulation implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JButton button = (JButton) e.getSource();
	
			
			if( button.equals(incVehicle1))
			{
				Random r = new Random();
				displayPanel.vehicles.add(new Vehicle(r.nextInt(WIDTH),r.nextInt(HEIGHT),true));
				
			}
			if( button.equals(decVehicle1))
			{
				displayPanel.vehicles.pop();
			}
			if( button.equals(incVehicle2))
			{
				Random r = new Random();
				displayPanel.vehicles.add(new Vehicle(r.nextInt(WIDTH),r.nextInt(HEIGHT),false));
							}
			if( button.equals(decVehicle2))
			{
				displayPanel.vehicles.pop();
			}
			
			
			updateLabels();
			displayPanel.repaint();
		}
		
		
	}
	
	public static void main( String[] args )
	{
		
		robotPopulation robotPopulation = new robotPopulation();
		lightPopulation = new lightPopulation();
		
		incVehicle1 = new JButton("+");
		incVehicle1.addActionListener( robotPopulation );
		decVehicle1 = new JButton("-");
		decVehicle1.addActionListener( robotPopulation );
		
		incVehicle2 = new JButton("+");
		incVehicle2.addActionListener( robotPopulation );
		decVehicle2 = new JButton("-");
		decVehicle2.addActionListener( robotPopulation );
		
		randomLights = new JButton("Random Lights");
		randomLights.addActionListener(lightPopulation);
	
		numOfVehicles1 = new JLabel("");
		numOfVehicles2 = new JLabel("");
	
		
		displayPanel = new BackgroundDisplay();
		displayPanel.setBounds( 0, 0, 800, 600 );
		displayPanel.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				lightPopulation.addLight(new Point(e.getX(), e.getY()));
				displayPanel.repaint();
			}
			public void mousePressed(MouseEvent e) {}

			public void mouseReleased(MouseEvent e) {}

			public void mouseEntered(MouseEvent e) {}

			public void mouseExited(MouseEvent e) {}
			
		});
		
		
		JPanel controlPanel = new JPanel();
		controlPanel.setBackground( Color.WHITE );
		controlPanel.setBorder(new EmptyBorder(10,10,10,10));
		controlPanel.setLayout( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		controlPanel.add( new JLabel( "Vehicle 1", SwingConstants.CENTER ), c );
		c.gridx = 1;
		c.gridy = 1;
		controlPanel.add( incVehicle1, c );
		c.gridx = 2;
		c.gridy = 1;
		controlPanel.add( numOfVehicles1, c );
		c.gridx = 3;
		c.gridy = 1;
		controlPanel.add( decVehicle1, c );

		c.gridx = 0;
		c.gridy = 2;
		controlPanel.add( new JLabel( "Vehicle 2", SwingConstants.CENTER ), c );
		c.gridx = 1;
		c.gridy = 2;
		controlPanel.add( incVehicle2, c );
		c.gridx = 2;
		c.gridy = 2;
		controlPanel.add( numOfVehicles2, c );
		c.gridx = 3;
		c.gridy = 2;
		controlPanel.add( decVehicle2, c );

		c.gridx = 0;
		c.gridy = 3;
		controlPanel.add( new JLabel( "", SwingConstants.CENTER ), c );
		c.gridx = 1;
		c.gridy = 3;
		controlPanel.add( randomLights, c );
		c.gridx = 2;
		c.gridy = 3;
		controlPanel.add( new JLabel("Random Lights"), c );
		
		JPanel panes = new JPanel();
		panes.setLayout( new BorderLayout() );
		panes.add( displayPanel, BorderLayout.CENTER );
		panes.add( controlPanel, BorderLayout.EAST);

		/**
		 * Additional items needed to setup the window
		 */
		JFrame window = new JFrame( "Team Ares - Project 4" );
		
		window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		window.setContentPane( panes );
		window.setSize( 1150, 620 );
		window.setLocation( 100, 100 );
		window.setVisible( true );
		window.setResizable( false );
		
		TimerTask timerTask = new TimerTask(){

			public void run() {
				displayPanel.onMove(lightPopulation.lights);
			}
			
		};
		Timer timer = new Timer();
		timer.schedule(timerTask, 500, 5);
		
		
	}

}