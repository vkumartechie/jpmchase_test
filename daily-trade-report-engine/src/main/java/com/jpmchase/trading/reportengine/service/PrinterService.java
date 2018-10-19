package com.jpmchase.trading.reportengine.service;

import com.jpmchase.trading.reportengine.dto.ServiceResponse;
import com.jpmchase.trading.reportengine.exception.ReportingEngineException;

/**
 * The Interface ReportEngineService.
 */
public interface PrinterService {

	public ServiceResponse print(String reportType) throws ReportingEngineException;
}
