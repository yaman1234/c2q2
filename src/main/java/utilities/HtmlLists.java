/**
 * 
 */
package utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;

/**
 * This class contains methods to work with HTML lists
 * 
 * @author sanish
 *
 */
public class HtmlLists extends UtilBase {
	/**
	 * Checks whether or not an element is a HTML list
	 * 
	 * @param menuList
	 * @return true/false
	 */
	public static boolean isElementList(WebElement menuList) {
		if (WebElementLib.doesElementExist(menuList)
				&& (menuList.getTagName().equals("ul") || menuList.getTagName().equals("ol")))
			return true;
		return false;
	}
	
	
	public static boolean isElementListDiv(WebElement menuList) {
		if (WebElementLib.doesElementExist(menuList)
				&& (menuList.getTagName().equals("div") ))
			return true;
		return false;
	}

	/**
	 * Returns array list of web elements in that list
	 * 
	 * @param menuList
	 * @return list of web elements
	 */
	public static List<WebElement> getListItems(WebElement menuList) {
		if (isElementList(menuList))
			return GenerateXpath.getImmediateChildren(menuList, "li");
		else
			return null;
	}



	
	
	/**
	 * Checks whether an item is listed in the list or not
	 * 
	 * @param menuList
	 * @param itemName
	 * @return
	 */
	public static boolean isItemInList(WebElement menuList, String itemName) {
		List<String> itemNames = getListItemNames(menuList);
		for (String item : itemNames) {
			if (item.equals(itemName))
				return true;
		}
		return false;
	}

	/**
	 * Returns array list of item names in that list
	 * 
	 * @param menuList
	 * @return list of item names
	 */
	public static List<String> getListItemNames(WebElement menuList) {
		List<WebElement> listItems = getListItems(menuList);
		List<String> itemNames = new ArrayList<>();
		for (WebElement item : listItems)
			itemNames.add(item.getText());
		return itemNames;
	}
	
	public static List<WebElement> getListItemsDiv(WebElement menuList) {
		if (isElementListDiv(menuList))
			return GenerateXpath.getImmediateChildren(menuList);
		
		
		else
			return null;
	}
	
	public static List<String> getListItemNamesDiv(WebElement menuList) {
		List<WebElement> listItems = getListItemsDiv(menuList);
		List<String> itemNames = new ArrayList<>();
		for (WebElement item : listItems)
			itemNames.add(item.getText());
		return itemNames;
	}

	/**
	 * Returns total number of items in a list
	 * 
	 * @param menuList
	 * @return
	 */
	public static int getListItemsCount(WebElement menuList) {
		return GenerateXpath.getImmediateChildren(menuList, "li").size();
	}
	
	public static int getDivListCount(WebElement menuList) {
		return GenerateXpath.getImmediateChildren(menuList, "div").size();
	}
	
	
	public static int getTHcount(WebElement menuList) {
		return GenerateXpath.getImmediateChildren(menuList, "th").size();
	}

	/**
	 * Selects an option in that list using its index
	 * 
	 * @param menuList
	 * @param index
	 */
	public static void selectThisOption(WebElement menuList, int index) {
		List<WebElement> options = GenerateXpath.getImmediateChildren(menuList, "li");
		MouseOperations.leftClick(options.get(index));
	}
	
	public static void selectThisOptionDiv(WebElement menuList, int index) {
		List<WebElement> options = GenerateXpath.getImmediateChildren(menuList, "div");
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		MouseOperations.leftClick(options.get(index));
	}

	/**
	 * Selects an option in that list using its item name
	 * 
	 * @param menuList
	 * @param itemName
	 */
	public static void selectThisOption(WebElement menuList, String itemName) {
		List<WebElement> options = GenerateXpath.getImmediateChildren(menuList, "li");
		for (WebElement item : options) {
			if (item.getText().equals(itemName)) {
				MouseOperations.leftClick(item);
				break;
			}
		}
	}
}
