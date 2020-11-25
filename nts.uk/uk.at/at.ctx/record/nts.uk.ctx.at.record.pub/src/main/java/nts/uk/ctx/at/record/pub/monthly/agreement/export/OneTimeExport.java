package nts.uk.ctx.at.record.pub.monthly.agreement.export;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearTime;

@Data
@Builder
public class OneTimeExport {

	/** エラーアラーム時間 */
	private int errorTime;
	/** エラーアラーム時間 */
	private int alarmTime;
	/** 上限時間 */
	private int upperLimit;
	
	public static OneTimeExport copy(OneMonthTime domain) {
		return OneTimeExport.builder()
		.alarmTime(domain.getErAlTime().getAlarm().valueAsMinutes())
		.errorTime(domain.getErAlTime().getError().valueAsMinutes())
		.upperLimit(domain.getUpperLimit().valueAsMinutes())
		.build();
	}
	
	public static OneTimeExport copy(OneYearTime domain) {
		return OneTimeExport.builder()
		.alarmTime(domain.getErAlTime().getAlarm().valueAsMinutes())
		.errorTime(domain.getErAlTime().getError().valueAsMinutes())
		.upperLimit(domain.getUpperLimit().valueAsMinutes())
		.build();
	}
}
