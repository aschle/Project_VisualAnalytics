package testing;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import database.DBConnector;
import database.Parser;
import processing.core.PApplet;
import squarification.Squarification;
import gui.Button;
import gui.Rectangle;

public class TestSquarification extends PApplet {

	private static final long serialVersionUID = 1L;

	ArrayList<Rectangle> rects = new ArrayList<Rectangle>();
	HashMap<String, Button> buttons = new HashMap<String, Button>();

	// Area in pixel of the diagram
	int sum = 0;

	// Offset to center the diagram
	int offsetX;
	int offsetY;

	// Dimension of the whole area
	int w = 1000;
	int h = 700;

	// Buttons
	Button b1;

	int dimX;
	int dimY;

	boolean redraw = false;

	public void parse() throws Exception {

		DBConnector conn = DBConnector.getInstance();
		conn.init();

		Parser p = new Parser("data.csv");
		p.open();
		p.parse();
	}

	public void addButtons() {
		buttons.put("male", new Button("Male", this));
		buttons.put("female", new Button("Female", this));
	}

	public void setup() {

		try {
			parse();
			getDBvalues();
		} catch (Exception e) {
			e.printStackTrace();
		}

		size(w, h);
		smooth();
		noLoop();

		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
			}
			@Override
			public void mouseMoved(MouseEvent e) {
				redraw();
			}
		});

		addButtons();

		int borderX = 100;
		int borderY = 100;

		double dimX = w - 2 * borderX;
		double dimY = h - 2 * borderY;

		Squarification s = new Squarification();
		s.getSquarify(rects, dimX, dimY);

		for (int i = 0; i < rects.size(); i++) {
			rects.get(i).setOffset(borderX, borderY);
		}
		
		draw();
	}

	public void draw() {
		background(255);
		noStroke();

		for (int i = 0; i < rects.size(); i++) {
			rects.get(i).display();
		}

		for(Rectangle r : rects)
			r.mouseText();

		// b1.display();
	}

	public void mouseClicked() {
		// if (b1.mouseClicked()) {
		// redraw = true;
		//
		// // reset erverything
		// rects.clear();
		// sum = 0;
		// }
	}

	public void getDBvalues() throws Exception {

		Statement selectStmt = DBConnector.getInstance().connection
				.createStatement();
		ResultSet rs = selectStmt
				.executeQuery("select sum(number), name, category from straftat WHERE name <> 'Sonstiges' AND origin = 'A' GROUP BY name;");

		while (rs.next()) {
			rects.add(new Rectangle(rs.getInt(1), rs.getString(2), rs
					.getString(3).toCharArray()[0], this));
			sum = sum + rs.getInt(1);
		}
	}
}