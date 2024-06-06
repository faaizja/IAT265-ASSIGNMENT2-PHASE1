package A2Pkg;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.TimerTask;
import java.awt.event.MouseAdapter;
import javax.swing.JPanel;
import javax.swing.Timer;

import processing.core.PVector;

public class SeaPanel extends JPanel implements ActionListener {

	Shark shark; // field to reference shark object as main character
	Sea sea; // field to reference the sea object to draw environment
//	Fish fish = null; // field to refer to fish objects to act as food in the sea

	private Dimension size;
	private Timer t;
	private static final int SPAWN_DELAY = 200;
	private int spawner = 0;
	private ArrayList<Fish> fishes; // array list to hold fish objects
	private MouseEvent mE; // new mouse event
	private boolean mouseIsDown = false;
	private ArrayList<Seaweed> weeds; // array list of sea weeds
	private static final int numWeeds = 5;

	public SeaPanel(Dimension initialSize) { // constructor
		super(); // call jpanel constructor

		MyMouseAdapter MMA = new MyMouseAdapter();
		addMouseListener(MMA);
		addMouseMotionListener(MMA);

		size = (Dimension) initialSize;

		sea = new Sea(new Color(40, 155, 165), new Color(40, 160, 185), new Color(40, 155, 190), initialSize.width,
				initialSize.height / 3, initialSize); // sea constructor

		// shark constructor
		shark = new Shark(initialSize.width / 2, initialSize.height / 2,
				Math.min(initialSize.width, initialSize.height) / 5, (int) (Math.random() * 2 + 4), // speed
				(int) (Math.random() * 2 + 4), // speed
				Util.randomColor(), initialSize.width, initialSize.height);

		// create array lists
		fishes = new ArrayList<Fish>();
		weeds = new ArrayList<Seaweed>();

		for (int z = 0; z < numWeeds; z++) {
			PVector position = new PVector(50 + size.width / numWeeds * z, size.height);
			weeds.add(new Seaweed(position, 10, 15, new Color(0, 100, 0), 10));
		}

		t = new Timer(1, this); // render animation at max fps
		t.start();

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		size = getSize();

		spawner++; // start fish spawn timer

		sea.setSize(size);

		setBackground(Color.DARK_GRAY);
		Graphics2D g2 = (Graphics2D) g;

		// draw out all objects
		sea.draw(g2);
		shark.draw(g2);

		for (Seaweed s : weeds) {
			s.draw(g2); // draw out sea weeds
		}

		// draw out all the fishes in the fish array list
		for (Fish fish : fishes) {
			fish.draw(g2);
		}

		if (spawner > SPAWN_DELAY) { // reset
			spawnFish(size); // spawn fish from timer
			spawner = 0;
		}

	} // method

	@Override
	public void actionPerformed(ActionEvent e) {
		// create an array list that stores the dead fishes to be removed after killed
		// by shark
		ArrayList<Fish> deadFishes = new ArrayList<>();

		// action logic
		this.shark.move(); // will call other methods in shark

		for (Seaweed sw : weeds) {
			sw.move(); // animate the seaweeds
		}

		///////////////////////////////////////////////////////////////////////////////////
		// PSEUDO CODE
		// LOOP THROUGH THE ARRAYLIST OF FISHES
		// USE A GETTER TO RECEIVE THE FISHES POSITION AND PASS IT TO THE SHARK
		// CALL A METHOD WITHIN SHARK THAT WILL CHASE THE FISH THAT WAS MOST RECENTLY CREATED AND ADDED TO THE ARRAYLIST
		// IF THE SHARK HAS EATEN A FISH, SEND THAT FISH TO ANOTHER ARRAY CONTAINING DEAD FISHES
		// CONSTANTLY REMOVE THE DEAD FISHES FROM THE ALIVE FISHES BY REMOVING FROM THE FISHES ARRAY
		for (int z = 0; z < fishes.size(); z++) {
			Fish f1 = fishes.get(z);
			f1.move(); // handles collision as well
			this.shark.chaseFish(f1.getPos()); // chases the fish

			if (this.shark.fishEaten) { // if shark has eaten the fish
				System.out.println("Fish eaten!");
				deadFishes.add(f1); // add this fish to dead fishes array
				this.shark.fishEaten = false;
				spawner = 0; // reset timer
			}

		} // for
		fishes.removeAll(deadFishes); // remove all the dead fishes from the array of fishes
		/////////////////////////////////////////////////////////////////////////////////////

		repaint();
	}

	// THIS METHOD WILL BE CALLED TO SPAWN A FISH IF THE TIMER REACHES 5 SECONDS
	private void spawnFish(Dimension initialSize) {
		System.out.println("Fish spawned");

		Fish fish = new Fish((int) Util.random(50, initialSize.width - 50),
				(int) Util.random(50, initialSize.height - 50), initialSize.width, initialSize.height,
				(int) Util.random(-5, 5), (int) Util.random(-5, 5));

		this.fishes.add(fish); // add the new fish to the fish array
	}

	// THIS METHOD CLICK A FISH TO EXIST WHEN THE USER CLICKS TWICE AT A LOCATION ON
	// THE SCREEN
	// RECEIVES MOUSES LOCATION
	private void clickAFish(Dimension initialSize, int x, int y) {
		System.out.println("Fish clicked into existence at: " + x + ", " + y);
		Fish fish = new Fish(x, y, initialSize.width, initialSize.height, (int) Util.random(-5, 5),
				(int) Util.random(-5, 5));

		this.fishes.add(fish); // add the clicked fish into the fish array
	}

	// THIS METHOD IS CALLED FROM THE MOUSERELEASED METHOD WHICH THEN CHECKS IF THE
	// MOUSE WAS CLICKED ON A FISH OBJECT
	// IF A FISH IS CLICKED IT INCREASES THE FISH'S SCALE TILL IT IS HALF OF THE SHARKS SIZE
	// IF THE CONTROL BUTTON IS DOWN AND A FISH IS CLICKED THEN THE FISH IS KILLED AND REMOVED
	private void mouseActions() {

		if (mE != null) {

			for (int i = 0; i < fishes.size(); i++) {

				Fish thisFish = fishes.get(i);

				if (thisFish.hitWithMouse(mE)) {

					thisFish.grow(); // increase fish size

					if (mE.isControlDown()) {
						fishes.remove(i);
					}

				}

			} // for

		} // outer if

	} // void

	// MOUSE ADAPTER CLASS
	private class MyMouseAdapter extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			mouseIsDown = true; // mouse is being held down
			mE = e;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			mouseIsDown = false;
			mE = e;
			mouseActions(); // when mouse is released a method is called that performs some actions
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			double mouseX = e.getX(); // mouse x coordinate received
			double mouseY = e.getY(); // mouse y coordinate received

			int timesClicked = e.getClickCount(); // how many times mouse is clicked

			if (timesClicked == 2) { // if clicked twice then create a fish at that location
				clickAFish(size, (int) mouseX, (int) mouseY);
			}

		} // void mouseClicked

	} // mouse adapter class

} // end class
