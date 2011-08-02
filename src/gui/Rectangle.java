package gui;

import java.awt.Color;

import processing.core.PApplet;
import processing.core.PFont;
import squarification.RectInterface;

public class Rectangle implements RectInterface {

	private double area;
	private int dimX;
	private int dimY;
	private int startX;
	private int startY;
	private String title;
	private Color color = new Color(0xFF0000);

	private int offsetX = 0;
	private int offsetY = 0;
	private static int margin = 2;

	PApplet parent;

	public Rectangle(double area, String title, char category, PApplet parent) {
		this.area = area;
		this.parent = parent;
		this.title = title;
		this.offsetX = 0;
		this.offsetY = 0;
		setColor(category);
	}

	@Override
	public double getArea() {
		return area;
	}

	@Override
	public void setDimention(int dimX, int dimY) {
		System.out.println("dim: " + dimX + " " + dimY);
		this.dimX = dimX;
		this.dimY = dimY;
	}

	@Override
	public void setStartPoint(int startX, int startY) {
		System.out.println("start: " + startX + " " + startY);
		this.startX = startX;
		this.startY = startY;
	}

	public void setColor(char category) {
		switch (category) {
		case 'A':
			color = new Color(0xFF0000);
			break;
		case 'B':
			color = new Color(0x000099);
			break;
		case 'C':
			color = new Color(0xFF7400);
			break;
		case 'D':
			color = new Color(0x00CC00);
			break;
		case 'E':
			color = new Color(0xFFF700);
			break;
		case 'F':
			color = new Color(0xC30083);
			break;
		case 'G':
			color = new Color(0xFFC900);
			break;
		case 'H':
			color = new Color(0x3914AF);
			break;
		case 'I':
			color = new Color(0x9F3300);
			break;
		default:
			break;
		}
	}

	public void setOffset(int offsetX, int offsetY) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		startX += offsetX;
		startY += offsetY;
	}

	public void display() {
		parent.fill(color.getRed(), color.getGreen(), color.getBlue());
		parent.rect((float) (startX + margin), (float) (startY + margin), 
				(float) (dimX - 2 * margin),
				(float) (dimY - 2 * margin));
	}

	public void mouseText() {
		if (parent.mouseX > startX
				&& parent.mouseX < startX + dimX
				&& parent.mouseY > startY 
				&& parent.mouseY < startY + dimY) {
			parent.fill(0);
			PFont f = parent.createFont("FFScala", 20);
			parent.textFont(f);
			parent.text(title, parent.mouseX, parent.mouseY);
		}
	}
}
