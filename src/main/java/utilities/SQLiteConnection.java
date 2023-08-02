package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class contains methods to manipulate SQLite databases
 * 
 * @author sanish
 */
public class SQLiteConnection {
	private static Connection conn = null;
	private static String fullPath = "", dbName = "";

	/**
	 * This method establishes connection with the SQLite database
	 */
	private static void connectDB() {
		try {
			// db parameters
			String url = "jdbc:sqlite:" + fullPath;
			// System.out.println(fullPath);
			// create a connection to the database
			conn = DriverManager.getConnection(url);
			System.out.println("Connection to SQLite database '" + dbName + "' has been established.");

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * This method closes the connection with the SQLite database
	 */
	private static void closeDB() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}

	/**
	 * Creates new SQLite database file with given name
	 * 
	 * @param databaseName
	 */
	public static void createDB(String databaseName) {
		File newDB;
		dbName = databaseName;
		// full path of the DB with the extension
		String fullName = System.getProperty("user.dir") + "\\testData\\" + databaseName + ".db";

		try {
			newDB = new File(fullName);

			if (newDB.exists()) {
				newDB.delete();
			}

			newDB.createNewFile();
			fullPath = newDB.getAbsolutePath();
			// System.out.println(fullPath);
			System.out.println("Database named '" + databaseName + "' created successfully!!");
		} catch (Exception e) {
			System.err.println("Error creating the database '" + databaseName + "'");
			System.err.println(e.getMessage());
		}
	}

	public static void createTable() {

	}

	public static void executeQuery() {

	}

	/**
	 * Reads login credentials from the database table
	 * 
	 * @param sourceDBName
	 * @param tableName
	 * @param id
	 * @return
	 */
	public static String[] readCredentialsFromDB(String sourceDBName, String tableName, int id) {

		// SQL query to view data from given table
		String sql = "SELECT * FROM " + tableName;
		File sourceDB;
		dbName = sourceDBName;
		String[] credentials = new String[2];
		// full path of the DB with the extension
		fullPath = System.getProperty("user.dir") + "\\testData\\" + sourceDBName + ".db";

		try {
			sourceDB = new File(fullPath);

			if (!sourceDB.exists()) {
				System.err.println("Database file '" + sourceDBName + "' doesn't exist!!");
				throw new FileNotFoundException();
			}

			connectDB();
			// System.out.println(fullPath);
		} catch (Exception e) {
			System.err.println("Error reading the database '" + sourceDBName + "'");
			System.err.println(e.getMessage());
		}

		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
			// loop through the result set
			while (rs.next()) {
				if (id == rs.getInt("id")) {
					credentials[0] = rs.getString("username");
					credentials[1] = rs.getString("password");
					break;
				}
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		closeDB();
		return credentials;
	}
}
