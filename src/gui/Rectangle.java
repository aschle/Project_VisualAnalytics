package gui;

import processing.core.PApplet;
import squarification.RectInterface;

public class Rectangle implements RectInterface {

	private double area;
	private int dimX;
	private int dimY;
	private int startX;
	private int startY;
	private String title;
	
	private int offsetX;
	private int offsetY;
	private static int margin = 2;

	PApplet parent;

	public Rectangle(double area, String title, PApplet parent) {
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
	
	public void setOffset(int offsetX, int offsetY){
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		startX += offsetX;
		startY += offsetY;
	}

	public void display() {
		parent.fill(200);
		parent.rect(startX + margin, startY + margin, dimX - 2 * margin, dimY
				- 2 * margin);
	}

	public void mouseText() {
		if (parent.mouseX > startX && parent.mouseX < startX + dimX
				&& parent.mouseY > startY && parent.mouseY < startY + dimY) {
			parent.fill(200, 200, 200, 150);
			parent.rect(parent.mouseX - 10, parent.mouseY - 20, 150, 30);
			parent.fill(0);
			parent.text(title, parent.mouseX, parent.mouseY);
		}
	}
}
