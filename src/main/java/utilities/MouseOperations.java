/**
 * 
 */
package utilities;

import org.openqa.selenium.WebElement;

/**
 * This class contains mouse functions
 * 
 * @author sanish
 *
 */
public class MouseOperations extends UtilBase {

	/**
	 * Performs left click on the element
	 * 
	 * @param element
	 */
	public static void leftClick(WebElement element) {
		try {
			scrollIntoView(element);
			element.click();
		} catch (Exception e2) {
			try {
				jsDriver.executeScript("arguments[0].click();", element);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/**
	 * Performs right/context click on the element
	 * 
	 * @param element
	 */
	public static void rightClick(WebElement element) {
		try {
			scrollIntoView(element);
			actions.contextClick(element).build().perform();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Performs double click on the element
	 * 
	 * @param element
	 */
	public static void doubleClick(WebElement element) {
		try {
			scrollIntoView(element);
			leftClick(element);
			leftClick(element);
		} catch (Exception e) {
			try {
				actions.doubleClick(element).build().perform();
			} catch (Exception ee) {
				System.err.println(ee.getMessage());
				ee.printStackTrace();
			}
		}
	}

	/**
	 * Performs mouse hover on the element using selenium
	 * 
	 * @param element
	 */
	public static void mouseHover(WebElement element) {
		try {
			actions.moveToElement(element).build().perform();
		} catch (Exception e) {
			try {
				scrollIntoView(element);
				actions.moveToElement(element).build().perform();
			} catch (Exception es) {
				System.err.println(es.getMessage());
				es.printStackTrace();
			}
		}
	}

	/**
	 * Scrolls the element into the viewport
	 * 
	 * @param element
	 */
	public static void scrollIntoView(WebElement element) {
		try {
			jsDriver.executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (Exception ez) {
			ez.printStackTrace();
		}
	}
}
