package com.jpmchase.trading.reportengine;

import com.jpmchase.trading.reportengine.dto.ServiceResponse;
import com.jpmchase.trading.reportengine.exception.ReportingEngineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ReportEngineApplication.
 */
public class Main  {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	/**
	 * The main method.
	 *
	 * @param args is command line arguments. Possible value can be ISR,OSR,IRR,ORR
	 * @throws ReportingEngineException
	 */
	public static void main(final String[] args) throws ReportingEngineException {
		logger.info("Reporting engine application START...");
		ReportEngineApplication reportEngineApplication = new ReportEngineApplication();
		ServiceResponse response = reportEngineApplication.generateReport(args[0]);
		System.out.println(response.getResponseMessage());
	}


}
