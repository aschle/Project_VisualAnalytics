package testing;

import gui.RangeSlider;
import processing.core.PApplet;

public class TestRangeSlider extends PApplet {

	private static final long serialVersionUID = 1L;
	RangeSlider slider;

	public void setup() {
		size(400, 150);
		background(255);
		String[] labels = { "2007", "2008", "2009", "2010", "2011", "2012", "2012" };
		slider = new RangeSlider(50, 50, 7, labels, this);
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
