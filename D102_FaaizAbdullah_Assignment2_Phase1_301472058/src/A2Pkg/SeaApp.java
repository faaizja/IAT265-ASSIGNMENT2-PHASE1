package A2Pkg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class SeaApp extends JFrame {

	public SeaApp(String title) {
		// create a new frame for sea app
		super(title);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		int width = 590; // increased by 40 to account for 20px border on each side
		int height = 540; // increased by 40 to account for 20px border on each side
		int x = (int) ((screenSize.getWidth() - width) / 2);
		int y = (int) ((screenSize.getHeight() - height) / 2);
		this.setBounds(x, y, width, height);

		// instantiating our SeaPanel
		SeaPanel panel = new SeaPanel(this.getSize());
		
		//create border
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// adding it to the current frame
		this.add(panel, BorderLayout.CENTER);
 
		// displaying the frame
		this.setVisible(true);
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		SeaApp app = new SeaApp("Sharky eater!"); // create
	}

} // end class
