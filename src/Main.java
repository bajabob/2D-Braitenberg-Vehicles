import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private static final int HEIGHT = 600;
	
	private static final int MAX_INTENSITY = 200;
	private static final int MIN_INTENSITY = 50;
	
	private static JButton captureSimulator;
	private static JButton incLightIntensity, decLightIntensity, clearLights;
    private static JButton incVehicle1, decVehicle1, incVehicle2, decVehicle2;
    private static JButton randomLights;
    
    private static JLabel numOfVehicles1, numOfVehicles2, lightIntensity;
    
	private static BackgroundDisplay displayPanel;
	private static lightPopulation lightPopulation;
	

	private static class BackgroundDisplay extends JPanel
	{
		
		public boolean canCaptureLights;
		public Stack<Vehicle> vehiclesSeekers;
		public Stack<Vehicle> vehiclesEvaders;

		public BackgroundDisplay(){
			canCaptureLights = false;
			vehiclesSeekers = new Stack<Vehicle>();
			vehiclesEvaders = new Stack<Vehicle>();
		}
		
		public void onMove(ArrayList<LightSource>lights){
			for(int i = 0; i < vehiclesSeekers.size(); i++){
				vehiclesSeekers.get(i).onMove(lights, canCaptureLights);
			}
			for(int i = 0; i < vehiclesEvaders.size(); i++){
				vehiclesEvaders.get(i).onMove(lights, false);
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
			
			for(int i = 0; i < vehiclesSeekers.size(); i++){
				vehiclesSeekers.get(i).onDraw(g);
			}
			for(int i = 0; i < vehiclesEvaders.size(); i++){
				vehiclesEvaders.get(i).onDraw(g);
			}
			
		}
	}
	
	private static void updateLabels(){
		lightIntensity.setText( ""+lightPopulation.lightIntensity );
		numOfVehicles1.setText( ""+displayPanel.vehiclesSeekers.size() );
		numOfVehicles2.setText( ""+displayPanel.vehiclesEvaders.size() );
		captureSimulator.setText( "Capture Lights: "+((displayPanel.canCaptureLights) ? "On" : "Off"));
	}
	
	private static class lightPopulation implements ActionListener{
		
		public int lightIntensity;
		public ArrayList<LightSource> lights;
		
		public lightPopulation(){
			lightIntensity = 100;
			lights = new ArrayList<LightSource>();
		}
		
		public void clearLights(){
			for(int i = 0; i < lights.size(); i++){
				lights.get( i ).captureLight();
			}
		}
		
		public void addLight(Point p){
			lights.add(new LightSource(p, 5, lightIntensity));
		}
		public void addLight(Point p, int intesity){
			lights.add(new LightSource(p, 5, intesity));
		}
		
		@Override
		public void actionPerformed(ActionEvent e){
			JButton button = (JButton) e.getSource();
		
			
			if( button.equals(randomLights))		
			{
				clearLights();
				Random r = new Random(System.currentTimeMillis());

				for(int i = 0; i < 20; i++){
					Point x = new Point(r.nextInt( WIDTH-100 )+50, 
							r.nextInt( HEIGHT-100 )+50);				
					this.addLight(x, r.nextInt( MAX_INTENSITY-MIN_INTENSITY )+MIN_INTENSITY);
				}
			}
			
			if(button.equals( incLightIntensity )){
				lightIntensity+=10;
				if(lightIntensity > MAX_INTENSITY){
					lightIntensity = MAX_INTENSITY;
				}
			}
			if(button.equals( decLightIntensity )){
				lightIntensity-=10;
				if(lightIntensity < MIN_INTENSITY){
					lightIntensity = MIN_INTENSITY;
				}
			}
			if(button.equals( captureSimulator )){
				displayPanel.canCaptureLights = !displayPanel.canCaptureLights;
			}
			if(button.equals( clearLights )){
				clearLights();
			}
			updateLabels();
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
				displayPanel.vehiclesSeekers.add(new Vehicle(50 + r.nextInt(WIDTH-50), 50+ r.nextInt(HEIGHT-50),true));
				
			}
			if( button.equals(decVehicle1))
			{
				if(!displayPanel.vehiclesSeekers.isEmpty()){
					displayPanel.vehiclesSeekers.pop();
				}
			}
			if( button.equals(incVehicle2))
			{
				Random r = new Random();
				displayPanel.vehiclesEvaders.add(new Vehicle(50+r.nextInt(WIDTH-50),50+r.nextInt(HEIGHT-50),false));
							}
			if( button.equals(decVehicle2))
			{
				if(!displayPanel.vehiclesEvaders.isEmpty()){
					displayPanel.vehiclesEvaders.pop();
				}
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
		
		incLightIntensity = new JButton("+");
		incLightIntensity.addActionListener( lightPopulation );
		decLightIntensity = new JButton("-");
		decLightIntensity.addActionListener( lightPopulation );
		lightIntensity = new JLabel("", SwingConstants.CENTER);
		
		captureSimulator = new JButton();
		captureSimulator.addActionListener( lightPopulation );
		
		randomLights = new JButton("Random Lights");
		randomLights.addActionListener(lightPopulation);
	
		clearLights = new JButton("Clear Lights");
		clearLights.addActionListener(lightPopulation);
		
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
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		controlPanel.add( new JLabel( "Seekers", SwingConstants.CENTER ), c );
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
		controlPanel.add( new JLabel( "Evaders", SwingConstants.CENTER ), c );
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
		controlPanel.add( new JLabel( "Light Intensity", SwingConstants.CENTER ), c );
		c.gridx = 1;
		c.gridy = 3;
		controlPanel.add( incLightIntensity, c );
		c.gridx = 2;
		c.gridy = 3;
		controlPanel.add( lightIntensity, c );
		c.gridx = 3;
		c.gridy = 3;
		controlPanel.add( decLightIntensity, c );
		
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 4;
		controlPanel.add( randomLights, c );
		
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 4;
		controlPanel.add( clearLights, c );
		
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 4;
		controlPanel.add( captureSimulator, c );
		
		
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
		window.setSize( 1020, 620 );
		window.setLocation( 100, 100 );
		window.setVisible( true );
		window.setResizable( false );
		
		TimerTask timerTask = new TimerTask(){

			public void run() {
				displayPanel.onMove(lightPopulation.lights);
				updateLabels();
			}
			
		};
		Timer timer = new Timer();
		timer.schedule(timerTask, 500, 20);
		
		
	}

}