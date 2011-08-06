package gui;

import java.awt.Color;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import squarification.RectInterface;

public class SubRectangle implements RectInterface {

	private double area;
	private int dimX;
	private int dimY;
	private int startX;
	private int startY;
	private String title; // name
	private Color color = new Color(0xAAAAAA);

	private int offsetX = 0;
	private int offsetY = 0;
	private static int margin = 1;

	Diagram parent;

	public SubRectangle(double area, String title, char category, Diagram parent) {
		this.area = area;
		this.parent = parent;
		this.title = title;
		this.offsetX = 0;
		this.offsetY = 0;
	}

	@Override
	public double getArea() {
		return area;
	}

	@Override
	public void setDimention(int dimX, int dimY) {
		this.dimX = dimX;
		this.dimY = dimY;
	}

	@Override
	public void setStartPoint(int startX, int startY) {
		this.startX = startX;
		this.startY = startY;
	}

	public void setOffset(int offsetX, int offsetY) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		startX += offsetX;
		startY += offsetY;
	}

	public void display() {
		parent.parent.fill(color.getRed(), color.getGreen(), color.getBlue(), 180);
		parent.parent.rect((float) (startX + margin), (float) (startY + margin),
				(float) (dimX - 2 * margin), (float) (dimY - 2 * margin));
	}

	public void mouseText() {
		if (parent.parent.mouseX > startX && parent.parent.mouseX < startX + dimX
				&& parent.parent.mouseY > startY && parent.parent.mouseY < startY + dimY) {
			parent.parent.fill(0);
			PFont f = parent.parent.createFont("FFScala", 20);
			parent.parent.textFont(f);
			parent.parent.textAlign(PConstants.CENTER);
			parent.parent.text(title, parent.parent.mouseX, parent.parent.mouseY);
		}
	}

}
