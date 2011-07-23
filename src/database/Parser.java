package database;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Parser {

	private String[][] keys = { { "D", "m", "1" }, { "D", "m", "2" },
			{ "D", "m", "3" }, { "D", "m", "4" }, { "D", "m", "5" },
			{ "D", "m", "6" }, { "D", "m", "7" }, { "D", "w", "1" },
			{ "D", "w", "2" }, { "D", "w", "3" }, { "D", "w", "4" },
			{ "D", "w", "5" }, { "D", "w", "6" }, { "D", "w", "7" },
			{ "A", "m", "1" }, { "A", "m", "2" }, { "A", "m", "3" },
			{ "A", "m", "4" }, { "A", "m", "5" }, { "A", "m", "6" },
			{ "A", "m", "7" }, { "A", "w", "1" }, { "A", "w", "2" },
			{ "A", "w", "3" }, { "A", "w", "4" }, { "A", "w", "5" },
			{ "A", "w", "6" }, { "A", "w", "7" } };

	private BufferedReader reader;
	private String filename;

	private PreparedStatement insertStmt;

	private static DBConnector conn;

	public Parser(String filename) throws Exception {

		conn = DBConnector.getInstance();
		this.filename = filename;

		insertStmt = conn.connection
				.prepareStatement("INSERT INTO straftat values (?,?,?,?,?,?);");
	}

	public void open() throws UnsupportedEncodingException,
			FileNotFoundException {
		reader = new BufferedReader(new InputStreamReader(new FileInputStream(
				filename)));
	}

	public void parse() throws IOException, SQLException {

		String line = reader.readLine();
		while ((line = reader.readLine()) != null) {
			String[] lineArray = line.split("\t");
			String kategory = lineArray[0];
			String name = lineArray[1];
			addLineToDB(kategory, name, lineArray);
		}
	}

	public void addLineToDB(String kategory, String name, String[] data)
			throws SQLException {

		for (int i = 2; i < data.length; i++) {

			insertStmt.setString(1, kategory);
			insertStmt.setString(2, name);
			insertStmt.setString(3, keys[i - 2][0]);
			insertStmt.setString(4, keys[i - 2][1]);
			insertStmt.setString(5, keys[i - 2][2]);
			insertStmt.setString(6, data[i]);
			insertStmt.execute();
		}
	}
}
