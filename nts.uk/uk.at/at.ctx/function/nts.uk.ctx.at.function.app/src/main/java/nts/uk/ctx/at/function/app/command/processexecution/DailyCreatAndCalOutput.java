package nts.uk.ctx.at.function.app.command.processexecution;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Data
@NoArgsConstructor
public class DailyCreatAndCalOutput {
	/* 日別作成の期間 */
	private DatePeriod dailyCreationPeriod;
	
	/* 日別計算の期間 */
	private DatePeriod dailyCalcPeriod;
}
