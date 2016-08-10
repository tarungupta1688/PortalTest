package com.portal.automation.pages;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.portal.automation.common.AbstractPage;
import com.portal.automation.common.PropertyMap;

public class ClassSearchPage extends AbstractPage {
	private WebDriver driver;
	private Log log = LogFactory.getLog(ClassSearchPage.class);
	private static Properties properties = PropertyMap.getGUIProperties();
	private int i,j;
	public ClassSearchPage(WebDriver driver) {
		this.driver = driver;
	}

	@Override
	protected WebDriver getDriver() {
		return driver;
	}
	
	
	public boolean gotoLandingPage() {
		try {
			String loginUrl = PropertyMap.getInstance().getAppBaseURL();
			driver.get(loginUrl);
			try
			  {
			   driver.navigate().to("javascript:document.getElementById('overridelink').click()");
			  }
			  
			   catch (Exception e) {
			             // do nothing as this exception is expected if no security ssl cert issue
			  }
			return true;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return false;
		}
	}
	

	public boolean verifyLocationforResultsClasses(String searchedLocation, String tillDate) {
		try {
			log.info("Clicking Clear Filter...");
			clickElement(By.xpath(properties.getProperty("Equinox.Classes.Search.Clear.Filter")), "Clear Filter");
			wait(2);
			log.info("Entering Location:: " + searchedLocation);
			fillField(By.xpath(properties.getProperty("Equinox.Classes.Search.Location.Input")), searchedLocation);
			wait(2);
			clickElement(By.xpath(properties.getProperty("Equinox.Classes.Searched.Location")), "Searched Location");
			wait(5);
			int weekDays = driver.findElements(By.xpath(properties.getProperty("Equinox.Classes.Week.Days"))).size();
			for(i=2; i<=weekDays+1; i++)
			{
				wait(2);
				List <WebElement> results = driver.findElements(By.xpath(properties.getProperty("Equinox.Classes.Result.Location")));
				for (j=0;j<results.size();j++)
				{
					if(results.get(j).getText().equals(searchedLocation))
						continue;
					else
						break;
				}
				if (j==results.size() && (getText(By.xpath(properties.getProperty("Equinox.Classes.Current.Date"))).equals(tillDate)))
				{
					return true;
				}
				else if(j==results.size() && (i!=weekDays+1))
				{				
					clickElement(By.xpath(properties.getProperty("Equinox.Classes.Week.Days")+"["+i+"]"), "Next Day");
					continue;
				}
				
				else if(j==results.size() && (i==weekDays+1))
				{
					String currentURL = driver.getCurrentUrl();
					String[] parts = currentURL.split("=");
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date date =  format.parse(parts[3]);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					calendar.add(Calendar.DAY_OF_YEAR, 1);
					String nextDate = format.format(calendar.getTime());
					String nextWeekURL = parts[0]+"="+parts[1]+"="+parts[2]+"="+nextDate;
					
					driver.get(nextWeekURL);
					wait(2);i=1;
					continue;				
				}
				else
					return false;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}