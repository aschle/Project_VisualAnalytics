package testing;

import gui.Rectangle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import database.DBConnector;

import processing.core.PApplet;
import squarification.Squarification;

public class MalteTestetSquarification extends PApplet {

	private static final long serialVersionUID = 1L;
	private ArrayList<Rectangle> rects = new ArrayList<Rectangle>();

	public void setup() {
		size(800, 600);
		smooth();
		background(255);

		getTags(rects, 800 * 600);

		Squarification s = new Squarification();
		s.getSquarify(rects, 800, 600);
	}

	public void draw() {
		background(255, 204, 0);
		for (Rectangle r : rects) {
			r.display();
			r.mouseText();
		}
	}

	private int getTags(List<Rectangle> retval, int totalArea) {
		try {
			DBConnector
					.setBaseURL("jdbc:sqlite:/home/malte/.gmp3view/medialib.db");
			DBConnector conn = DBConnector.getInstance();

			Statement selectStmt = conn.connection.createStatement();
			ResultSet rs;

			rs = selectStmt
					.executeQuery("SELECT SUM(number), tag FROM tags GROUP BY tag HAVING SUM(number) > 1000");

			int sum = 0;
			Map<String, Integer> h = new HashMap<String, Integer>();
			while (rs.next()) {
				h.put(rs.getString(2), rs.getInt(1));
				sum = sum + rs.getInt(1);
			}
			rs.close();
			selectStmt.close();

			// normalize
			double summe = 0;
			for (Entry<String, Integer> e : h.entrySet()) {
				double normalizedArea = ((double) e.getValue())
						* ((double) totalArea) / ((double) sum);
				summe += normalizedArea;
				retval.add(new Rectangle((int) normalizedArea, e.getKey(), this));
			}
			System.out.println("Summe = " + summe);
			System.out.println(800 * 600);

			return sum;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}
}
