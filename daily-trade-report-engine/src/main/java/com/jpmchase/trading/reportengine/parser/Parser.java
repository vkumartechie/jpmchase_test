package com.jpmchase.trading.reportengine.parser;

import java.util.List;

import com.jpmchase.trading.reportengine.dto.Instruction;
import com.jpmchase.trading.reportengine.exception.ReportingEngineException;

public interface Parser {

	public List<Instruction> parseInstructions() throws ReportingEngineException;
}
