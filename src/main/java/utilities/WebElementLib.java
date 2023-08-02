package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * This class includes the functions to verify the existence of WebElements, to
 * find those elements and to return them
 * 
 * @author udhakal
 *
 */

public class WebElementLib extends UtilBase {

	/**
	 * Finds element in the DOM using given attribute and value
	 * 
	 * @param attribute,
	 * @param value
	 * @return WebElement
	 */
	public static WebElement findMyElement(String attribute, String value) {

		try {
			if (doesElementExist(attribute, value)) {

				switch (attribute.toLowerCase()) {
				case "id":
					return driver.findElement(By.id(value));

				case "name":
					return driver.findElement(By.name(value));

				case "class":
					return driver.findElement(By.className(value));

				case "cssselector":
					return driver.findElement(By.cssSelector(value));

				case "linktext":
					return driver.findElement(By.linkText(value));

				case "partiallinktext":
					return driver.findElement(By.partialLinkText(value));

				case "tagname":
					return driver.findElement(By.tagName(value));

				case "xpath":
					return driver.findElement(By.xpath(value));

				default:
					String xpath = "//*[@" + attribute + "='" + value + "']";
					return driver.findElement(By.xpath(xpath));
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Checks whether or not the element located by given attribute and value
	 * 
	 * @param attribute,
	 * @param value
	 * @return true/false
	 **/
	public static boolean doesElementExist(String attribute, String value) {
		try {
			if (attribute != null && value != null) {

				switch (attribute.toLowerCase()) {
				case "id":
					return driver.findElements(By.id(value)).size() > 0;

				case "name":
					return driver.findElements(By.name(value)).size() > 0;

				case "class":
					return driver.findElements(By.className(value)).size() > 0;

				case "cssselector":
					return driver.findElements(By.cssSelector(value)).size() > 0;

				case "linktext":
					return driver.findElements(By.linkText(value)).size() > 0;

				case "partiallinktext":
					return driver.findElements(By.partialLinkText(value)).size() > 0;

				case "tagname":
					return driver.findElements(By.tagName(value)).size() > 0;

				case "xpath":
					return driver.findElements(By.xpath(value)).size() > 0;

				default:
					return false;
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Checks whether given element exists or not
	 * 
	 * @param element
	 * @return true/false
	 */
	public static boolean doesElementExist(WebElement element) {

		try {
			String tagName = element.getTagName();
			return doesElementExist("tagname", tagName);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Checks whether given element is enabled or not
	 * 
	 * @param element
	 * @return true/false
	 */
	public static boolean isEnabled(WebElement element) {
		boolean jsBool = !(boolean) jsDriver.executeScript("return arguments[0].disabled", element);
		try {
			return element.isEnabled() && jsBool;
		} catch (Exception e) {
			return jsBool;
		}
	}

	/**
	 * Checks whether given element is selected or not
	 * 
	 * @param element
	 * @return true/false
	 */
	public static boolean isSelected(WebElement element) {
		boolean jsBool = (boolean) jsDriver.executeScript("return arguments[0].checked", element);
		try {
			return element.isSelected() && jsBool;
		} catch (Exception e) {
			return jsBool;
		}
	}

	/**
	 * this method gets the text of any WebElement
	 * 
	 * @param element
	 * @return text
	 */
	public static String getText(WebElement element) {
		try {
			return element.getText();
		} catch (Exception e) {
			try {
				return (String) jsDriver.executeScript("return arguments[0].innerText;", element);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Gets value of an attribute of a web element
	 * 
	 * @param radioButton
	 * @param attribute
	 * @return attribute value
	 */
	public static String getAttributeValue(WebElement element, String attribute) {
		try {
			return element.getAttribute(attribute);
		} catch (Exception e) {
			return (String) jsDriver.executeScript("return arguments[0].getAttribute(\"" + attribute + "\")", element);
		}
	}

}
