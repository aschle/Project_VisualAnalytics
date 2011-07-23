package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {

	private static DBConnector instance = null;
	private static String driver = "org.sqlite.JDBC";
	private static String baseURL = "jdbc:sqlite:statistik.db";

	public static Connection connection = null;

	private DBConnector() {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(baseURL);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static DBConnector getInstance() {
		if (instance == null) {
			instance = new DBConnector();
		}
		return instance;
	}
	
	public static void init() throws SQLException{
		Statement stat = connection.createStatement();
		stat.executeUpdate("DROP TABLE IF EXISTS straftat;");
		stat.executeUpdate("DROP TABLE IF EXISTS category;");
		stat.executeUpdate("CREATE TABLE straftat ("
				+"category character(1),"
				+"name text,"
				+"origin character(1),"
				+"sex character(1),"
				+"age integer,"
				+"number integer);");
		
		stat.executeUpdate("CREATE TABLE category ("
				+"id character(1), "
				+"name text);");
		// 
		stat.executeUpdate("INSERT INTO category VALUES " +
				"('A', 'Straftaten gegen den Staat und die öffentliche Ordnung');");
		stat.executeUpdate("INSERT INTO category VALUES " +
				"('B', 'Straftaten gegen die sexuelle Selbstbestimmung');");
		stat.executeUpdate("INSERT INTO category VALUES " +
				"('C', 'Andere Straftaten gegen die Person (o.V.)');");
		stat.executeUpdate("INSERT INTO category VALUES " +
				"('D', 'Diebstahl und Unterschlagung');");
		stat.executeUpdate("INSERT INTO category VALUES " +
				"('E', 'Raub u. Erpressung und räuber. Angriff auf Kraftfahrer');");
		stat.executeUpdate("INSERT INTO category VALUES " +
				"('F', 'Andere Vermögensdelikte');");
		stat.executeUpdate("INSERT INTO category VALUES " +
				"('G', 'Gemeingefährliche einschl. Umweltstraftaten (o.V.)');");
		stat.executeUpdate("INSERT INTO category VALUES " +
				"('H', 'Straftaten im Straßenverkehr');");
		stat.executeUpdate("INSERT INTO category VALUES " +
				"('I', 'Straftaten nach anderen Bundes- u. Landesgesetzen');");
	}
}
