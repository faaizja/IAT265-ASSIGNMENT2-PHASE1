package A2Pkg;

import processing.core.PVector;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class Shark {

	// this class will create a shark with its attributed and behaviors

	public PVector pos, speed; // private fields for holding its position and moving velocity
	double bodyW, bodyH; // private fields for holding its body sizes
	private double scale; // private field for holding the scale factor for the shark getting larger
	double headW, headH; // private fields for holding head size
	boolean fishPresent; // public field to detect if a fish is in sight
	private int size; // shark size
	private Color c;
	private int w, h;
	public boolean fishEaten = false;

	public Shark(int x, int y, int size, int speedx, int speedy, Color c, int w, int h) {
		// constructor
		pos = new PVector(x, y); // initial shark coordinates
		this.size = size; // shark size
		speed = new PVector(speedx, speedy); // new shark speed and direction
		this.c = c; // shark color
		scale = 1.25; // get 1.25x larger each time shark eats fish
		this.w = w;
		this.h = h;
	}

	public void draw(Graphics2D g) {
		// draw shark here using geom geometric primitives

		AffineTransform af = g.getTransform(); // push()

		g.translate((int) pos.x, (int) pos.y);
//		g.rotate(speed.heading());
		g.scale(scale, scale);

		if (speed.x < 0) // if shark hits right edge flip image towards left and vice versa
			g.scale(-1, 1);

		// shark body
		g.setColor(this.c);
		Ellipse2D.Double body = new Ellipse2D.Double(0 -this.size / 2, 0, this.size, this.size / 2);
		g.fill(body);

		// eye
		g.setColor(Color.red);
	    Polygon eye = new Polygon();
	    eye.addPoint(25, 20);
	    eye.addPoint(35, 10);
	    eye.addPoint(45, 20);
	    g.fillPolygon(eye);

		// tail
	    g.setColor(this.c);
	    Polygon tail = new Polygon();
	    tail.addPoint(-this.size + 10, this.size / 2 - 25);
	    tail.addPoint(-this.size + 40, this.size / 2);
	    tail.addPoint(-this.size + 70, this.size / 2 - 25);
	    g.fillPolygon(tail);

		// mouth
	    g.setColor(Color.black);
	    Line2D.Double mouth = new Line2D.Double(this.size / 2 - 2, this.size / 3 - 5, this.size / 2 - 30, this.size / 3 - 2);
	    g.draw(mouth);

		g.setTransform(af); // pop()
	}

	public void move() {

		this.pos.add(this.speed);

		// call edge handler if necessary

		if ((pos.x + this.size > w)) {
			this.pos.x = this.w - (this.size + 25);
			this.handleWalls(true); // call
		}

		if (pos.x * scale - this.size / 2 < 30) {
			this.pos.x = (this.size + 10);
			this.handleWalls(true); // call
		}

		if ((pos.y * scale < 0) || (pos.y * scale - this.size / 2 > this.h - 10)) {
			this.handleWalls(false); // call
		}

	} // end method

	@SuppressWarnings("unused")
	public void handleWalls(boolean x) { // receive either speed.x or speed.y and switch its direction
		// if true reverse x else y
		if (x) {
			this.speed.x *= -1; // reverse
		}

		else
			this.speed.y *= -1;

	} // end method

	public boolean chaseFish(PVector fPos) {

		PVector target = PVector.sub(fPos, this.pos); // create target by subtracting distance
		target.setMag(10);
		speed = target.copy();
//		speed.limit(5);

		// when fish is eaten reset the shark speed to a random value
		if (PVector.dist(this.pos, fPos) <= this.size / 4.5) {
			fishEaten = true;
			speed = new PVector((int) Math.random() * 2 + -3, (int) Math.random() * 2 + 3);
		}

		return fishEaten;
	}

} // end class