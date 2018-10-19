package com.jpmchase.trading.reportengine.service.impl;

import com.jpmchase.trading.reportengine.dto.ServiceResponse;
import com.jpmchase.trading.reportengine.dto.TradeType;
import com.jpmchase.trading.reportengine.exception.ReportingEngineException;
import com.jpmchase.trading.reportengine.service.PrinterService;
import com.jpmchase.trading.reportengine.service.ReportEngineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.jpmchase.trading.reportengine.constant.ReportEngineConstant.*;

/**
 * The Class is use to print the report.
 */
public class PrinterServiceImpl implements PrinterService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(PrinterServiceImpl.class);


	@Override
	public ServiceResponse print(String reportType) throws ReportingEngineException {
		ReportEngineService reportEngineService = new ReportEngineServiceImpl();
		String reportStatus;
		ServiceResponse response = new ServiceResponse();
		try {
			if (reportType.equalsIgnoreCase(ISR)) {
				// Generate incoming settlement report
				reportEngineService.printDailyTradeAmounts(TradeType.SELL);
				reportStatus = SUCCESS;
			} else if (reportType.equalsIgnoreCase(OSR)) {
				// Generate outgoing settlement report
				reportEngineService.printDailyTradeAmounts(TradeType.BUY);
				reportStatus = SUCCESS;
			} else if (reportType.equalsIgnoreCase(IRR)) {
				// Generate incoming rankings report
				reportEngineService.printEntitiesByRanking(TradeType.SELL);
				reportStatus = SUCCESS;
			} else if (reportType.equalsIgnoreCase(ORR)) {
				// Generate outgoing rankings report
				reportEngineService.printEntitiesByRanking(TradeType.BUY);
				reportStatus = SUCCESS;
			} else {
				logger.error("Invalid request. Please pass valid input");
				reportStatus = INVALID;
			}
		} catch (ReportingEngineException e) {
			logger.error("ReportingEngineException while generating the report:", e);
			reportStatus = FAILURE;
		} catch (Exception e) {
			logger.error("Technical Exception while generating the report:", e);
			reportStatus = TECHNICAL_ERROR;
		}
		response.setResponseMessage(reportStatus);
		return response;
	}
}
