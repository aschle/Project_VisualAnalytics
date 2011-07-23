package testing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.DBConnector;
import database.Parser;

import processing.core.PApplet;

public class Test extends PApplet {

	ArrayList<Integer> values = new ArrayList<Integer>();
	ArrayList<String> text = new ArrayList<String>();
	Box[] boxen;
	int h;
	int space = 10;

	public void setup() {

		getDBvalues();
		boxen = new Box[values.size()];

		int spY = space;
		int spX = space;

		for (int i = 0; i < values.size(); i++) {
			int dim = ((int) sqrt(values.get(i) / 10));

			boxen[i] = new Box(dim, text.get(i), spX, spY, this);
			h = (int) (h + dim);
			spY = spY + dim + space;
		}

		h = h + (values.size() + 1) * space;
		size(500, h);
		background(255, 204, 0);
	}

	public void draw() {
		background(255, 204, 0);

		for (int i = 0; i < boxen.length; i++) {
			boxen[i].display();
			boxen[i].mouseText();
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
				values.add(rs.getInt(1));
				text.add(rs.getString(2));
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
