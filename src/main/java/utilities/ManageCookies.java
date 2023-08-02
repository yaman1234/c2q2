package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.openqa.selenium.Cookie;

/**
 * This class contains methods to save and load cookies to/from file
 * 
 * @author sanish
 *
 */

public class ManageCookies extends UtilBase {

	private static Set<Cookie> cookies;

	/**
	 * Save cookies from current instance of the web driver
	 * 
	 * @param driver
	 */
	public static void saveCookies() {

		// create file named Cookies to store Login Information
		File cookieFile = new File("Cookies.data");
		try {
			// Delete old file if exists
			cookieFile.delete();
			cookieFile.createNewFile();
			FileWriter fileWrite = new FileWriter(cookieFile);
			BufferedWriter buffWrite = new BufferedWriter(fileWrite);
			Set<Cookie> cookies = driver.manage().getCookies();

			// loop for getting the cookie information
			for (Cookie cookie : cookies) {
				buffWrite.write((cookie.getName() + ";" + cookie.getValue() + ";" + cookie.getDomain() + ";"
						+ cookie.getPath() + ";" + cookie.getExpiry() + ";" + cookie.isSecure()));
				buffWrite.newLine();
			}
			buffWrite.close();
			fileWrite.close();

		} catch (Exception ex) {
			System.err.println("Error occurred saving cookie into a file!!");
			ex.printStackTrace();
		}
	}

	/**
	 * Load cookies from previously saved file
	 * 
	 * @return
	 */
	public static void loadCookies() {

		cookies = new HashSet<Cookie>();

		try {
			File file = new File("Cookies.data");
			FileReader fileReader = new FileReader(file);
			BufferedReader buffReader = new BufferedReader(fileReader);
			String strLine;
			while ((strLine = buffReader.readLine()) != null) {
				StringTokenizer token = new StringTokenizer(strLine, ";");
				while (token.hasMoreTokens()) {
					String name = token.nextToken();
					String value = token.nextToken();
					String domain = token.nextToken();
					String path = token.nextToken();
					String date = token.nextToken();
					Date expiryDate = null;

					if (!date.equals("null")) {
						// converting the string of format below into date of same format
						expiryDate = new SimpleDateFormat("ddd',' dd MMM yyyy hh':'mm':'ss 'GMT'").parse(date);
					}

					@SuppressWarnings("deprecation")
					Boolean isSecure = new Boolean(token.nextToken()).booleanValue();
					Cookie cookie = new Cookie(name, value, domain, path, expiryDate, isSecure);
					cookies.add(cookie);
				}
			}
			buffReader.close();

			for (Cookie cook : cookies) {
				// adding cookie to the driver one by one
				driver.manage().addCookie(cook);
			}

		} catch (Exception ex) {
			System.err.println("Error occurred loading cookie file!!");
			ex.printStackTrace();
		}
	}
}
