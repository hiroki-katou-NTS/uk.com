package nts.uk.ctx.at.function.dom.adapter.monthly.agreement;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.calendar.period.YearMonthPeriod;

@Data
@AllArgsConstructor
public class AgreMaxAverageTimeImport {

	/** 期間 */
	private YearMonthPeriod period;
	/** 合計時間 */
	private int totalTime;
	/** 平均時間 */
	private int averageTime;
	/** 状態 */
	private int status;
}
