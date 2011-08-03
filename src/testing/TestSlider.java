package testing;

import gui.TrinärSlider;
import processing.core.PApplet;

public class TestSlider extends PApplet {

	private static final long serialVersionUID = 1L;
	TrinärSlider slider;
	
	public void setup() {
		size(500, 500);
		background(255);
		slider = new TrinärSlider(50, 50, this);
	}

	public void draw() {
		slider.display();
	}
	
	public void mouseDragged(){
		slider.moveBar();
	}
	
	public void mousePressed(){
		slider.mousePressed();
	}
	
	public void mouseReleased(){
		slider.mouseReleased();
	}
}
