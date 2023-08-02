package utilities;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author prabin
 */

public class WaitUntil extends UtilBase
{
	WebDriverWait wait;
//	int t1 = 30;
	Duration t1 = Duration.ofSeconds(30);
	Duration t2 = Duration.ofSeconds(60);
	

	/**
	 * This method will return document.readyState and wait until pageLoadCondition return complete
	 * @param driver
	 */
	public void waitForLoad()
	{
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>()
		{
			public Boolean apply (WebDriver driver)
			{
				return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, t2);
		wait.until(pageLoadCondition);
	}

	/**
	 *  This method will wait until search element is present 
	 * @param element
	 */
	public void wait_element_present(WebElement element)
	{
		boolean exist = WebElementLib.doesElementExist(element);
		if (exist==true)
		{
			wait = new WebDriverWait(driver, t1);
			wait.until(ExpectedConditions.visibilityOf(element));
		}
	}

	/**
	 *  This method will wait until present element is clickable 
	 * @param element
	 */
	public void wait_element_clickable(WebElement element)
	{
		boolean exist = WebElementLib.doesElementExist(element);
		if (exist==true)
		{
			wait = new WebDriverWait(driver, t1);
			wait.until(ExpectedConditions.elementToBeClickable(element));
		}
	}

	/**
	 *  This method will wait until element is disappear 
	 * @param element
	 */
	public void  wait_element_disappear(WebElement element)
	{
			wait = new WebDriverWait(driver, t1);
			//wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOf(element)));
			wait.until(ExpectedConditions.invisibilityOf(element));
	}

	/**
	 *  This method will wait until element is not clickable 
	 * @param element
	 */
	public void  wait_element_not_clickable(WebElement element)
	{
		if (element.isDisplayed()==true || element.isEnabled()==true || element.isSelected()==true)
		{
			wait = new WebDriverWait(driver, t1);
			wait.until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(element)));
		}
	}

	/**
	 *  This method will wait until element text is not changed 
	 * @param element
	 */
	public void  wait_element_text_change(WebElement element)
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String text = (String)js.executeScript("return arguments[0].value;",element);
		wait = new WebDriverWait(driver, t1);
		wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElementValue(element, text)));
	}
	
	/**
	 * This method will wait until expected text is not present
	 * @param element
	 */
	public void  wait_AttributeValue_getText(WebElement element)
	{
		String text = element.getAttribute("value");
		if (text!= null && text!= "")
		{
			wait = new WebDriverWait(driver, t1);
			wait.until(ExpectedConditions.textToBePresentInElementValue(element, text));
		}
	}
	
	/**
	 * This method will get text from element and wait until element text is not changed
	 * @param element
	 */
	public void wait_element_getText_change(WebElement element)
	{
		String text = element.getText();
		if (text!= null && text!= "")
		{
			wait = new WebDriverWait(driver, t1);
			wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(element, text)));
		}
		
	}
	
	/**
	 * This method will wait until visibility of element located by attribute and value
	 * @param attribute
	 * @param value
	 */
	public void  wait_visibilityOfElementLocated(String attribute, String value)
	{
		if (attribute != null && value != null)
		{
			try
			{
				switch(attribute.toLowerCase())
				{
				case "id":
					wait = new WebDriverWait(driver, t1);
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(value)));
					return;

				case "name":
					wait = new WebDriverWait(driver, t1);
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(value)));
					return;

				case "xpath":
					wait = new WebDriverWait(driver, t1);
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(value)));
					return;

				case "class":
					wait = new WebDriverWait(driver, t1);
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(value)));
					return;

				case "cssSelector":
					wait = new WebDriverWait(driver, t1);
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector((value))));		
					return;

				case "tagName":
					wait = new WebDriverWait(driver, t1);
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName((value))));	
					return;

				case "linkText":
					wait = new WebDriverWait(driver, t1);
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText((value))));		
					return;	
				}
			}
			catch(Exception e)
			{
				System.out.println("WebElement with attribute :: '" + attribute + " : " + value	+ "' is not visible.");
			}
		}
		else
		{
			System.out.println("Selected attribute::'"+attribute+"' or value:: '"+value+"' are null, please check " );
		}
	}

	/**
	 *  This method will wait until visibility of element located by attribute and value
	 * @param attribute
	 * @param value
	 */
	public void wait_elementToBeClickable(String attribute, String value)
	{
		if (attribute != null && value != null)
		{
			try
			{
				switch(attribute.toLowerCase())
				{
				case "id":
					wait = new WebDriverWait(driver, t1);
					wait.until(ExpectedConditions.elementToBeClickable(By.id(value)));
					return; 

				case "name":
					wait = new WebDriverWait(driver, t1);
					wait.until(ExpectedConditions.elementToBeClickable(By.name(value)));	
					return;

				case "class":
					wait = new WebDriverWait(driver, t1);
					wait.until(ExpectedConditions.elementToBeClickable(By.className((value))));	
					return;

				case "cssSelector":
					wait = new WebDriverWait(driver, t1);
					wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector((value))));		
					return;

				case "xpath":
					wait = new WebDriverWait(driver, t1);
					wait.until(ExpectedConditions.elementToBeClickable(By.xpath((value))));		
					return;

				case "tagName":
					wait = new WebDriverWait(driver, t1);
					wait.until(ExpectedConditions.elementToBeClickable(By.tagName((value))));	
					return;

				case "linkText":
					wait = new WebDriverWait(driver, t1);
					wait.until(ExpectedConditions.elementToBeClickable(By.linkText((value))));		
					return;
				}
			}
			catch(Exception e)
			{
				System.out.println("WebElement with attribute :: '" + attribute + " : " + value	+ "' is not clickable.");
			}
		}
		else
		{
			System.out.println("Selected attribute::'"+attribute+"' or value:: '"+value+"' are null, please check " );
		}
	}

	/**
	 *  This method will wait until title contains search text
	 * @param dataTitle
	 */
	public void wait_until_titleContains(String dataTitle)
	{
		wait = new WebDriverWait(driver, t1);
		wait.until(ExpectedConditions.titleContains(dataTitle));
	}

	/**
	 * This method will wait until title contains search URL
	 * @param dataURL
	 */
	public void wait_until_urlContains(String dataURL)
	{
		wait = new WebDriverWait(driver, t1);
		wait.until(ExpectedConditions.urlContains(dataURL));		
	}

}