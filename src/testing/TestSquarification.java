package testing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.DBConnector;
import database.Parser;
import processing.core.PApplet;
import squarification.Squarification;
import gui.Button;
import gui.Rectangle;

public class TestSquarification extends PApplet {

	private static final long serialVersionUID = 1L;

	ArrayList<Rectangle> rects = new ArrayList<Rectangle>();

	// Area in pixel of the diagram
	int sum = 0;

	// Offset to center the diagram
	int offsetX;
	int offsetY;

	// Dimension of the whole area
	int w = 1000;
	int h = 700;

	// Buttons
	//Button b1;
	//Button b2;
	
	// Boolean
	boolean first = false;
	
	int dimX;
	int dimY;

	public void setup() {
		size(w, h);
		smooth();
		background(255);

		//b1 = new Button("Ausländer", 20, 20, this);
		//b2 = new Button("Deutsche", b1.dimX + 40, 20, this);

		if (first == false) {
			// Read data.
			getDBvalues();
			first = true;
		}

		int borderX = 100, borderY = 100;

		double dimX = w - 2 * borderX;
		double dimY = h - 2 * borderY;
		
		double yS =Math.sqrt(((double)sum*dimY)/dimX);
		double xS = sum/yS;

		double trans = dimY/yS;

		Squarification s = new Squarification();
		s.getSquarify(rects, xS, yS);

		for (int i = 0; i < rects.size(); i++) {
			rects.get(i).setOffset(borderX, borderY);
			rects.get(i).setTransformationRatios(trans);
		}
	}

	public void draw() {
		background(255);
		noStroke();

		for (int i = 0; i < rects.size(); i++) {
			rects.get(i).display();
		}

		for (int i = 0; i < rects.size(); i++) {
			rects.get(i).mouseText();
		}

		//b1.display();
	}

	public void mouseClicked() {
		rects.clear();
		sum = 0;
		//b1.mouseClicked();
		//b2.mouseClicked();
	}

	public void getDBvalues() {

		try {

			DBConnector conn = DBConnector.getInstance();
			conn.init();

			Parser p = new Parser("data.csv");
			p.open();
			p.parse();

			Statement selectStmt = conn.connection.createStatement();
			ResultSet rs = selectStmt
					.executeQuery("select sum(number), name, category from straftat WHERE name <> 'Sonstiges' AND origin = 'A' GROUP BY name;");

			while (rs.next()) {
				rects.add(new Rectangle(rs.getInt(1), rs.getString(2), rs
						.getString(3).toCharArray()[0], this));
				sum = sum + rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getDBvalues2() {
		try {

			DBConnector conn = DBConnector.getInstance();
			conn.init();

			Parser p = new Parser("data.csv");
			p.open();
			p.parse();

			Statement selectStmt = conn.connection.createStatement();
			ResultSet rs = selectStmt
					.executeQuery("select sum(number), name, category from straftat WHERE origin == 'A' AND name <> 'Sonstiges' GROUP BY name;");

			while (rs.next()) {
				rects.add(new Rectangle(rs.getInt(1), rs.getString(2), rs
						.getString(3).toCharArray()[0], this));
				sum = sum + rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}