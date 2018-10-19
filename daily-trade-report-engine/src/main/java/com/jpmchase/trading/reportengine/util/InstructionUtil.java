package com.jpmchase.trading.reportengine.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.jpmchase.trading.reportengine.dto.Currency;
import com.jpmchase.trading.reportengine.dto.Entity;
import com.jpmchase.trading.reportengine.dto.Instruction;
import com.jpmchase.trading.reportengine.operations.SettlementInstruction;

/**
 * This class is used as a utility for Instructions
 */
public class InstructionUtil {
	public static Function<? super Instruction, ? extends Instruction> adjustSettlementDate() {
		return temp -> {
			LocalDate settlementDate = temp.getSettlementDate();
			String currency = temp.getCurrency();
			Instruction in = new Instruction();
			in.setEntity(temp.getEntity());
			in.setTradeType(temp.getTradeType());
			in.setExchangeRate(temp.getExchangeRate());
			in.setCurrency(currency);
			in.setInstructionDate(temp.getInstructionDate());
			in.setUnits(temp.getUnits());
			in.setPricePerUnit(temp.getPricePerUnit());
			in.setSettlementDate(getAdjustedSettlementDate(currency, settlementDate));
			return in;
		};
	}

	public static LocalDate getAdjustedSettlementDate(String currency, LocalDate settlementDate) {
		LocalDate adjustedSettlementDate = settlementDate;
		if (Currency.AED.name().equals(currency) || Currency.SAR.name().equals(currency)) {
			if (settlementDate.getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
				adjustedSettlementDate = settlementDate.plus(Period.ofDays(2));
			} else if (settlementDate.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
				adjustedSettlementDate = settlementDate.plus(Period.ofDays(1));
			}
		} else {
			if (settlementDate.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
				adjustedSettlementDate = settlementDate.plus(Period.ofDays(2));
			} else if (settlementDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
				adjustedSettlementDate = settlementDate.plus(Period.ofDays(1));
			}
		}
		return adjustedSettlementDate;
	}

	/**
	 * This method is used to calculate daily trade amounts
	 * @param filteredInstructions
	 * @return
	 */
	public static Map<LocalDate, Double> calculateDailyTradeAmounts(List<Instruction> filteredInstructions) {
		Map<LocalDate, Double> dailyTradeAmountsMap = new LinkedHashMap<>();
		for (int day = 1; day <= 30; day++) {
			Double tradeAmount = 0d;
			LocalDate currentDate = LocalDate.of(2018, Month.OCTOBER, day);

			List<Instruction> settlementDateList = filteredInstructions.stream()
					.filter(SettlementInstruction.isForSettlement(currentDate)).collect(Collectors.toList());

			for (Instruction instruction : settlementDateList) {

				Double settlementAmount = instruction.getPricePerUnit() * instruction.getUnits()
						* instruction.getExchangeRate();

				tradeAmount += settlementAmount;
			}
			dailyTradeAmountsMap.put(currentDate, tradeAmount);
		}
		return dailyTradeAmountsMap;
	}

	public static List<Entity> getEntityRankings(List<Instruction> filteredInstructions) {

		Map<String, Entity> entityTrades = new HashMap<>();
		for (Instruction instruction : filteredInstructions) {
			String entityName = instruction.getEntity();

			Entity entity = new Entity(entityName);

			Double tradeAmount = instruction.getPricePerUnit() * instruction.getUnits() * instruction.getExchangeRate();
			if (null != entityName && null != entityTrades.get(entityName)) {
				Entity existingEntity = entityTrades.get(entityName);
				tradeAmount += existingEntity.getTradeAmount();
				entity.setTradeAmount(tradeAmount);
			} else {
				entity.setTradeAmount(tradeAmount);
			}

			entityTrades.put(entityName, entity);
		}

		List<Entity> entities = entityTrades.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());

		entities.sort((entity1, entity2) -> entity2.getTradeAmount().compareTo(entity1.getTradeAmount()));
		return entities;
	}
}
