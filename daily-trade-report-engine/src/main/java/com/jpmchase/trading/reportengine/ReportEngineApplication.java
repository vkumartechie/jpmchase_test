package com.jpmchase.trading.reportengine;

import com.jpmchase.trading.reportengine.dto.ServiceResponse;
import com.jpmchase.trading.reportengine.exception.ReportingEngineException;
import com.jpmchase.trading.reportengine.service.PrinterService;
import com.jpmchase.trading.reportengine.service.impl.PrinterServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ReportEngineApplication.
 */
public class ReportEngineApplication {

	/**
	 * his method is used to generate the report and print in console
	 * @param reportType : reportType can be ISR/OSR/IRR/ORR
	 * @return ServiceResponse
	 * @throws ReportingEngineException
	 */
	public ServiceResponse generateReport(String reportType) throws ReportingEngineException {
		PrinterService printerService = new PrinterServiceImpl();
		return printerService.print(reportType);
	}
}
