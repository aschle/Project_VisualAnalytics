package testing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.DBConnector;
import database.Parser;

import processing.core.PApplet;

import squarification.RectInterface;
import squarification.Squarification;

import gui.Rectangle;

public class TestSquarification extends PApplet {

	ArrayList<Rectangle> rects = new ArrayList<Rectangle>();

	// Area in pixel of the diagram
	int sum = 0;

	// Offset so center the diagram
	int offsetX;
	int offsetY;

	// Dimension of the whole area
	int w = 1024;
	int h = 786;

	// Dimension of the diagram
	int dimX = 920;
	int dimY;

	public void setup() {
		size(w, h);
		smooth();
		background(255);

		// Testdaten lasen
		getDBvalues();

		dimY = Math.round(sum / dimX);
		
		offsetX = Math.round((w - dimX) / 2);
		offsetY = Math.round((h - dimY) / 2);

		Squarification s = new Squarification();
		s.getSquarify(rects, dimX, dimY);

		for (int i = 0; i < rects.size(); i++) {
			rects.get(i).setOffset(offsetX, offsetY);
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
					.executeQuery("select sum(number), name from straftat WHERE name <> 'Sonstiges' GROUP BY name;");

			while (rs.next()) {
				rects.add(new Rectangle(rs.getInt(1), rs.getString(2), this));
				sum = sum + rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
