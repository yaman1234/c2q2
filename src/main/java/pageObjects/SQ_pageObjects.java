package pageObjects;

import org.openqa.selenium.WebElement;

import utilities.WebElementLib;

public class SQ_pageObjects {

	/***************************** LOGIN PAGE ***************************/
	public WebElement username_input() {
		return WebElementLib.findMyElement("xpath", "//*[@id='root']/div/div/div/div/form/input[1]");
	}

	public WebElement password_input() {
		return WebElementLib.findMyElement("xpath", "//*[@id='root']/div/div/div/div/form/input[2]");
	}

	public WebElement submit_button() {
		return WebElementLib.findMyElement("xpath", "//*[@id='root']/div/div/div/div/form/button");
	}

	/***************************** NAVBAR ***************************/
	public WebElement home_link() {
		return WebElementLib.findMyElement("linktext", "Home");
	}

	public WebElement caseEmails_link() {
		return WebElementLib.findMyElement("linktext", "Case Emails");
	}

	public WebElement reviewAnnotation_link() {
		return WebElementLib.findMyElement("linktext", "Review Annotation");
	}

	public WebElement settings_link() {
		return WebElementLib.findMyElement("linktext", "Settings");
	}

	public WebElement messages_link() {
		return WebElementLib.findMyElement("linktext", "Messages");
	}

	public WebElement mlModels_link() {
		return WebElementLib.findMyElement("linktext", "ML Models");
	}
	
	public WebElement signout_button() {
		return WebElementLib.findMyElement("xpath", "//*[@id='root']/div/div/div/nav/div/div[2]/ul/li[7]/div");
	}
	
	/***************************** GLOBAL ELEMENTS ***************************/

	public WebElement table() {
		return WebElementLib.findMyElement("xpath", "//div[@class='ant-table-body']/table");
	}

	public WebElement noData_table() {
		return WebElementLib.findMyElement("xpath", "//div[@class='ant-empty ant-empty-normal']");
	}

	/***************************** Messages Tab ***************************/


	public WebElement caseNumber_messages_input() {
		return WebElementLib.findMyElement("xpath", "//*[@id='case_number']");
	}

	public WebElement search_messages_button() {
		return WebElementLib.findMyElement("xpath", "//*[@id='root']/div/div/div/form/div/div[2]/div[2]/div[1]/button");
	}

	public WebElement clear_messages_button() {
		return WebElementLib.findMyElement("xpath", "//*[@id='root']/div/div/div/form/div/div[2]/div[2]/div[2]/button");
	}

	public WebElement status_messages() {
		return WebElementLib.findMyElement("xpath", "//table/tbody/tr[2]/td[3]");
	}

	/***************************** Case Emails Tab ***************************/
	public WebElement caseNumber_caseEmails_input() {
		return WebElementLib.findMyElement("xpath", "//*[@id='case_number']");
	}

	public WebElement search_caseEmails_button() {
		return WebElementLib.findMyElement("xpath", "//*[@id='root']/div/div/div/div/form/div/div[4]/div[2]/div[1]/button");
	}

	public WebElement clear_caseEmails_button() {
		return WebElementLib.findMyElement("xpath", "//*[@id='root']/div/div/div/div/form/div/div[4]/div[2]/div[1]/button");
	}
	
	public WebElement caseNumber_caseEmails() {
		return WebElementLib.findMyElement("xpath", "//table/tbody/tr[2]/td[1]");
	}

	public WebElement customer_caseEmails() {
		return WebElementLib.findMyElement("xpath", "//table/tbody/tr[2]/td[2]");
	}

	public WebElement contact_caseEmails() {
		return WebElementLib.findMyElement("xpath", "//table/tbody/tr[2]/td[3]");
	}

	public WebElement caseType_caseEmails() {
		return WebElementLib.findMyElement("xpath", "//table/tbody/tr[2]/td[5]");
	}

	public WebElement status_caseEmails() {
		return WebElementLib.findMyElement("xpath", "//table/tbody/tr[2]/td[8]");
	}
}
