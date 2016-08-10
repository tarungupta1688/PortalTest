package com.portal.automation.tests;

import com.portal.automation.common.DriverFactory;
import com.portal.automation.common.Verification;
import com.portal.automation.pages.ClassSearchPage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ClassesSearchResultsTest extends Verification {

	private WebDriver driver;
	private ClassSearchPage classSearchPage;
	private Log log = LogFactory.getLog(ClassesSearchResultsTest.class);
	private String searchedLocation = "FLATIRON";
	private String tillDate = "AUG 25"; // Should be ahead of current date
	
	@BeforeClass(alwaysRun = true)
	public void setup() throws InterruptedException {
		driver = DriverFactory.getDriverInstance();
		classSearchPage = new ClassSearchPage(driver);	
	}
	
	
	@Test(description = "Verify LandingPage")
	public void landingPage() {
		assertEquals(true, classSearchPage.gotoLandingPage());
	}
	
	@Test(description = "Verify location for searched results for location-FLATIRON")
	public void verifyLocationforResultsClasses(){
		log.info("Verify location for searched results for location-FLATIRON test case started...");
		assertEquals(true, classSearchPage.verifyLocationforResultsClasses(searchedLocation,tillDate));
		
	}
	
				
	@AfterClass(alwaysRun = true)
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
}
}