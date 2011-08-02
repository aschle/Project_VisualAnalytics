package gui;

import java.awt.Color;

import processing.core.PApplet;
import testing.TestSquarification;

public class Button {

	public int dimX = 100;
	public int dimY = 50;
	public int startX;
	public int startY;

	public boolean status = false;
	private String title;
	private Color colorActive = new Color(200, 200, 200);
	private Color colorUnactive = new Color(100,100,100);
	private static int margin = 2;

	PApplet parent;

	public Button(String title, PApplet parent){
		this.title = title;
		this.parent = parent;
	}
	
	public Button(String title, int startX, int startY, PApplet parent) {
		this.title = title;
		this.startX = startX;
		this.startY = startY;
		this.parent = parent;
	}

	public void display() {
		Color drawColor = new Color(0);
		if (status){
			drawColor = colorActive;
		}
		else {
			drawColor = colorUnactive;
		}
		parent.fill(drawColor.getRed(), drawColor.getGreen(), drawColor.getBlue());
		parent.rect(startX, startY, dimX, dimY);
	}

	public boolean mouseClicked() {
		if (parent.mouseX > startX && parent.mouseX < startX + dimX
				&& parent.mouseY > startY && parent.mouseY < startY + dimY) {
			status = !status;
			System.out.println(status);
			return true;
		}
		return false;
	}
}
