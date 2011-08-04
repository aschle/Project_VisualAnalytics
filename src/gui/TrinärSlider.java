package gui;

import java.awt.Color;

import processing.core.PApplet;
import processing.core.PConstants;

public class TrinärSlider {

	// StartingPoint of the Slider (upper left corner of the scale)
	private int startX;
	private int startY;

	// Border (before the ticks start/end
	private int border = 20;

	// Scale
	private int sH = 8;
	private int sW = 150;
	private Color sColor = new Color(100, 100, 100);

	// Ticks
	private int tH = 25;
	private int tW = 25;
	private Color tColor = new Color(200, 200, 200);
	private int startXT1;
	private int startXT2;
	private int startXT3;
	private int startYT;

	// Bar to slide around
	private int bH = 20;
	private int bW = 20;
	private Color bColorInactive = new Color(50, 50, 50);
	private Color bColorActive = new Color(255, 210, 0);

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
	private String[] stateLabel = { "male", "all", "female" };

	private PApplet parent;

	public TrinärSlider(int startX, int startY, PApplet parent) {

		this.startX = startX;
		this.startY = startY;
		this.parent = parent;

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

		parent.smooth();
		parent.background(255);
		parent.noStroke();

		// Label the ticks
		setColor(new Color(0));
		parent.textAlign(PConstants.CENTER);
		parent.text(stateLabel[0], stickP1, startYT - 5);
		parent.text(stateLabel[1], stickP2, startYT - 5);
		parent.text(stateLabel[2], stickP3, startYT - 5);
		
		// Ticks
		setColor(tColor);
		roundRect(startXT1, startYT, tW, tH);
		roundRect(startXT2, startYT, tW, tH);
		roundRect(startXT3, startYT, tW, tH);
		
		// Scale
		setColor(sColor);
		roundRect(startX, startY, sW, sH);

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

	void roundRect(float x, float y, float w, float h) {
		float corner = w / 10f;
		float midDisp = w / 20f;

		parent.beginShape();
		parent.curveVertex(x + corner, y);
		parent.curveVertex(x + w - corner, y);
		parent.curveVertex(x + w + midDisp, y + h / 2f);
		parent.curveVertex(x + w - corner, y + h);
		parent.curveVertex(x + corner, y + h);
		parent.curveVertex(x - midDisp, y + h / 2f);

		parent.curveVertex(x + corner, y);
		parent.curveVertex(x + w - corner, y);
		parent.curveVertex(x + w + midDisp, y + h / 2f);
		parent.endShape();
	}
}
