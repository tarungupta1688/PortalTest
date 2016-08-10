package com.portal.automation.common;

/**
 * Base class for all unrecoverable errors that can be thrown by the automation
 * suite.
 * 
 */
public class AutomationError extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AutomationError(String message, Throwable cause) {
		super(message, cause);
	}

	public AutomationError(String message) {
		super(message);
	}

	public AutomationError(Throwable cause) {
		super(cause);
	}

}
