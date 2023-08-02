/**
 * 
 */
package utilities;

import org.openqa.selenium.WebElement;

/**
 * This class contains methods related with radio button (i.e. <input
 * type="radio" ..)
 * 
 * @author sanish
 *
 */
public class RadioButton extends WebElementLib {

	/**
	 * Checks if the element is a radio button or not
	 * 
	 * @param radioButton
	 * @return true/false
	 */
	public static boolean isRadioButton(WebElement radioButton) {
		return radioButton.getTagName().equalsIgnoreCase("input")
				&& radioButton.getAttribute("type").equalsIgnoreCase("radio");
	}

	/**
	 * This methods clicks/selects the radio button
	 * 
	 * @param radioButton
	 */
	public static void select(WebElement radioButton) {
		try {
			MouseOperations.leftClick(radioButton);
		} catch (Exception e) {
			// used when the <input> element cannot receive the click,
			// instead creates <label> element that receives the click for <input>
			// in some cases click for radio buttons can't be clicked [often in PROGRESS]
			// String xpath = "//*[@for='" + getValue(radioButton, "id") + "']";

			WebElement radioLabel = WebElementLib.findMyElement("for", getAttributeValue(radioButton, "id"));
			MouseOperations.leftClick(radioLabel);
		}
	}
}