package gui;

import java.awt.Color;

import processing.core.PApplet;
import processing.core.PConstants;

public class RangeSlider {
	
	// Rounded Corners
	private int r = 10;

	// StartingPoint of the Slider (upper left corner of the scale)
	private int startX;
	private int startY;

	// Border (before the ticks start/end
	private int border = 20;

	// Scale
	private int sH = 10;
	private int sW = 440;
	private Color sColor = new Color(100, 100, 100);

	// Ticks
	private int tH = 25;
	private int tW = 25;
	private Color tColor = new Color(200, 200, 200);
	private int tickNumber;
	private int tickDistance;

	// StickPoints (x value of the stick points)
	private int[] stickPArray;

	// TicksStartPoints (x value where to start drawing the ticks)
	private int[] ticksXArray;
	private int startYT;

	// Bar to slide around
	private int bH = 20;
	private int bW = 20;
	private Color bColorInactive = new Color(50, 50, 50);
	private Color bColorActive = new Color(255, 210, 0);

	private int currentBarLeft;
	private int currentBarRight;
	private int barY;

	private int[] barPosition;

	// Range
	private int currentRangeX;
	private int rangeY;
	private int currentRangeW;
	private int rangeH = 10;
	private Color rangeColor = new Color(255, 180, 0);

	// State to keep track if a bar is active or not
	private boolean activeLeft = false;
	private boolean activeRight = false;

	// States
	private boolean[] state;
	private String[] stateLabel;

	private boolean samePosition = false;

	private PApplet parent;

	public RangeSlider(int startX, int startY, int tickNumber,
			String[] stateLabel, PApplet parent) {

		this.startX = startX;
		this.startY = startY;
		this.parent = parent;
		this.tickNumber = tickNumber;
		this.stateLabel = stateLabel;

		tickDistance = (sW - 2 * border) / (tickNumber - 1);
		stickPArray = new int[tickNumber];
		ticksXArray = new int[tickNumber];
		barPosition = new int[tickNumber];
		state = new boolean[tickNumber];

		// calculate Sticky Points (x)
		// -|----|----|--..--|-
		// P1 P2 .. PN
		for (int i = 0; i < tickNumber; i++) {
			stickPArray[i] = startX + border + i * tickDistance;
		}

		// start points for the ticks (x|y)
		// -|_|----|_|----|_|--..--|_|-

		for (int i = 0; i < tickNumber; i++) {
			ticksXArray[i] = stickPArray[i] - tW / 2;
		}

		startYT = startY - (tH - sH) / 2;

		// bars
		for (int i = 0; i < tickNumber; i++) {
			barPosition[i] = stickPArray[i] - bW / 2;
		}

		currentBarLeft = 0;
		currentBarRight = tickNumber - 1;
		barY = startY - (bH - sH) / 2;

		// range
		currentRangeW = stickPArray[tickNumber - 1] - stickPArray[0];
		currentRangeX = stickPArray[0];
		rangeY = startY + sH / 2 - rangeH / 2;

	}

	public void display() {

		// Label the ticks
		setColor(new Color(0));
		parent.textSize(14);
		parent.textAlign(PConstants.CENTER);
		for (int i = 0; i < tickNumber; i++) {
			parent.text(stateLabel[i], stickPArray[i], startYT - 5);
		}

		// Ticks
		setColor(tColor);
		for (int i = 0; i < tickNumber; i++) {
			roundRect(ticksXArray[i], startYT, tW, tH, r, r);
		}

		// Scale
		setColor(sColor);
		roundRect(startX, startY, sW, sH, r, r);

		// Range
		setColor(rangeColor);
		parent.rect(currentRangeX, rangeY, currentRangeW, rangeH);

		// Bar LEFT
		if (activeLeft) {
			setColor(bColorActive);
		} else {
			setColor(bColorInactive);
		}
		parent.ellipseMode(PConstants.CORNER);
		parent.ellipse(barPosition[currentBarLeft], barY, bW, bH);

		// Bar RIGHT
		if (activeRight) {
			setColor(bColorActive);
		} else {
			setColor(bColorInactive);
		}
		parent.ellipseMode(PConstants.CORNER);
		parent.ellipse(barPosition[currentBarRight], barY, bW, bH);

	}

	public void moveBar() {

		if (isCloseLeft(stickPArray[0])) {
			move(0);
		}

		if (isCloseRigth(stickPArray[tickNumber - 1])) {
			move(tickNumber - 1);
		}

		int num = closeTo();
		if (num != 0) {
			move(num);
		}

		// if they are above each other
		if (currentBarLeft == currentBarRight) {
			samePosition = true;

		} else {
			samePosition = false;
		}

		// calculate new range
		currentRangeW = barPosition[currentBarRight]
				- barPosition[currentBarLeft];
		parent.println("CBL " +currentBarLeft);
		currentRangeX = stickPArray[currentBarLeft];

	}

	private int closeTo() {

		int num = 0;

		for (int i = 1; i < tickNumber - 1; i++) {

			if (parent.mouseX > (stickPArray[i - 1] + (stickPArray[i] - stickPArray[i - 1]) / 2)
					&& parent.mouseX < (stickPArray[i] + (stickPArray[i + 1] - stickPArray[i]) / 2)) {
				num = i;

			}
		}
		return num;
	}

	private void move(int value) {

		if (activeLeft) {
			currentBarLeft = value;
		}

		if (activeRight) {
			currentBarRight = value;
		}

		setStates();

	}

	public void setStates() {
		int min = 0;
		int max = tickNumber - 1;

		if (currentBarLeft < currentBarRight) {
			min = currentBarLeft;
			max = currentBarRight;
		} else {
			min = currentBarRight;
			max = currentBarLeft;
		}

		for (int i = 0; i < tickNumber; i++) {
			if (i >= min && i <= max) {
				state[i] = true;
			} else {
				state[i] = false;
			}

		}
	}

	private boolean isCloseLeft(int stickPX) {
		if (parent.mouseX < (stickPX + (stickPArray[1] - stickPX) / 2)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isCloseRigth(int stickPX) {
		if (parent.mouseX > (stickPArray[tickNumber - 1] + (stickPX - stickPArray[tickNumber - 1]) / 2)) {
			return true;

		} else {
			return false;
		}
	}

	public void mousePressed() {

		if (samePosition) {

			if (isInBar(currentBarLeft)) {
				activeLeft = true;
			}
			return;
		}

		if (isInBar(currentBarLeft)) {
			activeLeft = true;
		}

		if (isInBar(currentBarRight)) {
			activeRight = true;
		}
	}

	public void mouseReleased() {
		if (activeLeft) {
			activeLeft = false;
		}
		if (activeRight) {
			activeRight = false;
		}
	}

	private boolean isInBar(int startX) {
		if (parent.mouseX > barPosition[startX]
				&& parent.mouseX < barPosition[startX] + bW
				&& parent.mouseY > barY && parent.mouseY < barY + bH) {
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

}
