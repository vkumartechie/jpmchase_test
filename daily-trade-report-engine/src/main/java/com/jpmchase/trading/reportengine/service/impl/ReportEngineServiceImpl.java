package com.jpmchase.trading.reportengine.service.impl;

import com.jpmchase.trading.reportengine.dto.Entity;
import com.jpmchase.trading.reportengine.dto.Instruction;
import com.jpmchase.trading.reportengine.dto.TradeType;
import com.jpmchase.trading.reportengine.exception.ReportingEngineException;
import com.jpmchase.trading.reportengine.parser.CSVParser;
import com.jpmchase.trading.reportengine.parser.Parser;
import com.jpmchase.trading.reportengine.service.ReportEngineService;
import com.jpmchase.trading.reportengine.util.InstructionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The Class ReportEngineService.
 */
public class ReportEngineServiceImpl implements ReportEngineService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(ReportEngineServiceImpl.class);

	/** The instruction parser. */
	private Parser instructionParser;

	/** The instructions. */
	private List<Instruction> instructions;

	private static final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("en", "US"));

	private String message = "";

	/**
	 * This method is used for print daily trade amount
	 * @param tradeType
	 * @throws ReportingEngineException
	 */
	@Override
	public void printDailyTradeAmounts(TradeType tradeType) throws ReportingEngineException {

		if (tradeType.name().equals(TradeType.BUY.name())) {
			message = "Outgoing trade amount";
		} else {
			message = "Incoming trade amount";
		}

		instructions = getFilteredInstructions(tradeType);

		Map<LocalDate, Double> dailyTradeAmountsMap = InstructionUtil.calculateDailyTradeAmounts(instructions);

		dailyTradeAmountsMap.entrySet().stream().forEach(instruction -> logger
				.info(message + instruction.getKey() + " is " + currencyFormatter.format(instruction.getValue())));
	}

	/**
	 * This method is used for print Entities by ranking
	 * @param tradeType
	 * @throws ReportingEngineException
	 */
	@Override
	public void printEntitiesByRanking(TradeType tradeType) throws ReportingEngineException {

		if (TradeType.BUY.name().equals(tradeType.name())) {
			message = "Outgoing entities by ranking ...";
		} else {
			message = "Incoming entities by ranking ...";
		}

		instructions = getFilteredInstructions(tradeType);

		List<Entity> entities = InstructionUtil.getEntityRankings(instructions);

		//logger.info(message);
		System.out.println("************* \t*************");
		System.out.println("* Entity Name \tTrade Amount *");
		System.out.println("************* \t*************");
		entities.stream().forEach(
				entity -> System.out.println(entity.getName() + "\t" + currencyFormatter.format(entity.getTradeAmount())));
		System.out.println("************* \t*************");
	}

	private List<Instruction> getFilteredInstructions(TradeType tradeType) throws ReportingEngineException {

		instructionParser = new CSVParser();

		List<Instruction> instructions = instructionParser.parseInstructions();

		this.instructions = instructions.stream()
				.filter(instruction -> tradeType.name().equalsIgnoreCase(instruction.getTradeType()))
				.collect(Collectors.toList()).stream().map(InstructionUtil.adjustSettlementDate())
				.collect(Collectors.toList());

		this.instructions.sort((instruction1, instruction2) -> instruction1.getSettlementDate()
				.compareTo(instruction2.getSettlementDate()));

		return this.instructions;
	}
}
