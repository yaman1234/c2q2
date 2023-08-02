package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Methods that uses the data from progress database
 * 
 * @author badhikari
 *
 */
public class ProgressDB {

	private Connection conn = null;

	/**
	 * Method to make database connection
	 * 
	 * @return
	 * 
	 */
	public void connectToDB(String dbhost, String dbname, String dbport, String dbuser, String dbpass) {
		try {
			Class.forName("com.ddtek.jdbc.openedge.OpenEdgeDriver"); // Initializing OpenEdge Driver

			/*
			 * Making connection with progress database, User should be created from (
			 * Database Administration -> Admin -> Security -> Edit User list -> Add)
			 */
			conn = DriverManager.getConnection(
					"jdbc:datadirect:openedge://" + dbhost + ":" + dbport + ";databaseName=" + dbname, dbuser, dbpass);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Close db connection at the end of program
	 */
	public void closeDB() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * extract data from given field under given table name
	 * 
	 * @param tableName
	 * @param fieldName
	 * @return list of String
	 */
	public List<String> extractDataFromTable(String tableName, String fieldName) {
		List<String> dataFromDB = new ArrayList<>();
		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("select * from " + tableName)) {
			while (rs.next())
				dataFromDB.add(rs.getString(fieldName));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dataFromDB;
	}

	/**
	 * To get the program data from xprog table of certain field
	 * 
	 * @param program
	 * @param field
	 * @return data from particular field of xprog table of particular program
	 * 
	 */
	public String getProgramInfo(String program, String field) {
		String data = "";
		try (Statement stmt = conn.createStatement();
				ResultSet rs = stmt
						.executeQuery("select * from PUB.xprog where xprog.xproc = " + "\'" + program + "\'")) {
			while (rs.next()) {
				data = rs.getString(field);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * Method for returning the batch text based on the value returned by the
	 * progress database
	 * 
	 * @param data
	 * @return
	 */
	public String batchValue(String data) {
		if (data.equals("0")) {
			return "0 (No batch)";
		} else if (data.equals("1")) {
			return "1 (Ask for batch)";
		} else if (data.equals("2")) {
			return "2 (Must be in batch)";
		} else {
			return "3 (Must be In Batch IT also)";
		}
	}

	/**
	 * To check whether logged in user is admin or not
	 * 
	 * @param user
	 * @return
	 * 
	 */
	public String checkAdmin(String user) {
		String data = "";
		try (Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("select * from PUB.xuser where xuser.xusec = " + "\'" + user + "\'")) {
			while (rs.next()) {
				data = rs.getString("xuseladmin");
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (data.equals("0"))
			return "No";
		return "Yes";
	}

	/**
	 * To check if first batch is displayed or not
	 * 
	 * @param batchValue
	 * @param user
	 * @return
	 * 
	 */
	public boolean firstBatch(String batchValue, String user) {
		boolean firstbatchvalue = false;
		String newvalue = batchValue.split("\\s+")[0]; // Splitting string as batchvalue contains both name and its
		String data = ""; // description
		try (Statement stmt = conn.createStatement();
				ResultSet rs = stmt
						.executeQuery("select * from PUB.xbhead where xbhead.xbhec = " + "\'" + newvalue + "\'")) {

			while (rs.next()) {
				data = rs.getString("xbhetstart");
			}
			rs.close();
			stmt.close();
			int strSize = 0;
			String[] usergroups = {};
			// If start security is not *
			if (!data.equals("*")) {
				usergroups = data.split(",");
				strSize = usergroups.length;

				while (strSize != 0) {
					Statement stmts = conn.createStatement();
					ResultSet rss = stmt.executeQuery("select * from PUB.xugroup where xugroup.xusec = " + "\'" + user
							+ "\' and xugroup.xgroc=" + "\'" + usergroups[strSize - 1] + "\'");

					while (rs.next()) {
						Statement stmt1 = conn.createStatement();
						ResultSet rs1 = stmt1.executeQuery("select top 1 * from PUB.xbhead where xbhead.xbhetstart= "
								+ "\'" + data + "\' and xbhead.xbhec != 'A-SYNC'");
						while (rs1.next()) {
							String batchname = rs1.getString("xbhec"); // Getting the first batch value with that
																		// start
																		// security
							if (batchname.equals(newvalue)) {
								firstbatchvalue = true;
							}
						}
						rs1.close();
						stmt1.close();
						strSize--;
					}
					rss.close();
					stmts.close();
				}
			}
			// When the start security is *
			else {
				Statement stmt1 = conn.createStatement();
				ResultSet rs1 = stmt1.executeQuery("select top 1 * from PUB.xbhead where xbhead.xbhetstart= " + "\'"
						+ data + "\' and xbhead.xbhec != 'A-SYNC'");
				while (rs1.next()) {
					data = rs1.getString("xbhec");
					if (data.equals(newvalue)) {
						firstbatchvalue = true;
					}
				}
				rs1.close();
				stmt1.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return firstbatchvalue;
	}

	/**
	 * Method for checking the batch type of batch value
	 * 
	 * @param batchValue
	 * @return
	 * 
	 */
	public String batchTypeCheck(String batchValue) {
		String newvalue = batchValue.split("\\s+")[0]; // Splitting string as batch value contains both name and its
		String data = ""; // description
		try (Statement stmt = conn.createStatement();
				ResultSet rs = stmt
						.executeQuery("select * from PUB.xbhead where xbhead.xbhec = " + "\'" + newvalue + "\'")) {

			while (rs.next()) {
				data = rs.getString("xbtyc"); // Getting the batch type value
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * Method for checking whether the displayed printer is default printer or not
	 * 
	 * @param printerName
	 * @param xusec
	 * @return
	 * 
	 */
	public boolean defaultPrinter(String printerName, String xusec) {
		boolean result = false;
		try (Statement stmt = conn.createStatement();
				ResultSet rs = stmt
						.executeQuery("select * from PUB.xuser where xuser.xusec = " + "\'" + xusec + "\'")) {
			String data = "";
			String data1 = "";

			while (rs.next()) {
				data = rs.getString("xlprc"); // Default printer from xuser table
			}
			rs.close();
			stmt.close();

			if (!data.isEmpty()) {
				Integer strSize = 0;
				String[] printernames = {};
				try {
					printernames = data.split(",");

				} catch (Exception e) {
					printernames[0] = data;
				}
				strSize = printernames.length;
				while (strSize != 0) {
					Statement xlprm = conn.createStatement();
					ResultSet xlprmRS = xlprm.executeQuery("select * from PUB.xlprint where xlprint.xlprc = " + "\'"
							+ printernames[strSize - 1] + "\'");

					String prtName = "";
					while (xlprmRS.next()) {
						prtName = xlprmRS.getString("xlprm"); // Printer Description
					}
					// Checking whether the printer description of printer shown in print window is
					// same as that of xuser table's
					if (prtName.contains(printerName)) {
						result = true;
					}
					strSize--;
					xlprm.close();
				}
			}

			// If default printer is blank
			else {
				Statement stmt1 = conn.createStatement();
				ResultSet rs1 = stmt1.executeQuery("select top 1 * from PUB.xlprint"); // Getting the first printer
				while (rs1.next()) {
					data1 = rs1.getString("xlprm");
				}
				if (data1.equals(printerName)) {
					result = true;
				}
				stmt1.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Method to check whether printer has condensed checkbox enabled
	 * 
	 * @param printerName
	 * @return
	 * 
	 */
	public boolean condensedPrinter(String printerName) {
		try (Statement stmt = conn.createStatement();
				ResultSet rs = stmt
						.executeQuery("select * from PUB.xlprint where xlprint.xlprm = " + "\'" + printerName + "\'")) {
			String data = "";

			while (rs.next()) {
				data = rs.getString("xlprlcond"); // boolean value if the condensed is enabled for the passed
													// printername
			}
			rs.close();
			stmt.close();

			if (data.equals("1")) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Method to check whether printer has Landscape Checkbox enabled
	 * 
	 * @param printerName
	 * @return
	 * 
	 */
	public boolean landscapePrinter(String printerName) {
		try (Statement stmt = conn.createStatement();
				ResultSet rs = stmt
						.executeQuery("select * from PUB.xlprint where xlprint.xlprm = " + "\'" + printerName + "\'")) {
			String data = "";

			while (rs.next()) {
				data = rs.getString("xlprlland"); // boolean value if the landscape is enabled or not
			}
			rs.close();
			stmt.close();

			if (data.equals("1")) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Method for getting the batch line number of latest batchline
	 * 
	 * @param batch
	 * @return
	 * 
	 */
	public String batchLine(String batch) {
		String newvalue = batch.split("\\s+")[0]; // Splitting string as batchvalue contains both name and its
		String data = ""; // description
		try (Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("select top 1 * from PUB.xbline where xbline.xbhec = " + "\'"
						+ newvalue + "\'" + "order by xbline.xblin desc")) {

			while (rs.next()) {
				data = rs.getString("xblin"); // Getting batch line sequence number
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * Method for getting Default Filename
	 * 
	 * @param xusec
	 * @return String (Filename)
	 * 
	 */
	public String defaultFileName(String xusec) {
		String data = "";
		try (Statement stmt = conn.createStatement();
				ResultSet rs = stmt
						.executeQuery("select * from PUB.xuser where xuser.xusec = " + "\'" + xusec + "\'")) {

			while (rs.next()) {
				data = rs.getString("xusetfile");
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * Changing the batch type of program of xprog table
	 * 
	 * @param program
	 * @param BatchType
	 * 
	 */
	public void changeBatchType(String program, String BatchType) {
		try (PreparedStatement up = conn.prepareStatement(
				"update PUB.xprog set xpronbatch = " + BatchType + " where xprog.xproc = " + "\'" + program + "\'")) {
			up.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * To get xuseladmin field value from xuser table
	 * 
	 * @param user
	 * @return
	 * 
	 */
	public String xuselAdminValue(String user) {
		String data = "";
		try (Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("select * from PUB.xuser where xuser.xusec = " + "\'" + user + "\'")) {

			while (rs.next()) {
				data = rs.getString("xuseladmin");
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * Changing the xuseladmin (Administrator) field of xuser table
	 * 
	 * @param user
	 * @param AdminUser
	 * 
	 */
	public void changeAdminField(String user, String AdminUser) {
		try (PreparedStatement up = conn.prepareStatement(
				"update PUB.xuser set xuseladmin = " + AdminUser + " where xuser.xusec = " + "\'" + user + "\'")) {
			up.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Changing Only in batch value
	 * 
	 * @param program
	 * @param batch
	 * 
	 */
	public void changeOnlyInBatch(String program, String batch) {

		try (PreparedStatement up = conn.prepareStatement("update PUB.xprog set xbhec = " + "\'" + batch + "\'"
				+ " where xprog.xproc = " + "\'" + program + "\'")) {
			up.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get Data from certain field of certain table
	 * 
	 * @param tableName
	 * @param field
	 * @param value
	 * @return
	 * 
	 */
	public String retrieveData(String tableName, String field, String value) {
		String data = "";
		try (Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(
						"select * from PUB." + tableName + " where " + field + " = " + "\'" + value + "\'")) {

			while (rs.next()) {
				data = rs.getString(field);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * Returning First value of certain Field of certain table
	 * 
	 * @param tableName
	 * @param fieldName
	 * @return
	 * 
	 */
	public String getFirstRecord(String tableName, String fieldName) {
		String data = "";
		try (Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("select top 1 " + fieldName + " from PUB." + tableName)) {

			while (rs.next()) {
				data = rs.getString(fieldName);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * Checking whether certain index field is defined as unique
	 * 
	 * @param fieldName
	 * @return
	 * 
	 */
	public boolean uniqueField(String fieldName) {
		String data = "";
		try (Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(
						"select * from PUB.\"_Index\" where \"_Index\".\"_Unique\" = '1' and \"_Index-Name\"=" + "\'"
								+ fieldName + "\'")) {

			while (rs.next()) {
				data = rs.getString("_Index-Name");
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (data.equals(fieldName))
			return true;
		return false;
	}

	/**
	 * Checking whether the value of particular field of xwidget table matches the
	 * passed string
	 * 
	 * @param program
	 * @param widgetName
	 * @param fieldName
	 * @return
	 * 
	 */
	public boolean xwidgetField(String program, String widgetName, String fieldName) {
		String data = "";
		try (Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("select * from PUB.xwidget where xproc = " + "\'" + program
						+ "\' and xwidc = " + "\'" + widgetName + "\'")) {

			while (rs.next()) {
				data = rs.getString(fieldName);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (data.equals("1"))
			return true;
		return false;
	}

	/**
	 * Retrieve main query name of certain program
	 * 
	 * @param program
	 * @param fieldName
	 * @return
	 * 
	 */
	public String mainQueryProgram(String program, String fieldName) {

		String data = "";
		try (Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(
						"select * from PUB.xquery where xproc = " + "\'" + program + "\' and xquelmain ='1'")) {

			while (rs.next()) {
				data = rs.getString("xquec");
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * Return External table of the query (if any)
	 * 
	 * @param program
	 * @param query
	 * @return
	 * 
	 */
	public String externalTable(String program, String query) {
		String data = "";
		try (Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("select * from PUB.xquery where xproc = " + "\'" + program
						+ "\' and xquec =" + "\'" + query + "\'")) {

			while (rs.next()) {
				data = rs.getString("xquetexternal");
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * Get the random data from certain field of certain table
	 * 
	 * @param tableName
	 * @param fieldName
	 * @return
	 * 
	 */
	public String generateRandomData(String tableName, String fieldName, String conditionValue, String conditionField) {
		String data = "";
		try (Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
			ResultSet rs = null;
			if (conditionValue.isEmpty()) {
				rs = stmt.executeQuery("select " + fieldName + " from PUB." + tableName);
			} else {
				rs = stmt.executeQuery("select " + fieldName + " from PUB." + tableName + " where " + conditionField
						+ "= '" + conditionValue + "'");
			}
			int rowcount = 0;
			Random rand = new Random();

			while (rs.next()) {
				rowcount++;
			}
			if (rowcount != 0) {
				int randomcount = rand.nextInt(rowcount) + 1;
				while (rs.previous()) {
					if (randomcount == rs.getRow()) {
						data = rs.getString(fieldName);
					}
				}
			} else if (rowcount == 1) {
				while (rs.previous()) {
					data = rs.getString(fieldName);
				}
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * Checking the display name field if not checked
	 * 
	 * @param xproc
	 * @return
	 */
	public String checkDisplayName() {
		String data = "";
		try (Statement stmt = conn.createStatement();

				ResultSet rs = stmt.executeQuery("select top 1 * from PUB.xsystem")) {

			while (rs.next()) {
				data = rs.getString("xsysltitle");
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * Changing the display name boolean value from database
	 * 
	 * @param yes_no
	 * 
	 */
	public void changeDisplayName(String yes_no) {

		try (PreparedStatement up = conn.prepareStatement("update PUB.xsystem set xsysltitle ='" + yes_no + "\'")) {
			up.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Return the program description of given program
	 * 
	 * @param program
	 * @return
	 * 
	 */
	public String getProgramDescription(String program) {
		String data = "";
		try (Statement stmt = conn.createStatement();

				ResultSet rs = stmt.executeQuery("select top 1 * from PUB.xlabel where xproc='" + program + "\' ")) {

			while (rs.next()) {
				data = rs.getString("xlabtlabel");
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * Getting the value of Checkin / out of system default-> X/E files parameters
	 * 
	 * @return
	 * 
	 */
	public String systemCheckInOut() {
		String data = "";
		try (Statement stmt = conn.createStatement();

				ResultSet rs = stmt.executeQuery("select * from PUB.xsystem ")) {

			while (rs.next()) {
				data = rs.getString("xsyslcheckin");
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * Changing the value of Checkin / out of system default-> X/E files parameters
	 * 
	 * @param yes_no
	 * 
	 */
	public void changeSystemCheckInOut(String yes_no) {
		try (PreparedStatement up = conn.prepareStatement("update PUB.xsystem set xsyslcheckin ='" + yes_no + "\'")) {
			up.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * private ResultSet executeQuery(String query) { try (Statement stmt =
	 * conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) { return rs;
	 * } catch (SQLException e) { e.printStackTrace(); return null; } }
	 */
}
