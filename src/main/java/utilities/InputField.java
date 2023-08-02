package utilities;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

/**
 * this class contains methods that performs the operations related to text and
 * text fields, like: inputFieldClear() clears the input field textInput()
 * inputs the String value to the field getFieldText() gets the text from the
 * text field
 * 
 * @author udhakal
 *
 */
public class InputField extends WebElementLib {

	/**
	 * this method 'inputFieldClear()' clears any input field on this method call
	 * 
	 * @param inputField
	 */
	public static void inputFieldClear(WebElement inputField) {
		try {
			inputField.clear();
		} catch (Exception e) {
			try {
				inputField.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
			} catch (Exception ee) {
				try {
					jsDriver.executeScript("arguments[0].value ='';", inputField);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}

	/**
	 * this method fills all the text input field on its call
	 * 
	 * @param inputField
	 * @param text
	 */
	public static void enterText(WebElement inputField, String text) {
		try {
			inputFieldClear(inputField);
			inputField.sendKeys(text);
		} catch (Exception e) {
			try {
				jsDriver.executeScript("arguments[0].value='" + text + "'", inputField);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
