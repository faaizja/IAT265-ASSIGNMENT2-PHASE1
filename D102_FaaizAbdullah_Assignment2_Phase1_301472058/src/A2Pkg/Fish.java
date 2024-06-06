package A2Pkg;

import processing.core.PVector;
import processing.core.PMatrix;
import processing.core.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

public class Fish {

	// this method creates fish as food for the shark to detect and eat

	private PVector pos, speed; // private fields for holding its position and moving velocity (direction fish travelling will be randomized)
	private int size, w, h, tail,scale;

	public Fish(int x,int y,int w, int h, int xSpeed, int ySpeed) {
		// constructor
		this.pos = new PVector(x,y); // initial coordinates
		this.speed = new PVector(xSpeed,ySpeed); // moving speed
		this.w = w; // frame width
		this.h = h; // frame height
		this.size = 35; // fish body width
		this.tail = 20; // tail width
		this.scale = 1;
	}

	public void draw(Graphics2D g) {

		AffineTransform af = g.getTransform(); // push

		g.translate((int) pos.x, (int) pos.y); // translate to initial coordinates
		g.scale(scale, scale);
//		g.rotate(speed.heading());

		if (speed.x < 0) // if fish hits right edge flip image towards left and vice versa
			g.scale(-1, 1);

		// fish body
		g.setColor(Color.CYAN);
		Ellipse2D.Double body = new Ellipse2D.Double(0 - this.size / 2, -this.size / 4, this.size, this.size / 2);
		g.fill(body);

		// outer eye
		g.setColor(Color.white);
		Ellipse2D.Double outerEye = new Ellipse2D.Double(7.5, -4, 6, 6);
		g.fill(outerEye);

		// innereye
		g.setColor(Color.black);
		Ellipse2D.Double innerEye = new Ellipse2D.Double(9, -3, 3, 3);
		g.fill(innerEye);

		// tail
		g.setColor(Color.cyan);
		Polygon tail = new Polygon();
		tail.addPoint(-15, 0);
		tail.addPoint(-20, 5);
		tail.addPoint(-25, 0);
		g.fillPolygon(tail);

		g.setTransform(af); // pop
	}

	public void move() {
		this.pos.add(speed); // move fish in predetermined direction

		// call wall handler if needed
		if ((pos.x + (this.size * scale)/ 2 > this.w)) {
			this.pos.x = this.w - ((this.size*scale) + this.tail);
			this.handleWalls(true); // call
		}

		if (pos.x - (this.size *scale)/ 2 < 0) {
			this.pos.x = (this.size);
			this.handleWalls(true); // call
		}

		if ((pos.y - (this.size *scale) / 4 < 0) || (pos.y + (this.size * scale) / 2 > this.h)) {
			this.handleWalls(false); // call
		}
	}

	@SuppressWarnings("unused")
	private void handleWalls(boolean x) {

		if (x) {
			this.speed.x *= -1; // reverse
		}

		else
			this.speed.y *= -1;
	}
	
	// get current pos of fish
	public PVector getPos() {
		return this.pos;
	}

	public boolean hitWithMouse(MouseEvent e) {
        int newSize = size * scale;
        
        return  ( (Math.abs(e.getX() - pos.x) < newSize / 2) && (Math.abs(e.getY() - pos.y) < newSize / 2) );
    }
	
	public void grow() {
		if (this.scale <=1) {			
			this.scale++;
		}
	}
	
} // end fish