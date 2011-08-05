package testing;

import gui.TernarySlider;
import processing.core.PApplet;

public class TestSlider extends PApplet {

	private static final long serialVersionUID = 1L;
	TernarySlider slider;

	public void setup() {
		size(500, 500);
		background(255);
		String[] labels = { "male", "all", "female" };
		slider = new TernarySlider(50, 50, labels, this);
	}

	public void draw() {
		slider.display();
	}

	public void mouseDragged() {
		slider.moveBar();
	}

	public void mousePressed() {
		slider.mousePressed();
	}

	public void mouseReleased() {
		slider.mouseReleased();
	}
}
