package gui;

import java.awt.Color;

import processing.core.PApplet;
import testing.TestSquarification;

public class Button {

	public int dimX = 100;
	public int dimY = 50;
	public int startX;
	public int startY;
	private String title;
	private Color color = new Color(200,200,200);
	private static int margin = 2;

	PApplet parent;

	public Button(String title, int startX, int startY, PApplet parent) {
		this.title = title;
		this.startX = startX;
		this.startY = startY;
		this.parent = parent;
	}

	public void display() {
		parent.fill(color.getRed(), color.getGreen(), color.getBlue());
		parent.rect(startX, startY, dimX, dimY);
	}

	public void mouseClicked() {
		if (parent.mouseX > startX && parent.mouseX < startX + dimX
				&& parent.mouseY > startY && parent.mouseY < startY + dimY) {

			TestSquarification t = (TestSquarification) parent;
			t.getDBvalues2();
			parent.setup();
		}
	}
}
