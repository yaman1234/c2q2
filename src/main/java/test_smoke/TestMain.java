package test_smoke;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.SQ_pageObjects;
import utilities.ExcelRead;
import utilities.UtilBase;
import utilities.WebElementLib;

public class TestMain extends UtilBase {
	SQ_pageObjects sq_po = new SQ_pageObjects();

		@Test
		public void testLoginPage() {
		String actual = "text";
		String expected = "";
		Assert.assertEquals(actual, expected, "First Assert ");
		System.out.println("Assert");
		}
		
		@Test
		public void testLoginPage2() {
			Assert.assertFalse(true);
		String actual = "";
		String expected = "";
//		Assert.assertEquals(actual, expected, "First Assert ");
		System.out.println("Assert");
		}
		
		@Test
		public void sqlogin() throws InterruptedException {
//			load the url
			String sq_baseurl = "http://" +ExcelRead.getData(10, 5, "variables");
			String sq_username = ExcelRead.getData(1, 7,"variables");
			String sq_password = ExcelRead.getData(2, 7,"variables");
			
			initialiseDriver();
			driver.get(sq_baseurl);

//			login
			sq_po.username_input().sendKeys(sq_username);
			sq_po.password_input().sendKeys(sq_password);
			sq_po.submit_button().click();
			Thread.sleep(2000);
			
			Assert.assertFalse(WebElementLib.doesElementExist(sq_po.signout_button()), "ERROR : SQ Login Failed");
		}
}
