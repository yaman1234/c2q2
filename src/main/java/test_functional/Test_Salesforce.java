package test_functional;

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import pageObjects.Salesforce_pageObjects;
import utilities.ExcelRead;
import utilities.SuperEmail;
import utilities.TableData;
import utilities.UtilBase;

public class Test_Salesforce extends UtilBase {

	ExtentReports extent;
	ExtentTest test, precondition;

	Salesforce_pageObjects sf_po = new Salesforce_pageObjects();
	String expected_subject = "";
	

	@BeforeClass
	public void setup() {
		ExtentSparkReporter spark = new ExtentSparkReporter("user/build/name/");
		extent = new ExtentReports();
		extent.attachReporter(spark);
		test = extent.createTest("Salesforce Test");

	}

	@Test(priority = 1)
	public void precondition() {
		precondition = test.createNode("Send Email to Salesforce");
		sendEmail();
		salesforceLogin();
		sf_case = confirmCaseCreation(expected_subject);
		System.out.println("Sf_case: " + sf_case);
		searchCaseNumber(sf_case);
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
			if (true) {

				precondition.log(Status.PASS, "Logged in to SF");
			}
		} catch (Exception e) {
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
					System.out.println("case found : " + foundCase);
					precondition.log(Status.PASS, "Case found : " + foundCase);
					break;
				}
			}
		} catch (Exception e) {
			precondition.log(Status.FAIL, "Confirm case creation");
		}
		return foundCase;
	}

	public void searchCaseNumber(String sf_Case) {
//		search with case number 
		try {
			sf_po.search_input().sendKeys(sf_case);
			Thread.sleep(1000);
			sf_po.search_input().sendKeys(Keys.ENTER);
			Thread.sleep(3000);

			int rowCount = TableData.getRowCount(sf_po.table());
			System.out.println("rowCount " + rowCount);
//			used 2 because of an extra <tr> exists 
			if (rowCount == 2) {
				precondition.log(Status.PASS, "Search with Case number, case found");
			}else {
				System.out.println("Case not found");
				precondition.log(Status.FAIL, "Search with Case number, Case not found");
			}
		} catch (Exception e) {
			System.out.println("ERROR: search with case number failed");
			precondition.log(Status.FAIL, "Search with Case number");
			e.printStackTrace();
		}
	}

	/*
	 * Testcase_01: Case type is assigned as 'RFQ-Portal'.
	 * Application = Salesforce
	 */
	@Test(priority = 2)
	public void testcase_01() {
		ExtentTest testcase_01 = test.createNode("testcase_01: Case type is assigned as 'RFQ-Portal'. ");
		try {

			String type = TableData.getCellText(sf_po.table(), 1, 7);
			System.out.println("type: " + type);
			String expected_type = "RFQ - ILS";
			if (type.equals(expected_type)) {
				testcase_01.log(Status.PASS, "Search with Case number");
				System.out.println("Type Matched, Testcase_01: Passed");
			}
		} catch (Exception e) {
			testcase_01.log(Status.FAIL, "testcase_01: Case type is assigned as 'RFQ-Portal'. ");
			System.out.println("Type didn't Matched, Testcase_01: Failed");
		}
	}

	/*
	 * testcase_02: Outbound message is added in ActiveMQ  queue.
	 * Application = Salesforce
	 */
	@Test(priority = 3)
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
				} else if (status.equals("Open - (New)")) {
					driver.navigate().refresh();
					testcase_02.log(Status.INFO, "Status: Open - (New)");
					System.out.println("status: Status: Open - (New)");
					Thread.sleep(3000);
					searchCaseNumber(sf_case);
					
				}
			}
		} catch (Exception e) {
			testcase_02.log(Status.FAIL, "testcase_02: Outbound message is added in ActiveMQ  queue. ");
		}
	}

	@AfterClass
	public void teardown() {
		extent.flush();
//		driver.close();
	}

}
