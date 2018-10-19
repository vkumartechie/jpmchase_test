package com.jpmchase.trading.reportengine.exception;

/**
 * The Class ReportingEngineException.
 *
 * @author Vikash kumar
 */
public class ReportingEngineException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4286604544244420054L;

	/**
	 * create a new ReportingEngineException.
	 *
	 * @param message
	 *            the message
	 */
	public ReportingEngineException(final String message) {
		super(message);
	}

	/**
	 * create a new  exception.
	 *
	 * @param message
	 *            the message
	 * @param exception
	 *            the exception
	 */
	public ReportingEngineException(final String message, final Exception exception) {
		super(message, exception);
	}

	/**
	 * create a new  exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public ReportingEngineException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
