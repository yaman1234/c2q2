package utilities;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * this class CheckBox contains two methods isSelected() and select(), method
 * isSelected() checks weather check-boxes are enable or disable and selected or
 * not selected
 * 
 * @author sanish
 *
 */
public class CheckBox extends WebElementLib {

	/**
	 * Checks if the element is a checkbox or not
	 * 
	 * @param checkBox
	 * @return true/false
	 */
	public static boolean isCheckBox(WebElement checkBox) {
		return checkBox.getTagName().equalsIgnoreCase("input")
				&& checkBox.getAttribute("type").equalsIgnoreCase("checkbox");
	}

	/**
	 * Checks the given check-box (checks if unchecked, else leaves checked)
	 * 
	 * @param checkBox
	 */
	public static void check(WebElement checkBox) {
		if (!isSelected(checkBox)) {
			MouseOperations.leftClick(checkBox);
		}
	}

	/**
	 * Unchecks the given check-box (unchecks if checked, else leaves unchecked)
	 * 
	 * @param checkBox
	 */
	public static void unCheck(WebElement checkBox) {
		if (isSelected(checkBox)) {
			MouseOperations.leftClick(checkBox);
		}
	}

	/**
	 * Counts total number of checked check-boxes in a table
	 * 
	 * @param targetTable
	 * @param columnIndex
	 * @return no. of checked chkboxes
	 */
	public static int totalChecked(WebElement targetTable, int columnIndex) {
		int checked = 0;
		List<WebElement> chkboxes = TableData.getColumnElements(targetTable, columnIndex);
		for (WebElement chkbox : chkboxes) {
			chkbox = chkbox.findElement(By.tagName("input"));
			if (isCheckBox(chkbox) && isSelected(chkbox))
				checked++;
		}
		return checked;
	}
}
