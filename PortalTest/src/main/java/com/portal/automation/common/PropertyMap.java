package com.portal.automation.common;

import java.io.IOException;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PropertyMap{

	private static Log log = LogFactory.getLog(PropertyMap.class);

	private Properties properties;

	public String getChromedriverPath() {
		return properties.getProperty("Chrome.Driver.Path");
	}

	public String getIEdriverPath() {
		return properties.getProperty("IE.Driver.Path");
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public static PropertyMap getInstance() {
		return LazyHolder.propertyMap;
	}

	public String getBrowser() {
		return properties.getProperty("browser");
	}

	public String getAppBaseURL() {
		return properties.getProperty("application.base.url");
	}
	
	private PropertyMap(Properties properties) {
		this.properties = properties;
	}

	public static Properties getGUIProperties() {
		Properties p = new Properties();
		try {
			p.load(PropertyMap.class.getResourceAsStream("/gui.properties"));
		} catch (IOException e) {
			log.fatal("Failed to load properties: " + e.getMessage(), e);
			throw new AutomationError(e);
		}
		return p;
	}
	private static class LazyHolder {
		public static PropertyMap propertyMap = createPropertyMap();

		private static PropertyMap createPropertyMap() {

			Properties p = new Properties();
			try {
				p.load(LazyHolder.class.getResourceAsStream("/automation.properties"));
			} catch (IOException e) {
				log.fatal("Failed to load properties: " + e.getMessage(), e);
				throw new AutomationError(e);
			}

			return new PropertyMap(p);
		}
	}

}
