package gui;

import java.awt.Color;
import squarification.RectInterface;

public class SubRectangle implements RectInterface {

	private double area;
	private int dimX;
	private int dimY;
	private int startX;
	private int startY;
	public String title; // name
	private Color color = new Color(0xAAAAAA);

	private int offsetX = 0;
	private int offsetY = 0;
	private static int margin = 1;

	Diagram parentDia;
	Main parentMain;
	Rectangle parentRect;

	public SubRectangle(double area, String title, char category,
			Rectangle parentRect, Diagram parent, Main parentApplet) {
		this.area = area;
		this.parentRect = parentRect;
		this.parentDia = parent;
		this.parentMain = parentApplet;
		this.title = title;
		this.offsetX = 0;
		this.offsetY = 0;
		setColor(category);
	}

	public void setColor(char category) {
		switch (category) {
		case 'A':
			color = new Color(0x68CBE7); // blue
			break;
		case 'B':
			color = new Color(0xA66600); // orange
			break;
		case 'C':
			color = new Color(0xB9A733); // yellow
			break;
		case 'D':
			color = new Color(0xE73A3A); // red
			break;
		case 'E':
			color = new Color(0xA4E639); // green
			break;
		case 'F':
			color = new Color(0x65CC33); // dark green
			break;
		case 'G':
			color = new Color(0xD699D3); // purple
			break;
		case 'H':
			color = new Color(0xB3B3B3); // grey
			break;
		case 'I':
			color = new Color(0x5C86D9); // blue
			break;
		default:
			break;
		}
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
		parentDia.parentMain.fill(color.getRed(), color.getGreen(),
				color.getBlue(), 180);
		parentDia.parentMain.rect((float) (startX + margin),
				(float) (startY + margin), (float) (dimX - 2 * margin),
				(float) (dimY - 2 * margin));
	}

	private boolean isInRect() {

		if (parentMain.mouseX > startX && parentMain.mouseX < startX + dimX
				&& parentMain.mouseY > startY
				&& parentMain.mouseY < startY + dimY) {
			return true;
		} else
			return false;

	}
}
