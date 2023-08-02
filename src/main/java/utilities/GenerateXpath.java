package utilities;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author prabin
 */
public class GenerateXpath {

	/**
	 * Returns xpath of an element
	 * 
	 * @param childElement
	 * @param current
	 * @return xpath of current element
	 */
	public static String getXpath(WebElement childElement, String current) {

		String childTag = childElement.getTagName();
		if (childTag.equals("html")) {
			return "/html[1]" + current;
		}
		WebElement parentElement = getParent(childElement);
		List<WebElement> childrenElements = parentElement.findElements(By.xpath("*"));
		int count = 0;
		for (int i = 0; i < childrenElements.size(); i++) {
			WebElement childrenElement = childrenElements.get(i);
			String childrenElementTag = childrenElement.getTagName();
			if (childTag.equals(childrenElementTag)) {
				count++;
			}
			if (childElement.equals(childrenElement)) {
				return getXpath(parentElement, "/" + childTag + "[" + count + "]" + current);
			}
		}
		return null;
	}

	/**
	 * Returns parent of an element
	 * 
	 * @param childElement
	 * @return parent element
	 */
	public static WebElement getParent(WebElement childElement) {
		WebElement parentElement;
		try {
			parentElement = childElement.findElement(By.xpath(".."));
		} catch (Exception e) {
			parentElement = null;
		}
		return parentElement;
	}

	/**
	 * Returns xpath of parent of an element
	 * 
	 * @param childElement
	 * @return xpath of parent
	 */
	public static String getParentXpath(WebElement childElement) {
		WebElement parentElement;
		try {
			parentElement = childElement.findElement(By.xpath(".."));
		} catch (Exception e) {
			parentElement = null;
		}
		return getXpath(parentElement, "");
	}

	/**
	 * Returns list of immediate children of a node
	 * 
	 * @param parentElement
	 * @return list of immediate children
	 */
	public static List<WebElement> getImmediateChildren(WebElement parentElement) {
		List<WebElement> children;
		try {
			children = parentElement.findElements(By.xpath("./*"));
		} catch (Exception e) {
			children = null;
		}
		return children;
	}

	/**
	 * Returns list of immediate children of a node with given tagname only
	 * 
	 * @param parentElement
	 * @param childTag
	 * @return
	 */
	public static List<WebElement> getImmediateChildren(WebElement parentElement, String childTag) {
		List<WebElement> children = new ArrayList<>(), temp;
		try {
			temp = parentElement.findElements(By.xpath("./*"));
			for (WebElement el : temp) {
				if (el.getTagName().equalsIgnoreCase(childTag))
					children.add(el);
			}
		} catch (Exception e) {
			children = null;
		}
		return children;
	}

	/**
	 * Returns list of all children of a node
	 * 
	 * @param parentElement
	 * @return list of all children
	 */
	public static List<WebElement> getAllChildren(WebElement parentElement) {
		List<WebElement> children;
		try {
			children = parentElement.findElements(By.cssSelector("*"));
		} catch (Exception e) {
			children = null;
		}
		return children;
	}

	/**
	 * Returns list of all children of a node with given tag
	 * 
	 * @param parentElement
	 * @param childTag
	 * @return list of all children
	 */
	public static List<WebElement> getAllChildren(WebElement parentElement, String childTag) {
		List<WebElement> children;
		try {
			children = parentElement.findElements(By.tagName(childTag));
		} catch (Exception e) {
			children = null;
		}
		return children;
	}
}
