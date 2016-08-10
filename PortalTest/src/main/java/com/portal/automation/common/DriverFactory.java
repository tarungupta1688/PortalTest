package com.portal.automation.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

public class DriverFactory {

	private static Log log = LogFactory.getLog(DriverFactory.class);

	public static WebDriver getDriverInstance() {
		return createDriver(PropertyMap.getInstance().getBrowser());
	}

	static WebDriver d;

	private static FirefoxProfile GetFirefoxProfile() {
		FirefoxProfile firefoxProfile = new FirefoxProfile();
		firefoxProfile.setPreference("network.automatic-ntlm-auth.trusted-uris", "http://localhost");
		return firefoxProfile;
	}

	public static WebDriver createDriver(String browserId) {

		/*if (d != null)
			return d;*/
		if (browserId.startsWith("firefox")) {
			log.info("Starting Firefox...");
			String browserPath = null;
			FirefoxProfile ffprofile = GetFirefoxProfile();
			String[] components = browserId.split("\\s", 2);
			if (components.length > 1) {
				browserPath = components[1];
			}
			if (browserPath == null) {
				d = new FirefoxDriver(ffprofile);
			} else {
				d = new FirefoxDriver(new FirefoxBinary(new File(browserPath)), new FirefoxProfile());
			}

		} else if (browserId.equals("iexplore")) {
			log.info("Starting Internet Explorer...");
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			System.setProperty("webdriver.ie.driver", PropertyMap.getInstance().getIEdriverPath());
			d = new InternetExplorerDriver(capabilities);

		} else if (browserId.equals("chrome")) {
			log.info("Starting Internet chrome...");
			System.setProperty("webdriver.chrome.driver", PropertyMap.getInstance().getChromedriverPath());
			d = new ChromeDriver();

		} else if (browserId.equals("htmlunitdriver")) {
			log.info("staring html unit driver ..");
			d = new HtmlUnitDriver(true);
			((HtmlUnitDriver) d).setJavascriptEnabled(true);
		} else {

			throw new UnsupportedBrowserError("Unknown browser: " + browserId);
		}
		d.manage().window().maximize();

		return d;
	}

	public static class UnsupportedBrowserError extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public UnsupportedBrowserError(String message) {
			super(message);
		}

	}

}
