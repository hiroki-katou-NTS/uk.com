package nts.uk.ctx.at.function.app.find.annualworkschedule;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CalculationFormulaOfItemDto {

	/** オペレーション. */
	private int operation;

	/** 勤怠項目. */
	private int attendanceItemId;
}
