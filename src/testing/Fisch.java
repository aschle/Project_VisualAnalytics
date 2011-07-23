package testing;

import processing.core.PApplet;

public class Fisch extends PApplet {
	
	
	float x = -100;
	float y = -100;
	float fishHeight = 15;
	float fishWidth = 30;
	float fishFin = 10;

	float xspeed = 0.65f;

	public void setup() {
	  size(400, 400);  // define the size of the window
	  //frameRate(1);
	  smooth();
	}

	public void draw() {

	  background(0, 0, 100);// draw a blue background

	  //change the location of the fish
	  x = x + xspeed;

	  // define fish color
	  fill(100, 100, 255, 100);
	  noStroke();

	  //draw lots of fish
	  for (int i=0; i<10; i++) {

	    ellipse(x+50*i, y+50*i, fishWidth+i, fishHeight+i);
	    triangle((x+50*i)-(fishWidth+i)/2, (y+50*i), (x+50*i)-(fishWidth+i)/2-fishFin, (y+50*i)+fishFin, (x+50*i)-(fishWidth+i)/2-fishFin, (y+50*i)-fishFin);
	  }

	    stroke(0, 0, 80); //define middle blue
	    strokeWeight(2); // thickness of lines
	    fill(100, 100, 255); // fill shapes with light blue
	  
	    rectMode(CENTER);
	    rect(200, 330, 20, 120); // draw the lower part of the tower
	    ellipse(200, 245, 60, 60); // draw globe
	    triangle(197, 215, 200, 100, 203, 215);  // draw the antenna
	}

	  //draw one fish
	  //ellipse(x, y, fishWidth, fishHeight);
	  //triangle(x-fishWidth/2, y, x-fishWidth/2-fishFin, y+fishFin, x-fishWidth/2-fishFin, y-fishFin);



}
