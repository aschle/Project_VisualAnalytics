package gui;

import java.io.File;

import database.DBConnector;
import database.Parser;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

public class Main extends PApplet {

	private static final long serialVersionUID = -6818220594093253964L;

	// File
	File file;

	// Height and Width of the whole area
	int w;
	int h;

	// Diagrams (left and right)
	Diagram dia1;
	Diagram dia2;

	// Dimension of the diagram
	int diaW;
	int diaH;

	// Borders (half the display area)
	int borderXleft = 25;
	int borderXright = 20;
	int borderYbottom = 50;
	int borderYtop = 25;

	PFont f;
	PFont fbold;

	public void setup() {

		// Schrift laden
		f = createFont("FFScala", 20);
		fbold = createFont("FFScala-Bold-12.vlw", 16);
		textFont(f);

		// fill the whole screen
		w = screenWidth;
		h = screenHeight;

		// if there is no DB file, parse the file into the DB
		file = new File("statistik.db");

		if (!file.exists()) {

			// parse data into database
			try {
				DBConnector conn = DBConnector.getInstance();
				conn.init();

				Parser p = new Parser("data.csv");
				p.open();
				p.parse();

			} catch (Exception e) {
				System.out.println("Error while parsing data into database.");
				e.printStackTrace();
				System.exit(1);
			}
		}

		smooth();

		// for saving some useless drawing
		noLoop();
		size(w, h);

		diaW = w / 2 - borderXleft - borderXright;
		diaH = h - borderYtop - borderYbottom;

		dia1 = new Diagram(diaW, diaH, this);
		dia2 = new Diagram(diaW, diaH, this);
		dia1.setOffset(borderXleft, borderYtop);
		dia2.setOffset(w / 2 + borderXleft, borderYtop);
		dia1.setup();
		dia2.setup();
	}

	public void draw() {
		background(255);
		noStroke();

		dia1.display();
		dia2.display();
		fill(0);

		// Quellenangabe:
		int space = 30;
		textAlign(PConstants.LEFT);
		textSize(14);
		text("Quelle: Statistisches Bundesamt Deutschland, Strafverfolgungsstatistik 2009",
				dia1.areaSartX, dia1.startSliderAreaY + dia1.dimSliderY + space);
		textAlign(PConstants.RIGHT);
		text("Alexa Schlegel, Freie Universität Berlin",
				(int) (dia2.areaSartX + dia2.dimX), dia1.startSliderAreaY
						+ dia1.dimSliderY + space);

		// Überschrift
		textAlign(PConstants.CENTER);
		textSize(14);
		text("Anzahl der Verurteilten nach Arte der Straftat, Alter, Geschlecht und Herkunft",
				w / 2, dia1.areaSartX + 2 * space);
		textSize(26);
		text("Strafverfolgungsstatistik-Explorer", w / 2, dia1.areaSartX
				+ space);

	}

	/**
	 * This is important for the sliders. To move the bars of the slider.
	 */
	public void mouseDragged() {
		if (isInArea(dia1.startSliderAreaX, dia1.startSliderAreaY,
				dia1.dimSliderX, dia1.dimSliderY)) {
			dia1.mouseDragged();
		}

		if (isInArea(dia2.startSliderAreaX, dia2.startSliderAreaY,
				dia2.dimSliderX, dia2.dimSliderY)) {
			dia2.mouseDragged();
		}
	}

	/**
	 * This is importaint for activating a slider bar.
	 */
	public void mousePressed() {

		if (isInArea(dia1.startSliderAreaX, dia1.startSliderAreaY,
				dia1.dimSliderX, dia1.dimSliderY)) {
			dia1.mousePressed();
		}

		if (isInArea(dia2.startSliderAreaX, dia2.startSliderAreaY,
				dia2.dimSliderX, dia2.dimSliderY)) {
			dia2.mousePressed();
		}
	}

	/**
	 * This is important for unactivating a slider bar. And for showing more
	 * info inside the rectangles.
	 */
	public void mouseReleased() {

		if (isInArea(dia1.areaSartX, dia1.areaStartY, (int) dia1.w,
				(int) dia1.h)) {
			dia1.mouseReleased();
		}

		if (isInArea(dia2.areaSartX, dia2.areaStartY, (int) dia2.w,
				(int) dia2.h)) {
			dia2.mouseReleased();
		}
	}

	/**
	 * Check if a mouse event was in a ceratin area. For checking if it was
	 * inside the right or left diagram.
	 * 
	 * @param x
	 *            x position of the starting point
	 * @param y
	 *            y position of the starting point
	 * @param w
	 *            with of the area
	 * @param h
	 *            heigth of the area
	 * @return
	 */
	private boolean isInArea(int x, int y, int w, int h) {
		if (mouseX > x && mouseX < x + w && mouseY > y && mouseY < y + h) {
			return true;
		} else
			return false;
	}
}
