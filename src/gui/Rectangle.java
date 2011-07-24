package gui;

import processing.core.PApplet;
import squarification.RectInterface;

public class Rectangle implements RectInterface {

	private int area;
	private int dimX;
	private int dimY;
	private int startX;
	private int startY;

	PApplet parent;

	public Rectangle(int area, PApplet parent) {
		this.area = area;
		this.parent = parent;
	}

	@Override
	public int getArea() {
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

	public void display() {
		parent.fill(200);
		parent.rect(startX, startY, dimX, dimY);
	}
}
