
/*
CLASS: Asteroids
DESCRIPTION: Extending Game, Asteroids is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.
Original code by Dan Leyzberg and Art Simon
 */
import java.awt.*;
import java.util.Random;

import java.util.*;

public class Asteroids extends Game {
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;

	static int counter = 0;

	private java.util.List<Asteroid> randomAsteroids = new ArrayList<Asteroid>();
	
	Ship ship;
	
	Star[] stars;
	
	private static int tracking = 90;
	private boolean collide = false;
	
	private static int lives = 5;


	public Asteroids() {
		super("Asteroids!",SCREEN_WIDTH,SCREEN_HEIGHT);
		this.setFocusable(true);
		this.requestFocus();

		// create a number of random asteroid objects
		randomAsteroids = createRandomAsteroids(10,60,30);
		
				
		ship = createShip();

		this.addKeyListener(ship);
		
		stars = createStars(150, 5);


	}
	
	// private helper method to create the Ship
		private Ship createShip() {
	        // Look of ship
			
			//Ship
			
			double xCenter = Asteroids.SCREEN_WIDTH / 2;
			double yCenter = Asteroids.SCREEN_HEIGHT / 2;
			
			Point[] pts = new Point[5];
			Point position;
			pts[0] = new Point (xCenter - 80, yCenter - 30);
			pts[1] = new Point (xCenter - 60, yCenter - 10);
			pts[2] = new Point (xCenter, yCenter);
			pts[3] = new Point (xCenter - 60, yCenter + 10);
			pts[4] = new Point (xCenter - 80, yCenter + 30);
			
	        // Set ship at the middle of the screen

			position = new Point (300,250);

			return new Ship(pts, position, 0);
	    }

	//  Create an array of random asteroids
	private java.util.List<Asteroid> createRandomAsteroids(int numberOfAsteroids, int maxAsteroidWidth,
			int minAsteroidWidth) {
		java.util.List<Asteroid> asteroids = new ArrayList<>(numberOfAsteroids);

		for(int i = 0; i < numberOfAsteroids; ++i) {
			// Create random asteroids by sampling points on a circle
			// Find the radius first.
			int radius = (int) (Math.random() * maxAsteroidWidth);
			if(radius < minAsteroidWidth) {
				radius += minAsteroidWidth;
			}
			// Find the circles angle
			double angle = (Math.random() * Math.PI * 1.0/2.0);
			if(angle < Math.PI * 1.0/5.0) {
				angle += Math.PI * 1.0/5.0;
			}
			// Sample and store points around that circle
			ArrayList<Point> asteroidSides = new ArrayList<Point>();
			double originalAngle = angle;
			while(angle < 2*Math.PI) {
				double x = Math.cos(angle) * radius;
				double y = Math.sin(angle) * radius;
				asteroidSides.add(new Point(x, y));
				angle += originalAngle;
			}
			// Set everything up to create the asteroid
			Point[] inSides = asteroidSides.toArray(new Point[asteroidSides.size()]);
			Point inPosition = new Point(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT);
			double inRotation = Math.random() * 360;
			asteroids.add(new Asteroid(inSides, inPosition, inRotation));
		}
		return asteroids;
	}

	public void paint(Graphics brush) {
		brush.setColor(Color.black);
		brush.fillRect(0,0,width,height);

		// sample code for printing message for debugging
		// counter is incremented and this message printed
		// each time the canvas is repainted
		counter++;
		brush.setColor(Color.white);
		brush.drawString("Counter is " + counter,10,10);
		

		
		brush.setColor(Color.white);
		brush.drawString("Lives left " + lives,10,20);

				

		
		// display the random asteroids
		for (Asteroid asteroid : randomAsteroids) {
			asteroid.paint(brush,Color.lightGray);
			asteroid.move();

			
			if (!collide) {
				collide = asteroid.collision(ship);
			}
		}

		//ship.paint(brush, Color.pink);

		if (collide) {
			ship.paint(brush, Color.red);
			tracking -= 1;
			if (tracking <= 0) {
				collide = false;
				tracking = 90;
				lives -= 1;
			}
			
		}
		
		else {
			ship.paint(brush, Color.pink);
		}
		
		


		ship.move();

		//Random number generator for the stars to twinkle
		Random twinkle = new Random(); 
		int upperbound = 30;
        int int_twinkle = twinkle.nextInt(upperbound); 


		
		for (int i = 0; i < stars.length; i++) {
			stars[i].paint(brush, Color.white);
			if(int_twinkle == 13) {
				stars[i].paint(brush, Color.black);
			}
		}
		
		ArrayList<Bullet> bulletsFired = ship.getBullets();
		ArrayList<Bullet> bulletsOut = new ArrayList<Bullet>();
		ArrayList<Asteroid> asteroidCollide = new ArrayList<Asteroid>();
		ArrayList<Bullet> bulletsCollide = new ArrayList<Bullet>();


		
		for (Bullet bullets : bulletsFired) {
			bullets.paint(brush,Color.white);
			bullets.move();
			if (bullets.outOfBounds()) {
				bulletsOut.add(bullets);
			}
			
			for (Asteroid asteroid : randomAsteroids) {
				if(asteroid.contains(bullets.getCenter())) {
					asteroidCollide.add(asteroid);
					bulletsCollide.add(bullets);
				}
			}
			randomAsteroids.removeAll(asteroidCollide);				
		}
		
		bulletsFired.removeAll(bulletsOut);
		bulletsFired.removeAll(bulletsCollide);
		
		if(randomAsteroids.isEmpty()) {
			brush.setColor(Color.orange);
			brush.fillRect(0,0,width,height);
			
			brush.setColor(Color.black);
			brush.drawString("Game Over! You Won!", + 330, 295);
		}
		
		if(lives <= 0) {
			brush.setColor(Color.black);
			brush.fillRect(0,0,width,height);
			
			brush.setColor(Color.white);
			brush.drawString("Game Over! You Lost :(", + 330, 295);
		}

	}
	

	// Create a certain number of stars with a given max radius
	public Star[] createStars(int numberOfStars, int maxRadius) {
		Star[] stars = new Star[numberOfStars];
		for (int i = 0; i < numberOfStars; ++i) {
			Point center = new Point(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT);

			int radius = (int) (Math.random() * maxRadius);
			if (radius < 1) {
				radius = 1;
			}
			stars[i] = new Star(center, radius);
			
		}

		return stars;
	}

	


	public static void main (String[] args) {
		Asteroids a = new Asteroids();
		a.repaint();
	}
}