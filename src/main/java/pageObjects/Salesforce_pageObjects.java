package pageObjects;

import org.openqa.selenium.WebElement;

import utilities.WebElementLib;

public class Salesforce_pageObjects {

/*****************************   Login page	    ********************************/
	public WebElement username_input() {
		return WebElementLib.findMyElement("xpath", "//*[@id='username']");
	}
	
	public WebElement password_input() {
		return WebElementLib.findMyElement("xpath", "//*[@id='password']");
	}
	
	public WebElement login_button() {
		return WebElementLib.findMyElement("xpath", "//*[@id='Login']");
	}
	

	/*****************************   Navbar links after Logged in   ********************************/
	public WebElement cases_link() {
		return WebElementLib.findMyElement("linktext", "Cases");
	}
	
	public WebElement catalogs_link() {
		return WebElementLib.findMyElement("linktext", "Customer Catalogs");
	}
	
	public WebElement logout_link() {
		return WebElementLib.findMyElement("linktext", "Log out");
	}
	
	
	/*****************************   Cases Tab after Logged in   ********************************/
	public WebElement search_input() {
		return WebElementLib.findMyElement("xpath", "//input[@name='Case-search-input']");
	}
	
	public WebElement table() {
		return WebElementLib.findMyElement("xpath", "//table");
	}


}
