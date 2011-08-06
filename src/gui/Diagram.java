package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.sql.ResultSet;
import java.util.ArrayList;

import database.Querying;
import squarification.Squarification;

public class Diagram {

	// Slider
	public TernarySlider genderSlider;
	public TernarySlider originSlider;
	public RangeSlider ageSlider;

	// Dimension of the whole area
	int w;
	int h;

	// Offset
	int offsetX;
	int offsetY;

	// Dimension of the diagram area
	double dimX;
	double dimY;

	int areaSartX;
	int areaStartY;

	// Dimension of the slider area
	int dimSliderX;
	int dimSliderY;

	// Starting-point of the slider area
	int startSliderAreaX;
	int startSliderAreaY;

	// Borders
	int borderX = 20;
	int borderYtop = 50;
	int boderYbottom = 50;

	// Space between diagram and slider
	int space = 5;

	// Rectangles
	ArrayList<Rectangle> rects = new ArrayList<Rectangle>();

	// Total Sum (whole area)
	int sum;

	// PApplet to draw on
	Main parent;

	public Diagram(int w, int h, Main parent) {
		this.w = w;
		this.h = h;
		this.parent = parent;
	}

	/**
	 * Setup loads the initial data from the database. Calculates the borders
	 * and dimension of the slider-area. Sets up the sliders. And does the first
	 * squarification with the initial data.
	 */
	public void setup() {

		// calculate all borders and areas
		dimX = w - 2 * borderX;
		int dimYTmp = h - borderYtop - boderYbottom;
		dimY = (dimYTmp / 8) * 6;

		areaSartX = borderX + offsetX;
		areaStartY = borderYtop + offsetY;

		dimSliderX = (int) dimX - 4;
		dimSliderY = (int) (dimYTmp - dimY - 4);

		startSliderAreaX = areaSartX + 2;
		startSliderAreaY = (int) (borderYtop + dimY + space + offsetY);

		addListener();
		setupSlider();

		// load data
		try {
			loadData(genderSlider.getState(), originSlider.getState(),
					ageSlider.getState());
		} catch (Exception e) {
			System.out.println("Error: Loading data from database failed.");
			e.printStackTrace();
			System.exit(1);
		}

		doSquarification();
		display();
	}

	/**
	 * Set the x and y offset of the whole diagram. Its the offset to the paretn
	 * object(PApplet).
	 * 
	 * @param offsetX
	 *            offset in x direction
	 * @param offsetY
	 *            offset in y direction
	 */
	public void setOffset(int offsetX, int offsetY) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

	/**
	 * Display the diagram.
	 */
	public void display() {

		// color of the slider area
		parent.fill(220);

		// draw slider areas
		parent.rect(startSliderAreaX, startSliderAreaY, dimSliderX, dimSliderY
				- 2 * space);

		// draw the sliders
		genderSlider.display();
		originSlider.display();
		ageSlider.display();

		// draw the squarified retangles
		for (int i = 0; i < rects.size(); i++) {
			rects.get(i).display();
		}

		// display mouse Text (Categorys)
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

		if (parent.mouseX > areaSartX && parent.mouseX < areaSartX + dimX
				&& parent.mouseY > areaStartY
				&& parent.mouseY < areaStartY + dimY) {

			for (Rectangle r : rects) {
				r.mouseKlick();
			}

			parent.redraw();
			return;
		}

		genderSlider.mouseReleased();
		originSlider.mouseReleased();
		ageSlider.mouseReleased();

		// redraw everything
		boolean[] gender = genderSlider.getState();
		boolean[] origin = originSlider.getState();
		boolean[] age = ageSlider.getState();

		// reset everything
		rects = new ArrayList<Rectangle>();
		sum = 0;

		try {
			loadData(gender, origin, age);
		} catch (Exception e) {
			System.out.println("Error, while loading new data from the DB.");
			e.printStackTrace();
			System.exit(1);
		}

		doSquarification();

		parent.redraw();
	}

	/**
	 * Sets up the Gender, Origin and Age Slider. Does the positioning and
	 * labeling.
	 */
	private void setupSlider() {

		int sliderBorderTop = 40;
		int sliderBorderLeft = 20;

		int startXgender = startSliderAreaX + sliderBorderLeft;
		int startYgender = startSliderAreaY + sliderBorderTop;

		String[] labelGender = { "Männer", "Alle", "Frauen" };
		String[] labelOrigin = { "Deutsch", "Alle", "Ausländer" };
		String[] labelAge = { "14-18", "18-21", "21-25", "25-30", "30-40",
				"40-50", "50+" };

		genderSlider = new TernarySlider(startXgender, startYgender,
				labelGender, parent);

		originSlider = new TernarySlider(startXgender + genderSlider.getsW()
				+ (int)(4.7 * sliderBorderLeft), startYgender, labelOrigin, parent);

		ageSlider = new RangeSlider(startXgender, startYgender
				+ (int) (1.5 * sliderBorderTop), 7, labelAge, parent);
	}

	/**
	 * Adds a MouseMotionListener, only if the mouse is Dragged or Moved the are
	 * gets repainted.
	 */
	private void addListener() {

		parent.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				parent.redraw();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				parent.redraw();
			}
		});
	}

	/**
	 * Loads the data from the database. Uses the states of the slider to
	 * retrieve the filterd data.
	 * 
	 * @param gender
	 *            state of the gender slider
	 * @param origin
	 *            state if the origin slieder
	 * @param age
	 *            state of the age slider
	 * @throws Exception
	 */
	private void loadData(boolean[] gender, boolean[] origin, boolean[] age)
			throws Exception {

		ResultSet rs = Querying.getDBvaluesByFilter(gender, origin, age);

		while (rs.next()) {
			rects.add(new Rectangle(rs.getInt(1), rs.getString(2), rs
					.getString(3).toCharArray()[0], this));
			sum = sum + rs.getInt(1);
		}
	}

	/**
	 * Does the first squarification with the default values.
	 */
	private void doSquarification() {

		Squarification s = new Squarification();
		s.getSquarify(rects, dimX, dimY);

		for (int i = 0; i < rects.size(); i++) {
			rects.get(i).setOffset(areaSartX, areaStartY);
		}
	}
}
