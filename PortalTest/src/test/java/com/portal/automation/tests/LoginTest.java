package com.portal.automation.tests;

import com.portal.automation.common.DriverFactory;
import com.portal.automation.common.Verification;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginTest extends Verification {

	private WebDriver driver;
	private Log log = LogFactory.getLog(LoginTest.class);
	
	@BeforeClass(alwaysRun = true)
	public void setup() throws InterruptedException {
		driver = DriverFactory.getDriverInstance();
	}
	
	@Test(description = "Verify Gmail Login")
	public void verifyLocationforResultsClasses() throws Exception{
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://mail.google.com");
		log.debug("--------Application Url entered as : mail.google.com");
		
		WebDriverWait wait = new WebDriverWait(driver,30);
		
		
		// handle Internet explorer
		try
		  {
		   driver.navigate().to("javascript:document.getElementById('overridelink').click()");
		  }
		  
		   catch (Exception e) {
		             // do nothing as this exception is expected if no security ssl cert issue
		  }
		
		driver.manage().window().maximize();

		System.out.println("Browser Maximized.....................");
		log.debug("--------Browser Maximized----------------------");
		driver.findElement(By.xpath("//input[@id='Email']")).clear();
		driver.findElement(By.xpath("//input[@id='Email']")).sendKeys("shivanisaxena985");
		System.out.println("Enter username.....................");
		log.debug("--------Enter Username----------------------");
		Thread.sleep(3000);
		
		try
		  {
			log.debug("--------Wait For Next Button Else enter the password----------------------");
			driver.findElement(By.xpath("//*[@id='next']")).click();
			log.debug("--------Click On Next Button----------------------");
			Thread.sleep(3000);
		  }
		  
		   catch (Exception e) {
		             
		  }
		
		driver.findElement(By.xpath("//*[@id='Passwd']")).clear();
		driver.findElement(By.xpath("//*[@id='Passwd']")).sendKeys("Dell@123");
		Thread.sleep(3000);
		System.out.println("Enter Password.....................");
		log.debug("--------Enter Password----------------------");
		
		driver.findElement(By.xpath("//*[@id='signIn']")).click();
		System.out.println("Click  Sign In.....................");
		log.debug("--------Click  Sign In----------------------");
		
		Thread.sleep(3000);
		String title= driver.getTitle();
		log.debug("--------Title of the Page is ----------------------"+title);
		System.out.println(title);
		
	}
	
				
	@AfterClass(alwaysRun = true)
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
}
}