/**
 * 
 */
package utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains methods to handle browser tabs
 * 
 * @author sanish
 *
 */
public class HandleTabs extends UtilBase {
	/**
	 * Opens given url in a new tab and switches to it
	 * 
	 * @param url
	 */
	public static void openTab(String url) {
		jsDriver.executeScript("window.open('" + url + "');");
		switchToTab(noOfTabsOpen() - 1);
	}

	/**
	 * Switches to tab located by given index
	 * 
	 * @param tabIndex
	 *            first tab as 0, second as 1...
	 */
	public static void switchToTab(int tabIndex) {
		// listing tabs opened in current browser
		List<String> tabs = currentlyOpenTabs();
		driver.switchTo().window(tabs.get(tabIndex));
	}

	/**
	 * Switches to tab located by given tab name
	 */
	public static void switchToTab(String tabName) {
		// listing tabs opened in current browser
		List<String> tabs = currentlyOpenTabs();
		for (String tab : tabs) {
			if (tab.equalsIgnoreCase(tabName)) {
				driver.switchTo().window(tabName);
				break;
			}
		}
	}

	/**
	 * Closes tab located by given index
	 * 
	 * @param tabIndex
	 */
	public static void closeTab(int tabIndex) {
		switchToTab(tabIndex);
		driver.close();
		if (noOfTabsOpen() > 0)
			switchToTab(0);
	}

	/**
	 * Closes tab located by given tab name
	 * 
	 * @param tabName
	 */
	public static void closeTab(String tabName) {
		switchToTab(tabName);
		driver.close();
		if (noOfTabsOpen() > 0)
			switchToTab(0);
	}

	/**
	 * Returns number of total tabs open in the browser
	 * 
	 * @return no. of open tabs
	 */
	public static int noOfTabsOpen() {
		return currentlyOpenTabs().size();
	}

	/**
	 * Returns the list of tabs open in the browser
	 * 
	 * @return list of tabs
	 */
	public static List<String> currentlyOpenTabs() {
		return new ArrayList<String>(driver.getWindowHandles());
	}
}
