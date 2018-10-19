package com.jpmchase.trading.reportengine.operations;

import java.time.LocalDate;
import java.util.function.Predicate;

import com.jpmchase.trading.reportengine.dto.Instruction;

public class SettlementInstruction {
	
	public static Predicate<Instruction> isForSettlement(LocalDate currentDate) {
		return instruction -> instruction.getSettlementDate().equals(currentDate);
	}
}
