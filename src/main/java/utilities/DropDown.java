package utilities;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * This class contains methods to perform different operations in a drop down
 * menu
 * 
 * @author sanish
 *
 */
public class DropDown extends UtilBase {

	/**
	 * Returns the total no of options in a drop-down menu
	 * 
	 * @param dropdown
	 * @return no. of options, -1 if exception occurs
	 */
	public static int getOptionsCount(WebElement dropdown) {
		try {
			List<WebElement> elementCount = new Select(dropdown).getOptions();
			return elementCount.size();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * Selects the option from drop-down menu with given visible text
	 * 
	 * @param dropDown
	 * @param visibleText
	 */
	public static void selectOptionByVisibleText(WebElement dropDown, String visibleText) {
		try {
			if (visibleText != null)
				new Select(dropDown).selectByVisibleText(visibleText);
			else
				throw new NullPointerException("unable to use null value");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * Selects option from drop-down menu with given index
	 * 
	 * @param dropDown
	 * @param index
	 */

	public static void selectOptionByIndex(WebElement dropDown, int index) {
		try {
			if (index >= 0)
				new Select(dropDown).selectByIndex(index);
			else
				throw new NoSuchElementException("invalid index");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Returns text of selected option of a drop down menu
	 * 
	 * @param dropdown
	 * @return Text of selected option
	 */
	public static String getSelectedOptionText(WebElement dropdown) {
		return new Select(dropdown).getFirstSelectedOption().getText();
	}

	/**
	 * Returns index of selected option of a drop down menu
	 * 
	 * @param dropdown
	 * @return index value
	 */
	public static int getSelectedOptionIndex(WebElement dropdown) {
		String optionText = getSelectedOptionText(dropdown);
		return getOptionIndex(dropdown, optionText);
	}

	/**
	 * Returns list of text of all options of a drop down menu
	 * 
	 * @param dropdown
	 * @return list of texts of options
	 */
	public static List<String> getAllOptionText(WebElement dropdown) {
		List<String> optionTexts = new ArrayList<>();
		List<WebElement> options = new Select(dropdown).getOptions();
		for (WebElement option : options)
			optionTexts.add(option.getText());
		return optionTexts;
	}

	/**
	 * Returns index of an option of a drop down menu
	 * 
	 * @param dropdown
	 * @param itemText
	 * @return index value, -1 if not found
	 */
	public static int getOptionIndex(WebElement dropdown, String itemText) {
		List<WebElement> options = new Select(dropdown).getOptions();

		for (int i = 0; i < options.size(); i++) {
			if (options.get(i).getText().equals(itemText))
				return i;
		}
		return -1;
	}

	/**
	 * Returns text of an option of a drop down menu
	 * 
	 * @param dropdown
	 * @param index
	 * @return text of an option
	 */
	public static String getOptionText(WebElement dropdown, int index) {
		List<WebElement> options = new Select(dropdown).getOptions();
		return options.get(index).getText();
	}

	/**
	 * Returns list of texts of selected options of a drop down menu
	 * 
	 * @param dropdown
	 * @return list of texts
	 */
	public static List<String> getAllSelectedOptions(WebElement dropdown) {
		List<String> optionTexts = new ArrayList<>();
		List<WebElement> options = new Select(dropdown).getAllSelectedOptions();
		for (WebElement option : options)
			optionTexts.add(option.getText());
		return optionTexts;
	}

	/**
	 * Selects multiple options of a drop down menu located by given indices
	 * 
	 * @param dropdown
	 * @param indices
	 */
	public static void selectTheseOptions(WebElement dropdown, int... indices) {
		try {
			Select sl = new Select(dropdown);
			if (sl.isMultiple()) {
				for (int index : indices)
					sl.selectByIndex(index);
			} else
				throw new Exception("menu doesn't support multiple selection");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Selects multiple options of a drop down menu located by given texts
	 * 
	 * @param dropdown
	 * @param optionTexts
	 */
	public static void selectTheseOptions(WebElement dropdown, String... optionTexts) {
		try {
			Select sl = new Select(dropdown);
			if (sl.isMultiple()) {
				for (String text : optionTexts)
					sl.selectByVisibleText(text);
			} else
				throw new Exception("menu doesn't support multiple selection");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Deselects an option of a drop down menu located by given index
	 * 
	 * @param dropdown
	 * @param index
	 */
	public static void deselectOptionByIndex(WebElement dropdown, int index) {
		new Select(dropdown).deselectByIndex(index);
	}

	/**
	 * Deselects an option of a drop down menu located by given text
	 * 
	 * @param dropdown
	 * @param visibleText
	 */
	public static void deselectOptionByVisibleText(WebElement dropdown, String visibleText) {
		new Select(dropdown).deselectByVisibleText(visibleText);
	}

	/**
	 * Deselects all options of a drop down menu
	 * 
	 * @param dropdown
	 */
	public static void deselectAllOptions(WebElement dropdown) {
		new Select(dropdown).deselectAll();
	}
}
