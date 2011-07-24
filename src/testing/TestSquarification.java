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
	int sum = 0;

	public void setup() {
		size(600, 800);
		smooth();
		background(255);

		getDBvalues();

		Squarification s = new Squarification();
		s.getSquarify(rects, 1000, sum/1000);
		
	}

	public void draw() {

		for (int i = 0; i < rects.size(); i++) {
			rects.get(i).display();
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
					.executeQuery("select sum(number), category.name from straftat, category WHERE category.id = straftat.category GROUP BY category;");

			while (rs.next()) {
				rects.add(new Rectangle(rs.getInt(1), this));
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
