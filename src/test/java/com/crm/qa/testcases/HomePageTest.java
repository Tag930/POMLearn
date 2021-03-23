package com.crm.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.crm.qa.base.TestBase;
import com.crm.qa.pages.ContactsPage;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;
import com.crm.qa.utilities.TestUtil;

public class HomePageTest extends TestBase {
	HomePage homePage;
	LoginPage loginPage;
	TestUtil testUtil;
	ContactsPage contactsPage;
	
	@BeforeMethod
	public void setup(){
		initialization();
		testUtil= new TestUtil();
		homePage = new HomePage();
		loginPage = new LoginPage();
		contactsPage = new ContactsPage();
		loginPage.login(prop.getProperty("username"),prop.getProperty("password"));
	}
	
	@Test
	public void UserloggedInTest() {
		testUtil.switchtoFrame();
		String name = homePage.verifyloggedusername();
		Assert.assertEquals(name,"  User: group automation");
	}
	
	@Test
	public void homePageTitleTest() {
		String title =homePage.verifyHomePageTitle();
		Assert.assertEquals(title,"CRMPRO","Wrong Page Title");
	}
	
	@Test(priority=2)
	public void verifyContactsLink() {
		testUtil.switchtoFrame();
		contactsPage = homePage.clickOnContactsLink();
	}
	
	
	
	@AfterMethod
	public void teardown() {
		driver.quit();
	}
	
}
