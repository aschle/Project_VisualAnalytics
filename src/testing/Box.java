package testing;

import processing.core.PApplet;

public class Box {

	PApplet parent;
	int size;
	String name;
	int posX;
	int posY;

	public Box(int size, String name, int posX, int posY, PApplet parent) {
		this.parent = parent;
		this.size = size;
		this.name = name;
		this.posX = posX;
		this.posY = posY;
	}

	public void display() {
		parent.fill(0);
		parent.rect(posX, posY, size, size);
	}

	public void mouseText() {
		if (parent.mouseX > posX && parent.mouseX < posX + size
				&& parent.mouseY > posY && parent.mouseY < posY + size) {
			parent.fill(200, 200, 200, 150);
			parent.noStroke();
			parent.rect(parent.mouseX-10, parent.mouseY-20, 150, 30);
			parent.fill(0);
			parent.text(name, parent.mouseX, parent.mouseY);
		}
	}
}
