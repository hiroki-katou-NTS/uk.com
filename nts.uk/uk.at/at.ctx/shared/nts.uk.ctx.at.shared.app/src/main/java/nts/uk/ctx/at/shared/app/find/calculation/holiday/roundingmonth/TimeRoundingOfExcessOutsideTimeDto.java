package nts.uk.ctx.at.shared.app.find.calculation.holiday.roundingmonth;

import lombok.Data;

/**
 * Time rounding of Excess outside time DTO
 * @author HoangNDH
 *
 */
@Data
public class TimeRoundingOfExcessOutsideTimeDto {

	// 単位
	private int roundingUnit;
	// 時間外超過の端数処理
	private int roundingProcess;
}
