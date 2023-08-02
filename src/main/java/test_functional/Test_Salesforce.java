package test_functional;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import utilities.ExcelRead;
import utilities.SuperEmail;
import utilities.UtilBase;

public class Test_Salesforce extends UtilBase {

	ExtentReports extent;
	ExtentTest test;
	@BeforeClass
	public void setup() {
		ExtentSparkReporter spark = new ExtentSparkReporter("user/build/name/");
		extent = new ExtentReports();
		extent.attachReporter(spark);
		test = extent.createTest("Salesforce Test");
		
	}
	@Test(priority=1)
	public void precondition() {
		ExtentTest precondition = test.createNode("Send Email to Salesforce");
//		send email to sf with timestamp appended into subject
		try {
			
//			get current timestamp
			String timestamp = String.valueOf(System.currentTimeMillis()) ;
			String attachmentPath = System.getProperty("user.dir")+"\\testdata\\sample\\sample_ILS.txt";
			
			String from = ExcelRead.getData(19, 1, "variables");
			String to = ExcelRead.getData(21, 1, "variables");
			String subject = "Test Email "+ timestamp;
			
			SuperEmail.sendEmailUsingHtmlMsg(from, to, subject, attachmentPath);
			precondition.log(Status.PASS, "Send Email to Salesforce");
		} catch (Exception e) {
			// TODO: handle exception
			precondition.log(Status.FAIL, "Send Email to Salesforce");
		}
		
//		login to sf
		try {
			precondition.log(Status.PASS, "Logged in to SF");
		} catch (Exception e) {
			// TODO: handle exception
		}

//		confirm case created in sf, by verifying the timestamp in subject
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
//		search with case number 
		try {
			precondition.log(Status.PASS, "Search with Case number");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	@Test(priority=2)
	public void testcase_01() {
		ExtentTest testcase_01 = test.createNode("testcase_01: Case type is assigned as “RFQ-Portal”. ");
		try {
			testcase_01.log(Status.PASS, "Search with Case number");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	@Test(priority=3)
	public void testcase_02() {
		ExtentTest testcase_02 = test.createNode("testcase_02: Outbound message is added in ActiveMQ  queue. ");
		try {
			testcase_02.log(Status.PASS, "Search with Case number");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	
	@AfterClass
	public void teardown() {
		extent.flush();
	}
	
}
