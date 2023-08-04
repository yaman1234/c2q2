package test_functional;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import pageObjects.PQC_pageObjects;
import pageObjects.SQ_pageObjects;
import pageObjects.Salesforce_pageObjects;
import utilities.ExcelRead;
import utilities.SuperEmail;
import utilities.TableData;
import utilities.UtilBase;
import utilities.WebElementLib;

public class Test_Salesforce extends UtilBase {

	ExtentReports extent;
	ExtentTest test, precondition;

	Salesforce_pageObjects sf_po = new Salesforce_pageObjects();
	SQ_pageObjects sq_po = new SQ_pageObjects();
	PQC_pageObjects pqc_po = new PQC_pageObjects();

	String expected_subject = "";

	/******************** Global variables ***********************/
//	used in sq status check
	int retryCount = 0;
	int maxRetry = 3;

	@BeforeClass
	public void setup() {
		ExtentSparkReporter spark = new ExtentSparkReporter("testReports/automationReport.html");
		extent = new ExtentReports();
		extent.attachReporter(spark);
		test = extent.createTest("Portal Test");

	}

	@Test(priority = 1)
	public void precondition_salesforce() {
		precondition = test.createNode("Preconditions");
		sendEmail();
		salesforceLogin();
		sf_case_global = confirmCaseCreation(expected_subject);
		System.out.println("Sf_case: " + sf_case_global);
		searchCaseNumber_salesforce(sf_case_global);
	}

	public void sendEmail() {
//		send email to sf with timestamp appended into subject
		try {

//			get current timestamp
			String timestamp = String.valueOf(System.currentTimeMillis());
			String attachmentPath = System.getProperty("user.dir") + "\\testdata\\sample\\sample_ILS.txt";

			String from = ExcelRead.getData(19, 1, "variables");
			String to = ExcelRead.getData(21, 1, "variables");
			expected_subject = "Test Email " + timestamp;
			precondition.log(Status.INFO, "Email Subject: " + expected_subject);
			SuperEmail.sendEmailUsingHtmlMsg(from, to, expected_subject, attachmentPath);
			precondition.log(Status.PASS, "Send Email to Salesforce");
			System.out.println("Email Subject: " + expected_subject);
		} catch (Exception e) {
			// TODO: handle exception
			precondition.log(Status.FAIL, "Send Email to Salesforce");
		}
	}

	public void salesforceLogin() {
//		login to sf with existing chrome profile
		try {
			String chromeprofilepath = "C:\\Users\\yamah022\\Desktop\\eclipse\\chromeData";
			initialiseDriverwithprofile(chromeprofilepath);
			precondition.log(Status.INFO, "Initialized chrome with profile");

			String cases_url = "https://kapcoglobal--esbox.sandbox.lightning.force.com/lightning/o/Case/list?filterName=00B8M000000RNCrUAO";
			driver.get(cases_url);
			if (WebElementLib.doesElementExist(sf_po.logout_link())) {
				System.out.println("SUCCESS: Log in to SF ");
				precondition.log(Status.PASS, "Logged in to SF");
			}else {
				System.out.println("ERROR: Log in to SF FAILED");
			}
		} catch (Exception e) {
			System.out.println("ERROR: Log in to SF FAILED");
			e.printStackTrace();
		}
	}

	public String confirmCaseCreation(String expected_subject) {
		String foundCase = "";
//	confirm case created in sf, by verifying the timestamp in subject
		try {
			Thread.sleep(8000);
			WebElement table = sf_po.table();
			List<String> rowdata = TableData.getRowData(table, 1);

			for (int i = 1; i < 5; i++) {
				String subject = TableData.getCellText(table, i, 9);

				if (subject.equals(expected_subject)) {
					foundCase = TableData.getCellText(table, i, 2);
					System.out.println("confirmCaseCreation, case found : " + foundCase);
					precondition.log(Status.PASS, "confirmCaseCreation, Case found : " + foundCase);
					break;
				}else {
					System.out.println("ERROR: confirmCaseCreation, case not found");
					precondition.log(Status.PASS, "confirmCaseCreation, Case found " );
				}
			}
		} catch (Exception e) {
			precondition.log(Status.FAIL, "Confirm case creation");
		}
		return foundCase;
	}

	public void searchCaseNumber_salesforce(String sf_Case) {
//		search with case number 
		try {
			sf_po.search_input().sendKeys(sf_case_global);
			Thread.sleep(1000);
			sf_po.search_input().sendKeys(Keys.ENTER);
			Thread.sleep(3000);

			int rowCount = TableData.getRowCount(sf_po.table());
			System.out.println("searchCaseNumber_salesforce rowCount: " + rowCount);
//			used 2 because of an extra <tr> exists 
			if (rowCount == 2) {
				System.out.println("Search with case number, case found");
				precondition.log(Status.PASS, "Search with Case number, case found");
			} else {
				System.out.println("ERROR: searchCaseNumber_salesforce, Case not found");
				precondition.log(Status.FAIL, "Search with Case number, Case not found");
			}
		} catch (Exception e) {
			System.out.println("ERROR: search with case number failed");
			precondition.log(Status.FAIL, "Search with Case number");
			e.printStackTrace();
		}
	}

	/*
	 * Test : Email received from portal configured email address
	 * Expected Result: Type should be identified as RFQ-PORTAL and xml should be added to outbound queue
	 */
	@Test(priority = 2)
	public void US01_TC05() {
		ExtentTest us01_tc05 = test.createNode("US01_TC05: Email received from portal configured email address ");
		
//		Type should be identified as RFQ-PORTAL
		try {
			String expected_type = "RFQ - ILS";
			String type = TableData.getCellText(sf_po.table(), 1, 7);
			System.out.println("type: " + type);
	
			Assert.assertEquals(type, expected_type);
			
			if (type.equals(expected_type)) {
				us01_tc05.log(Status.PASS, "Type should be identified as RFQ-PORTAL");
				System.out.println("PASS : Type should be identified as RFQ-PORTAL");
//				Assert
			}
		} catch (Exception e) {
			us01_tc05.log(Status.FAIL, "Type should be identified as RFQ-PORTAL");
			System.out.println("ERROR : Type should be identified as RFQ-PORTAL");
		}
		
		
//		XML should be added to outbound Queue
		try {
			int count = 4;
			for (int i = 1; i < count; i++) {

				String status = TableData.getCellText(sf_po.table(), 1, 4);
				String expected_status = "Working";

				if (status.equals(expected_status)) {
					us01_tc05.log(Status.PASS, "XML should be added to outbound Queue");
					System.out.println("PASS : XML should be added to outbound Queue");
					break;
				} else if (status.equals("Open - (New)")) {
					driver.navigate().refresh();
					us01_tc05.log(Status.INFO, "Status: Open - (New)");
					System.out.println("status: Status: Open - (New)");
					Thread.sleep(5000);
					searchCaseNumber_salesforce(sf_case_global);
				}
			}
		} catch (Exception e) {
			us01_tc05.log(Status.FAIL, "ERROR : XML should be added to outbound Queue");
		}
		
	}

	/*
	 * testcase_02: Outbound message is added in ActiveMQ queue. Application =
	 * Salesforce
	 */
//	@Test(priority = 3)
	public void testcase_02() {
		ExtentTest testcase_02 = test.createNode("testcase_02: Outbound message is added in ActiveMQ  queue. ");

		try {
			int count = 2;
			for (int i = 1; i < count; i++) {

				String status = TableData.getCellText(sf_po.table(), 1, 4);
				String expected_status = "Working";

				if (status.equals(expected_status)) {
					testcase_02.log(Status.PASS, "testcase_02: Outbound message is added in ActiveMQ  queue. ");
					System.out.println("status: Working");
					break;
				} else if (status.equals("Open - (New)")) {
					driver.navigate().refresh();
					testcase_02.log(Status.INFO, "Status: Open - (New)");
					System.out.println("status: Status: Open - (New)");
					Thread.sleep(5000);
					searchCaseNumber_salesforce(sf_case_global);
				}
			}
		} catch (Exception e) {
			testcase_02.log(Status.FAIL, "testcase_02: Outbound message is added in ActiveMQ  queue. ");
		}
	}
	
	
	@Test(priority = 4)
	public void precondition_sq() {
		login_sq();
	}
	
	
//	open new tab and login 
	public void login_sq() {
		try {
			
			// opening the new tab
			jsDriver.executeScript("window.open()");
//			switch to new tab
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			System.out.println(tabs.size());
			System.out.println(tabs.get(0));
			driver.switchTo().window(tabs.get(1));

//			load the url
			String sq_baseurl = "http://" +ExcelRead.getData(10, 5, "variables");
			String sq_username = ExcelRead.getData(1, 7,"variables");
			String sq_password = ExcelRead.getData(2, 7,"variables");
			
			driver.get(sq_baseurl);

//			login
			sq_po.username_input().sendKeys(sq_username);
			sq_po.password_input().sendKeys(sq_password);
			sq_po.submit_button().click();
			Thread.sleep(2000);
			
			Assert.assertTrue(WebElementLib.doesElementExist(sq_po.signout_button()), "ERROR : SQ Login Failed");
			
//			verify login is successful
			if (WebElementLib.doesElementExist(sq_po.signout_button())) {
				precondition.log(Status.PASS, "Logged in to SQ");
				System.out.println("PASS : Login to SQ ");
			}else {
				System.out.println("ERROR : Login to SQ");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("ERROR : Login to SQ");
			e.printStackTrace();
		}
	}
	

	/*
	 * Smarter Quoting Application
	 * Test US02_TC25 : Valid XML content from ActiveMQ and message type is 'RFQ-Portal'
	 * Expected condition: Search case number in Messages tab and verify the status 'RECEIVED'
	 */

	@Test(priority = 4)
	public void US02_TC25() throws InterruptedException {
		ExtentTest us02_tc25 = test.createNode("US02_TC25: Valid XML content from ActiveMQ and message type is 'RFQ-Portal' ");

		try {
			String expected_status = "RECEIVED";
			for (int i = retryCount; i < maxRetry; i++) {
				sq_po.messages_link().click();
				Thread.sleep(1000);
				sq_po.clear_messages_button().click();
				sq_po.caseNumber_messages_input().sendKeys(sf_case_global);
				sq_po.search_messages_button().click();
				Thread.sleep(2000);
				String status = sq_po.status_messages().getText();

				if (status.equals(expected_status)) {
					us02_tc25.log(Status.PASS, "Valid XML content from ActiveMQ and message type is 'RFQ-Portal'");
					System.out.println("PASS : Valid XML content from ActiveMQ and message type is 'RFQ-Portal'");
					break;
				} else {
					System.out.println("Retry #" + i);
					driver.navigate().refresh();
					Thread.sleep(1000);
					retryCount++;
				}
			}
		} catch (Exception e) {
			if (WebElementLib.doesElementExist(sq_po.noData_table())) {
				System.out.println("ERROR :: Valid XML content from ActiveMQ and message type is 'RFQ-Portal'");
				us02_tc25.log(Status.FAIL, "Valid XML content from ActiveMQ and message type is 'RFQ-Portal'");
			}
			e.printStackTrace();
		}
		

//		search case in Messages tab and check the status
//		searchMessages(sf_case_global);

//		search case in Case Emails tab and check the status
//		searchCaseEmails(sf_case_global);
	}

	public void searchMessages(String caseNumber) {
		try {
			String expected_status = "RECEIVED";
			for (int i = retryCount; i < maxRetry; i++) {
				sq_po.messages_link().click();
				Thread.sleep(1000);
				sq_po.clear_messages_button().click();
				sq_po.caseNumber_messages_input().sendKeys(sf_case_global);
				sq_po.search_messages_button().click();
				Thread.sleep(2000);
				String status = sq_po.status_messages().getText();

				if (status.equals(expected_status)) {
					System.out.println("status: Working");
					System.out.println("Testcase_03 passed");
					break;
				} else {
					System.out.println("Retry #" + i);
					driver.navigate().refresh();
					Thread.sleep(1000);
					retryCount++;
				}
			}
		} catch (Exception e) {
			if (WebElementLib.doesElementExist(sq_po.noData_table())) {
				System.out.println("Search Messages :: No Data found");
			}
			e.printStackTrace();
		}
	}
	
	/*
	 * US03_TC40 : Valid .json file and at least one part number 
	 * Expected Result: Verify case sent to PQC (status changed to 'CLOSED')
	 */
	@Test(priority = 5)
	public void US03_TC40() {
		ExtentTest us03_tc40 = test.createNode("US03_TC40: Valid XML content from ActiveMQ and message type is 'RFQ-Portal' ");
		String sq_status = "";
		String sq_caseNumber, sq_caseType, sq_subject, sq_customer, sq_contact;
		try {
			sq_po.caseEmails_link().click();
			Thread.sleep(2000);
			sq_po.clear_caseEmails_button().click();
			sq_po.caseNumber_caseEmails_input().sendKeys(sf_case_global);
			sq_po.search_caseEmails_button().click();
			Thread.sleep(2000);
			sq_caseNumber = sq_po.caseNumber_caseEmails().getText();
			sq_caseType = sq_po.caseType_caseEmails().getText();
			sq_status = sq_po.status_caseEmails().getText();

			System.out.println("Case Number: " + sq_caseNumber);
			System.out.println("Type: " + sq_caseType);
			System.out.println("Case Emails Status: " + sq_status);

			if (sq_status.contains("NEW")) {
				Thread.sleep(5000);
				searchCaseEmails(sf_case_global);
				System.out.println(sq_status);
			} else if (sq_status.contains("RECEIVED") || sq_status.contains("IN-PROGRESS") || sq_status.contains("READY")) {
				Thread.sleep(5000);
				searchCaseEmails(sf_case_global);
				System.out.println(sq_status);
			} else if (sq_status.contains("COMPLETED")) {
				Thread.sleep(5000);
				searchCaseEmails(sf_case_global);
				System.out.println(sq_status);
			} else if (sq_status.contains("CLOSED")) {
				us03_tc40.log(Status.PASS, "Valid .json file and at least one part number");
				System.out.println("sq_status: CLOSED, Move on to PQC");
			}
		} catch (Exception e) {
			if (WebElementLib.doesElementExist(sq_po.noData_table())) {
				System.out.println("Case Emails:  No Data found");
				us03_tc40.log(Status.FAIL, "Valid .json file and at least one part number");
			}
			e.printStackTrace();
		}
	}
	
	

	public void searchCaseEmails(String caseNumber) {
		String sq_status = "";
		String sq_caseNumber, sq_caseType, sq_subject, sq_customer, sq_contact;
		try {
			sq_po.caseEmails_link().click();
			Thread.sleep(2000);
			sq_po.clear_caseEmails_button().click();
			sq_po.caseNumber_caseEmails_input().sendKeys(caseNumber);
			sq_po.search_caseEmails_button().click();
			Thread.sleep(2000);
			sq_caseNumber = sq_po.caseNumber_caseEmails().getText();
			sq_caseType = sq_po.caseType_caseEmails().getText();
			sq_status = sq_po.status_caseEmails().getText();

			System.out.println("Case Number: " + sq_caseNumber);
			System.out.println("Type: " + sq_caseType);
			System.out.println("Case Emails Status: " + sq_status);

			if (sq_status.contains("NEW")) {
				Thread.sleep(5000);
				searchCaseEmails(caseNumber);
				System.out.println(sq_status);
			} else if (sq_status.contains("RECEIVED") || sq_status.contains("IN-PROGRESS") || sq_status.contains("READY")) {
				Thread.sleep(5000);
				searchCaseEmails(caseNumber);
				System.out.println(sq_status);
			} else if (sq_status.contains("COMPLETED")) {
				Thread.sleep(5000);
				searchCaseEmails(caseNumber);
				System.out.println(sq_status);
			} else if (sq_status.contains("CLOSED")) {
				System.out.println("sq_status: CLOSED, Move on to PQC");
			}
		} catch (Exception e) {
			if (WebElementLib.doesElementExist(sq_po.noData_table())) {
				System.out.println("Case Emails:  No Data found");
			}
			e.printStackTrace();
		}
	}

	@AfterClass
	public void teardown() {
		extent.flush();
//		driver.close();
	}

}
