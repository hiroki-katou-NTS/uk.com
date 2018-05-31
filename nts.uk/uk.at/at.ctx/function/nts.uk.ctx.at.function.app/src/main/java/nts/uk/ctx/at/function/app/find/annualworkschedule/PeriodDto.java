package nts.uk.ctx.at.function.app.find.annualworkschedule;

import lombok.Value;
/**
 * 期間
 */
@Value
public class PeriodDto {
	/** A3_2 開始年月 */
	String startYearMonth;
	/** A3_4 終了年月 */
	String endYearMonth;
	public PeriodDto(String startYearMonth, String endYearMonth) {
		super();
		this.startYearMonth = startYearMonth;
		this.endYearMonth = endYearMonth;
	}
}
