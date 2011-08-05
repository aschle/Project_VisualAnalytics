package database;

import gui.Rectangle;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 
 * @author Alexa Schlegel
 * 
 */
public class Querying {

	public static ResultSet getInitialDBvalues() throws Exception {

		Statement selectStmt = DBConnector.getInstance().connection
				.createStatement();
		ResultSet rs = selectStmt
				.executeQuery("select sum(number), name, category from straftat WHERE name <> 'Sonstiges' AND origin = 'A' GROUP BY name;");
		return rs;
	}
}
