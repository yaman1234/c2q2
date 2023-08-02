package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

/**
 * This class contains methods to handle login automatically
 * 
 * @author sanish
 *
 */

public class HandleAuthentication extends UtilBase {

	/**
	 * Logging in by passing credentials in URL (Works only for HTTP)
	 * 
	 * @param driver
	 * @return
	 */
	public static void loginUsingURLA2() {
		// getting login details from excel file
		String username = "", password = "", url = "";
		try {
			username = ExcelRead.getData(2, 1, 2);
			password = ExcelRead.getData(2, 2, 2);
			url = ExcelRead.getData(2, 3, 2);
		} catch (Exception e) {
			System.err.println("Error reading file!!");
			e.printStackTrace();
			// halts the program if any exception occurred
			System.exit(0);
		}

		url = "http://" + username + ":" + password + "@" + url;
		driver.get(url);
	}

	public static void loginUsingURLA4() throws InterruptedException {
		// getting login details from excel file
		String username = "", password = "", url = "";
		try {
			username = ExcelRead.getData(2, 1, 0);
			password = ExcelRead.getData(2, 2, 0);
			url = ExcelRead.getData(2, 3, 0);
			
		} catch (Exception e) {
			System.err.println("Error reading file!!");
			e.printStackTrace();
			// halts the program if any exception occurred
			System.exit(0);
		}

		url = "http://" + username + ":" + password + "@" + url;
		driver.get(url);
	}

	/**
	 * handling authentication pop ups with autoIt scripts, for effective
	 * handling call this method before navigating to a URL
	 */
	public static void loginUsingScript() {
		// alternative of using Runtime.getRuntime();
		Desktop desk = Desktop.getDesktop();
		File f = null;
		String browserName = "";

		try {
			browserName = ExcelRead.getData(18, 1, 0).toLowerCase();
		} catch (Exception e1) {
			System.err.println("Error reading file!!");
			e1.printStackTrace();
		}

		switch (browserName) {
		case "chrome":
			f = new File(System.getProperty("user.dir") + "\\auth\\chrome.exe");
			break;

		case "firefox":
			f = new File(System.getProperty("user.dir") + "\\auth\\firefox.exe");
			break;

		case "ie":
			f = new File(System.getProperty("user.dir") + "\\auth\\edge.exe");
			break;

		case "edge":
			f = new File(System.getProperty("user.dir") + "\\auth\\edge.exe");
			break;

		default:
			f = new File(System.getProperty("user.dir") + "\\auth\\chrome.exe");
			break;
		}

		try {
			desk.open(f);
		} catch (IOException e) {
			System.err.println("Error occurred opening file located " + f);
			e.printStackTrace();
			// halts the program if any exception occurred
			System.exit(0);
		}
	}

	// usually handles any login
	/**
	 * Login using robot that does copy-paste into text boxes
	 */
	public static void loginUsingRobot() {

		String username = "", password = "";

		try {
			// define username password location here
			username = ExcelRead.getData(2, 1, 0);
			password = ExcelRead.getData(2, 2, 0);

		} catch (Exception e) {
			System.err.println("Error reading file!!");
			e.printStackTrace();
			// halts the program if any exception occurred
			// System.exit(0);
		}

		// copy paste username & password
		try {
			RobotClass.copyPaste(username, password);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
