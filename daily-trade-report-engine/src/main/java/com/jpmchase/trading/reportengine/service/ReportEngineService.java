package com.jpmchase.trading.reportengine.service;

import com.jpmchase.trading.reportengine.dto.TradeType;
import com.jpmchase.trading.reportengine.exception.ReportingEngineException;

/**
 * The Interface ReportEngineService.
 */
public interface ReportEngineService {

	public void printDailyTradeAmounts(TradeType tradeType) throws ReportingEngineException;

	public void printEntitiesByRanking(TradeType tradeType) throws ReportingEngineException;
}
