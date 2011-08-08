package gui;

import java.awt.Color;
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

	// InfoBox
	InfoBox infoBox;

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

		infoBox = new InfoBox(title, this, parentDia, parentMain);
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

	public void showInfoBox() {

		if (isInRect()) {
			infoBox.display();
		}
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
