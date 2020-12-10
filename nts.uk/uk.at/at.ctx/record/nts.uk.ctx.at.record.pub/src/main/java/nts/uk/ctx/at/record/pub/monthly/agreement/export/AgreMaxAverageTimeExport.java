package nts.uk.ctx.at.record.pub.monthly.agreement.export;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreMaxAverageTime;

@Data
@Builder
@AllArgsConstructor
public class AgreMaxAverageTimeExport {

	/** 期間 */
	private YearMonthPeriod period;
	/** 合計時間 */
	private int totalTime;
	/** 平均時間 */
	private int averageTime;
	/** 状態 */
	private int status;
	
	public static AgreMaxAverageTimeExport copy(AgreMaxAverageTime domain) {
		
		return AgreMaxAverageTimeExport.builder()
				.period(domain.getPeriod())
				.totalTime(domain.getTotalTime().valueAsMinutes())
				.averageTime(domain.getAverageTime().valueAsMinutes())
				.status(domain.getStatus().value)
				.build();
	}
}
