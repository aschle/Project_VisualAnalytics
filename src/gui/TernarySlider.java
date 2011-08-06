package gui;

import java.awt.Color;

import processing.core.PApplet;
import processing.core.PConstants;

/**
 * The TernarySlider is a Slider with 3 discrete steps. It consists of a scale,
 * ticks and the bar, which is moved around. The starting point of the slider is
 * the upper left corner of the scale. You can configure the slider, which
 * means: * the dimension of the slider itself is done by change the dimension
 * of the scale * dimension of the ticks, and bar * colors of scale and ticks *
 * active/inactive color of the bar * roundness * border (where the ticks
 * start/end)
 * 
 * @author Alexa Schlegel
 * 
 */
public class TernarySlider {

	// Rounded Corners
	private int r = 10;

	// StartingPoint of the Slider (upper left corner of the scale)
	private int startX;
	private int startY;

	// Border (before the ticks start/end)
	private int border = 20;

	// Scale
	private int sH = 10;

	private int sW = 170;
	private Color sColor = new Color(100, 100, 100);

	// Ticks
	private int tH = 25;
	private int tW = 25;
	private Color tColor = new Color(180, 180, 180);
	private int startXT1;
	private int startXT2;
	private int startXT3;
	private int startYT;

	// Bar to slide around
	private int bH = 20;
	private int bW = 20;
	private Color bColorInactive = new Color(0x339900);
	private Color bColorActive = new Color(0x7DCC00);

	// Range of the Bar
	private int currentStartBX; // this one moves around
	private int startBX;
	private int startBY;

	private boolean active = false;

	// StickPoints
	private int stickP1;
	private int stickP2;
	private int stickP3;

	// States
	private boolean[] state = { false, true, false };
	private String[] stateLabel;
	
	// Changed
	private boolean changed = false;

	private PApplet parent;

	public TernarySlider(int startX, int startY, String[] stateLabel,
			PApplet parent) {

		this.startX = startX;
		this.startY = startY;
		this.parent = parent;

		this.stateLabel = stateLabel;

		// calculate Sticky Points
		// -|----|----|-
		// P1 P1 P3
		stickP1 = startX + border;
		stickP2 = startX + sW / 2;
		stickP3 = startX + sW - border;

		// start points for the ticks
		// -|_|----|_|----|_|-
		startXT1 = stickP1 - tW / 2;
		startXT2 = stickP2 - tW / 2;
		startXT3 = stickP3 - tW / 2;
		startYT = startY - (tH - sH) / 2;

		// start points for the bar
		startBX = stickP1 - bW / 2;
		startBY = startY - (bH - sH) / 2;

		// start by default in the middle
		currentStartBX = stickP2 - bW / 2;
	}

	public void display() {

		// Label the ticks
		setColor(new Color(0));
		parent.textSize(14);
		parent.textAlign(PConstants.CENTER);
		parent.text(stateLabel[0], stickP1, startYT - 5);
		parent.text(stateLabel[1], stickP2, startYT - 5);
		parent.text(stateLabel[2], stickP3, startYT - 5);

		// Ticks
		setColor(tColor);
		roundRect(startXT1, startYT, tW, tH, r, r);
		roundRect(startXT2, startYT, tW, tH, r, r);
		roundRect(startXT3, startYT, tW, tH, r, r);

		// Scale
		setColor(sColor);
		roundRect(startX, startY, sW, sH, r, r);

		// Bar
		if (active) {
			setColor(bColorActive);
		} else {
			setColor(bColorInactive);
		}
		parent.ellipseMode(PConstants.CORNER);
		parent.ellipse(currentStartBX, startBY, bW, bH);
	}

	public void moveBar() {

		if (active) {

			if (closeToP1()) {
				currentStartBX = startBX;
				state[0] = true;
				state[1] = false;
				state[2] = false;
			}

			if (closeToP2()) {
				currentStartBX = stickP2 - bW / 2;
				state[0] = false;
				state[1] = true;
				state[2] = false;
			}

			if (closeToP3()) {
				currentStartBX = stickP3 - bW / 2;
				state[0] = false;
				state[1] = false;
				state[2] = true;
			}
		}
	}

	public boolean closeToP1() {
		if (parent.mouseX < (stickP1 + (stickP2 - stickP1) / 2)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean closeToP2() {
		if (parent.mouseX > (stickP1 + (stickP2 - stickP1) / 2)
				&& parent.mouseX < (stickP2 + (stickP3 - stickP2) / 2)) {
			return true;

		} else {
			return false;
		}
	}

	public boolean closeToP3() {
		if (parent.mouseX > (stickP2 + (stickP3 - stickP2) / 2)) {
			return true;

		} else {
			return false;
		}

	}

	public void mousePressed() {
		if (isInBar()) {
			active = true;
		}
	}

	public void mouseReleased() {
		active = false;
	}

	private boolean isInBar() {
		if (parent.mouseX > currentStartBX
				&& parent.mouseX < currentStartBX + bW
				&& parent.mouseY > startBY && parent.mouseY < startBY + bH) {
			return true;
		} else {
			return false;
		}
	}

	private void setColor(Color c) {
		parent.fill(c.getRed(), c.getGreen(), c.getBlue());
	}

	void roundRect(float x, float y, float w, float h, float rx, float ry) {
		parent.beginShape();
		parent.vertex(x, y + ry); // top of left side
		parent.bezierVertex(x, y, x, y, x + rx, y); // top left corner

		parent.vertex(x + w - rx, y); // right of top side
		parent.bezierVertex(x + w, y, x + w, y, x + w, y + ry); // top right
																// corner

		parent.vertex(x + w, y + h - ry); // bottom of right side
		parent.bezierVertex(x + w, y + h, x + w, y + h, x + w - rx, y + h); // bottom
		// right

		parent.vertex(x + rx, y + h); // left of bottom side
		parent.bezierVertex(x, y + h, x, y + h, x, y + h - ry); // bottom left
																// corner

		parent.endShape(PConstants.CLOSE);
	}

	public void setsW(int sW) {
		this.sW = sW;
	}

	public int getsW() {
		return sW;
	}

	public boolean[] getState() {
		return state;
	}
	
	public boolean hasChanged(){
		return changed;
	}
}