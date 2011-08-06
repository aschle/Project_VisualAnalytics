package database;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 
 * @author Alexa Schlegel
 * 
 */
public class Querying {

	private static String mainQuery = "select sum(number), category.name, category from straftat, category "
			+ "WHERE straftat.category = category.id ";

	private static String mainQueryGroupBy = "GROUP BY category;";

	private static String mainSubQuery = "select sum(number), straftat.name, category.id from straftat, category WHERE straftat.category = category.id AND category.name = ";
	private static String mainSubQueryGroupBy = " GROUP BY straftat.name;";

	private static String originForeigner = "AND straftat.origin = 'A' ";
	private static String originGerman = "AND straftat.origin = 'D' ";

	private static String sexMale = "AND straftat.sex = 'm' ";
	private static String sexFemale = "AND straftat.sex = 'w' ";

	/**
	 * Returns a Result set, which consists of total sums of all categories. No
	 * filtering is used.
	 * 
	 * @return
	 * @throws Exception
	 */
	public static ResultSet getInitialDBvalues() throws Exception {

		Statement selectStmt = DBConnector.getInstance().connection
				.createStatement();
		ResultSet rs = selectStmt.executeQuery(mainQuery
				.concat(mainQueryGroupBy));
		return rs;
	}

	/**
	 * Returns a ResultSet, which consists of total sums of all categories, but
	 * limited by the filters.
	 * 
	 * @param gender
	 *            gender filter <male,all,female>
	 * @param origin
	 *            origin filter <german,all,non-german>
	 * @param age
	 *            age filter <1,2,3,4,5,6,7>
	 * @return ResaultSet containing the requested date
	 * @throws Exception
	 */
	public static ResultSet getDBvaluesByFilter(boolean[] gender,
			boolean[] origin, boolean[] age) throws Exception {

		String query = "";

		query = query.concat(mainQuery);
		query = query.concat(buildQuery(gender, origin, age));
		query = query.concat(mainQueryGroupBy);

		Statement selectStmt = DBConnector.getInstance().connection
				.createStatement();
		ResultSet rs = selectStmt.executeQuery(query);

		return rs;
	}

	public static ResultSet getInitialSubDBvalues(String category)
			throws Exception {

		String query = "";
		query = query.concat(mainSubQuery);
		query = query.concat("'");
		query = query.concat(category);
		query = query.concat("'");
		query = query.concat(mainSubQueryGroupBy);

		Statement selectStmt = DBConnector.getInstance().connection
				.createStatement();
		ResultSet rs = selectStmt.executeQuery(query);

		return rs;
	}

	public static ResultSet getSubDBvaluesByFilter(String category, boolean[] gender,
			boolean[] origin, boolean[] age) throws Exception {

		String query = "";
		query = query.concat(mainSubQuery);
		query = query.concat("'");
		query = query.concat(category);
		query = query.concat("'");
		query = query.concat(buildQuery(gender, origin, age));
		query = query.concat(mainSubQueryGroupBy);

		Statement selectStmt = DBConnector.getInstance().connection
				.createStatement();
		ResultSet rs = selectStmt.executeQuery(query);

		return rs;
	}

	private static String buildQuery(boolean[] gender, boolean[] origin,
			boolean[] age) {

		String result = "";
		result = result.concat(genderBuilder(gender));
		result = result.concat(originBuilder(origin));
		result = result.concat(ageBuilder(age));

		return result;
	}

	private static String genderBuilder(boolean[] states) {

		String result = "";

		if (states[0] == true) {
			result = result.concat(sexMale);
		}

		if (states[2] == true) {
			result = result.concat(sexFemale);
		}

		return result;
	}

	private static String originBuilder(boolean[] states) {

		String result = "";

		if (states[0] == true) {
			result = result.concat(originGerman);
		}

		if (states[2] == true) {
			result = result.concat(originForeigner);
		}
		return result;
	}

	private static String ageBuilder(boolean[] states) {

		String result = "AND (";
		boolean isFirst = true;

		for (int i = 0; i < states.length; i++) {

			if (states[i] == true) {
				if (isFirst) {
					result = result.concat(" straftat.age = " + "'" + (i + 1)
							+ "'");
					isFirst = false;
				} else {
					result = result.concat(" OR straftat.age = " + "'"
							+ (i + 1) + "'");
				}
			}
		}
		result = result.concat(" ) ");

		return result;
	}
}
