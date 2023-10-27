import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends Circle {
	
	private static final int RADIUS = 6;
	private double rotation;

	public Bullet(Point center, double rotation) {
		super(center, RADIUS); // define RADIUS in Bullet class
		this.rotation = rotation;
		}


	@Override
	public void paint(Graphics brush, Color color) {
		// TODO Auto-generated method stub
		brush.setColor(color);
		
		brush.fillOval(((int)center.x), ((int)center.y), radius, radius);
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		center.x += 3 * Math.cos(Math.toRadians(rotation));
		center.y += 3 * Math.sin(Math.toRadians(rotation));

	}
	
	public boolean outOfBounds() {
		double x = center.x;
		double y = center.y;
		
		if (x > Asteroids.SCREEN_WIDTH) {
			return true;
		}
		
		if (y > Asteroids.SCREEN_HEIGHT) {
			return true;
		}
		
		if (x < 0) {
			return true;
		}
		
		if (y < 0) {
			return true;
		}
		
		return false;
		
	}
	
	public Point getCenter() {
		return center;
	}

}
