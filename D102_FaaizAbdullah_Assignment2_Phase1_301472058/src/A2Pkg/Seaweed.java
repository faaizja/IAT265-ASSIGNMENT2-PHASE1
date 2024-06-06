package A2Pkg;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import processing.core.PVector;

public class Seaweed {
    private PVector position; // position of the seaweed base
    private float width; // width of the seaweed segments
    private float height; // height of the seaweed segments
    private Color color; // color of the seaweed
    private float swayAngle; // angle for swaying motion
    private float swaySpeed; // speed of the swaying motion
    private int segments; // number of segments for the seaweed
    private Ellipse2D.Double[] segmentShapes; // pre-created segment shapes

    public Seaweed(PVector position, float width, float height, Color color, int segments) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.color = color;
        this.swayAngle = 0;
        this.swaySpeed = 0.05f; // adjust for desired speed
        this.segments = segments;
        this.segmentShapes = new Ellipse2D.Double[segments];

        // Pre-create segment shapes to avoid creating them repeatedly in the draw loop
        for (int i = 0; i < segments; i++) {
            segmentShapes[i] = new Ellipse2D.Double(0, 0, width, height);
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(color);

        float currentX = position.x;
        float currentY = position.y;

        for (int i = 0; i < segments; i++) {
            float angle = swayAngle + i * 0.2f;
            float sway = (float) Math.sin(angle) * width / 2;

            Ellipse2D.Double segment = segmentShapes[i];
            segment.x = currentX - width / 2 + sway;
            segment.y = currentY - height;

            g.fill(segment);

            currentY -= height * 0.8; // slightly overlapping segments
        }
    }

    public void move() {
        swayAngle += swaySpeed;
        if (swayAngle > Math.PI * 2) {
            swayAngle -= Math.PI * 2; // reset the swayAngle to keep it within a full cycle
        }
    }
    
} // class