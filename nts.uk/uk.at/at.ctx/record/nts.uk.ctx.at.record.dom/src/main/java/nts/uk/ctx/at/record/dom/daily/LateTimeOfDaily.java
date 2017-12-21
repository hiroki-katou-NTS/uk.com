package nts.uk.ctx.at.record.dom.daily;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 日別実績の遅刻時間
 * @author ken_takasu
 *
 */
@Value
public class LateTimeOfDaily {
	
	private TimeWithCalculation lateTime;
	private TimeWithCalculation lateDeductionTime;
	private int workNo;//workNo型で作り直す必要がある
	private boolean calcClassification;
	
}
