package nts.uk.ctx.at.record.dom.daily;

import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.latetime.IntervalExemptionTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;

/**
 * 日別実績の遅刻時間
 * @author ken_takasu
 *
 */
@Value
public class LateTimeOfDaily {
	
	private TimeWithCalculation lateTime;
	private TimeWithCalculation lateDeductionTime;
	private WorkNo workNo;//workNo型で作り直す必要がある
	private TimevacationUseTimeOfDaily timePaidUseTime;
	private IntervalExemptionTime exemptionTime;
	
}
