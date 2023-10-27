import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.awt.Graphics;


public class Ship extends Polygon implements KeyListener {
	
	boolean forward;
	boolean left;
	boolean right;
	boolean shoot = false;
	
	java.util.ArrayList<Bullet> bulletsFired = new ArrayList<Bullet>();


	public Ship(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void paint(Graphics brush, Color color) {
		
		Point[] getPts = getPoints();
		
		int[] xPoints = new int[getPts.length];
		int[] yPoints = new int[getPts.length];
		int nPoints = xPoints.length;

		
		for(int i=0; i< getPts.length; i++) {
			xPoints[i] = (int)getPts[i].x;
			yPoints[i] = (int)getPts[i].y;
		}
		
		
		brush.setColor(color);
		
		brush.drawPolygon(xPoints, yPoints, nPoints);
		
		brush.fillPolygon(xPoints, yPoints, nPoints);
		
	
	}

	@Override
	public void move() {
		double x = position.x;
		double y = position.y;
		
		//if asteroid goes out of frame, move it back in
		if(x > Asteroids.SCREEN_WIDTH) {
			x = 0;
		}
		
		if(y > Asteroids.SCREEN_HEIGHT) {
			y = 0;
		}
		
		if(x < 0) {
			x = Asteroids.SCREEN_WIDTH;
		}
		
		if(y < 0) {
			y = Asteroids.SCREEN_HEIGHT;
		}
		this.position = new Point(x, y);
		
		if (forward) {
			position.x += 3 * Math.cos(Math.toRadians(rotation));
			position.y += 3 * Math.sin(Math.toRadians(rotation));
		}
		
		if (left) {
			rotate(-5);
		}
		
		if (right) {
			rotate(5);
		}
		
		if (shoot) {
			for(int i = 0; i < bulletsFired.size(); i++) {
				bulletsFired.get(i).move();
			}
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			forward = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (!shoot) {
				Point[] getPts = getPoints();
				Bullet fired = new Bullet(getPts[2], rotation);
				bulletsFired.add(fired);
				shoot = true;
			}

		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			forward = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			shoot = false;
		}
	}
	
	public ArrayList<Bullet> getBullets() {
			return bulletsFired;
	}

}
