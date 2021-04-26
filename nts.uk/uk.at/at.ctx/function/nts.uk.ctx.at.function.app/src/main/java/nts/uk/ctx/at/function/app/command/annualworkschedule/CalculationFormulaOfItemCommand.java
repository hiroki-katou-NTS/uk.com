package nts.uk.ctx.at.function.app.command.annualworkschedule;

import lombok.Value;

@Value
public class CalculationFormulaOfItemCommand {

	/** 勤怠項目ID */
	private int attendanceItemId;

	/** オペレーション */
	private int operation;

}
