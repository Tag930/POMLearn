package com.crm.qa.testcases;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.crm.qa.base.TestBase;
import com.crm.qa.pages.ContactsPage;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;
import com.crm.qa.utilities.TestUtil;


public class ContactsPageTest extends TestBase {
	TestUtil testUtil;
	LoginPage loginPage;
	HomePage homePage;
	ContactsPage contactsPage;
	
	String sheetname="contacts";
	
	//For Screenshot in Extent report
	ExtentReports extent;
	ExtentTest logger;
	
	@BeforeMethod
	public void setup(Method method) {
		//For Screenshot in Extent report
		ExtentHtmlReporter reporter=new ExtentHtmlReporter("./test-output/learn_automation1.html");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		logger=extent.createTest(method.getName());
		logger.info("Enter initialization Phase");
		
		
		initialization();
		testUtil= new TestUtil();
		loginPage= new LoginPage();
		homePage=new HomePage();
		contactsPage= new ContactsPage();
		homePage= loginPage.login(prop.getProperty("username"),prop.getProperty("password"));
		testUtil.switchtoFrame();
		homePage.clickOnContactsLink();
	}
	
	@Test(enabled=false)
	public void verifyContactsPageLabel() {
		Assert.assertTrue(contactsPage.verifyContactsLabel(),"Contacts Label is missing on the Page");
	}
	
	@Test(priority=2)
	public void selectContactsTest() {
		boolean b =contactsPage.selectContactsByName("David Cris");
		logger.warning("Hey this is just a warning");
		boolean c = false;
		try {
			c = contactsPage.selectContactsByName("h");
		} catch (Exception e) {
			System.out.println("Element Not Found");
			e.printStackTrace();
		}
		
		logger.fatal("This is just a fatal error message");
		boolean d =contactsPage.selectContactsByName("Justin Jones");
		SoftAssert sf = new SoftAssert();
		sf.assertTrue(b);
		sf.assertTrue(c);
		sf.assertTrue(d);
		sf.assertAll();
	}
	
	@DataProvider
	public Object[][] getCRMTestData() {
		Object data[][]=TestUtil.getTestData(sheetname);
		return data;
	}
	
	
	@Test(dataProvider = "getCRMTestData",enabled=false)
	public void validateCreateNewContact(String title,String firstname,String lastname, String company) {
		homePage.clickOnNewContact();
		//contactsPage.createNewContact("Mr.", "Tommy", "Shelby", "Google");
		contactsPage.createNewContact(title, firstname, lastname, company);
	}
	
	
	
	
	@AfterMethod
	public void teardown(ITestResult result) throws IOException {
		
		if (ITestResult.FAILURE==result.getStatus()) {
			String path=TestUtil.takeScreenshotAtEndOfTest();
			
			logger.fail(result.getThrowable().getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(path).build());
		}
		
		
		driver.quit();
		logger.info("************Browser Closed*************");
		extent.flush();
		
	}

}
