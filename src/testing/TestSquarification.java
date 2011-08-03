package testing;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import database.DBConnector;
import database.Parser;
import processing.core.PApplet;
import squarification.Squarification;
import gui.Button;
import gui.Rectangle;
import guicomponents.GButton;

public class TestSquarification extends PApplet {

	private static final long serialVersionUID = 1L;

	ArrayList<Rectangle> rects = new ArrayList<Rectangle>();

	Button male = new Button("Male", 10, 10, this);
	Button female = new Button("Female", 10, 70, this);

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

		for (int i = 0; i < rects.size(); i++)
			rects.get(i).display();

		for (Rectangle r : rects)
			r.mouseText();

		male.display();
		female.display();

	}
	
	public void mouseClicked() {
		 male.mouseClicked();
		 female.mouseClicked();
		 redraw();
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