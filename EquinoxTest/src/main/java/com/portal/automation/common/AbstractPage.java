package com.portal.automation.common;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.google.common.base.Function;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;

public abstract class AbstractPage {
	
	protected abstract WebDriver getDriver();


	protected WebElement waitForElement(final By fieldLocator)
	{
		return waitForElement(fieldLocator, 30);
	}

	protected WebElement waitForElement(final By fieldLocator, int timeoutSeconds)
	{

		Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver()).withTimeout(timeoutSeconds, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

		WebElement field = null;
		try {
			field = wait.until(new Function<WebDriver, WebElement>() {

				public WebElement apply(WebDriver driver) {
					return driver.findElement(fieldLocator);
				}

			});
		} catch (Throwable t) {
			throw new AutomationError(t);
		}

		return field;
	}

	protected void wait(int seconds)
	{
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void fillField(By elementPath, String value)
	{
		try {
			if (value != null) {
				wait(1);
				WebElement element = waitForElement(elementPath);
				element.clear();
				element.sendKeys(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void fillField(WebElement source, By elementPath, String value)
	{

		if (value != null) {
			wait(1);
			WebElement element = source.findElement(elementPath);
			element.clear();
			element.sendKeys(value);
		}
	}

	public void clickElement(By elementPath, String value)
	{
		try {
			if (value != null) {
				WebElement element = waitForElement(elementPath);
				element.click();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isElementPresent(By elementPath)
	{
		try{						
			boolean isPresent = getDriver().findElements(elementPath).size()>0;
			if(isPresent)
			{
				return true;
			}
		}
		catch(ElementNotFoundException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public WebElement waitUntilElementPresent(By elementPath)
	{
		WebDriverWait wait = new WebDriverWait(getDriver(), 20);
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(elementPath));
		return element;

	}
	
	public String getText(By elementPath)
	{
		try {
			WebElement element = waitForElement(elementPath);
			String Text=element.getText();
			return Text;
		} catch (Exception e) {
			 e.printStackTrace();
			return null;
		}
		
	}


	
}
