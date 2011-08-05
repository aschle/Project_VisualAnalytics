package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBConnector;
import database.Parser;
import database.Querying;

import processing.core.PApplet;
import squarification.Squarification;

/**
 * This is the main class, which presents the main visualization.
 * 
 * @author Alexa Schlegel
 * 
 */
public class Main extends PApplet {

	private static final long serialVersionUID = 1L;

	// Slider
	TernarySlider genderSlider;
	TernarySlider originSlider;
	RangeSlider ageSlider;

	// Offset to center the diagram
	int offsetX;
	int offsetY;

	// Dimension of the whole area
	int w = 1000;
	int h = 700;

	// Dimension of the diagram area
	double dimX;
	double dimY;

	// Dimension of the slider area
	int dimSliderX;
	int dimSliderY;

	int startSliderAreaX;
	int startSliderAreaY;

	// Borders
	int borderX = 100;
	int borderYtop = 100;
	int boderYbottom = 50;

	// Space between diagram and slider
	int space = 5;

	// File
	File file;

	// Rectangles
	ArrayList<Rectangle> rects = new ArrayList<Rectangle>();

	// Total Sum
	int sum;

	public void setup() {

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

		// load the initial data
		try {
			loadData();
		} catch (Exception e) {
			System.out.println("Error, while loading date from the DB.");
			e.printStackTrace();
			System.exit(1);
		}

		// calculate all borders and areas
		dimX = w - 2 * borderX;
		int dimYTmp = h - borderYtop - boderYbottom;
		dimY = (dimYTmp / 8) * 6;

		dimSliderX = (int) dimX - 4;
		dimSliderY = (int) (dimYTmp - dimY - 4);

		startSliderAreaX = borderX + 2;
		startSliderAreaY = (int) (borderYtop + dimY + space);

		smooth();
		noLoop();
		addListener();
		setupSlider();
		size(w, h);

		Squarification s = new Squarification();
		s.getSquarify(rects, dimX, dimY);

		for (int i = 0; i < rects.size(); i++) {
			rects.get(i).setOffset(borderX, borderYtop);
		}

		draw();
	}

	public void draw() {

		background(255);
		noStroke();

		fill(220);
		// draw slider ares
		rect(startSliderAreaX, startSliderAreaY, dimSliderX, dimSliderY - 2
				* space);

		genderSlider.display();
		originSlider.display();
		ageSlider.display();

		for (int i = 0; i < rects.size(); i++)
			rects.get(i).display();

		for (Rectangle r : rects)
			r.mouseText();
	}

	public void mouseDragged() {
		genderSlider.moveBar();
		originSlider.moveBar();
		ageSlider.moveBar();
	}

	public void mousePressed() {
		genderSlider.mousePressed();
		originSlider.mousePressed();
		ageSlider.mousePressed();
	}

	public void mouseReleased() {
		genderSlider.mouseReleased();
		originSlider.mouseReleased();
		ageSlider.mouseReleased();
	}

	private void setupSlider() {

		int sliderBorderTop = 40;
		int sliderBorderLeft = 20;
		int startXgender = startSliderAreaX + sliderBorderLeft;
		int startYgender = startSliderAreaY + sliderBorderTop;
		String[] labelGender = { "male", "all", "female" };
		String[] labelOrigin = { "German", "all", "Alien" };
		String[] labelAge = { "14-18", "18-21", "21-25", "25-30", "30-40",
				"40-50", "50+" };

		ageSlider = new RangeSlider(startXgender, startYgender
				+ (int)(1.5*sliderBorderTop), 7, labelAge, this);

		genderSlider = new TernarySlider(startXgender, startYgender,
				labelGender, this);

		originSlider = new TernarySlider(startXgender + genderSlider.getsW()
				+ 2 * sliderBorderLeft, startYgender, labelOrigin, this);

	}

	private void addListener() {

		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				redraw();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				redraw();
			}
		});
	}

	private void loadData() throws Exception {

		ResultSet rs = Querying.getInitialDBvalues();

		while (rs.next()) {
			rects.add(new Rectangle(rs.getInt(1), rs.getString(2), rs
					.getString(3).toCharArray()[0], this));
			sum = sum + rs.getInt(1);
		}
	}

}
