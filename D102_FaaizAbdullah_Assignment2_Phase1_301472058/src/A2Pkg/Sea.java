package A2Pkg;

import processing.core.PVector;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

@SuppressWarnings("unused")
public class Sea {
	// this class acts as an image that is rendered as the background for the sea

	Color top; // private field for color of the top of the frame
	Color middle; // private field for color of the middle of the frame
	Color bottom; // private field for color of the bottom of the frame
	double waveW, waveH; // private fields for the width and height of the waves
	private int w, h;
	private Dimension size;
	public Sea(Color top, Color middle, Color bottom, int w, int h, Dimension size) {

		// constructor
		this.top = top;
		this.middle = middle;
		this.bottom = bottom;
		this.w = w; // width
		this.h = h; // height /3
		this.size = size;
	}

	 public void setSize(Dimension size) {
	        this.size = size;
	    }
	 
	public void draw(Graphics2D g) { // over here draw out sea environment
		// draw sea background using geom's geometric objects
		
		Rectangle2D.Double topRect = new Rectangle2D.Double(0,0, this.w, this.h);
		Rectangle2D.Double middleRect = new Rectangle2D.Double(0,this.h, this.w, this.h);
		Rectangle2D.Double bottomRect = new Rectangle2D.Double(0, this.h *2, this.w, this.h);
		
		g.setColor(this.top);
		g.fill(topRect);

		g.setColor(this.middle);
		g.fill(middleRect);

		g.setColor(this.bottom);
		g.fill(bottomRect);

	}

} // end class
