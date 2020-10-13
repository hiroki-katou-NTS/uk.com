package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * インターバル時間     (遅刻・早退用)
 * @author keisuke_hoshina
 *
 */
@Getter
@AllArgsConstructor
public class IntervalExemptionTime {
	//免除時間
	private AttendanceTime exemptionTime;
	
	public IntervalExemptionTime() {
		super();
		this.exemptionTime = new AttendanceTime(0);
	}
	
	public static IntervalExemptionTime defaultValue() {
		return new IntervalExemptionTime(new AttendanceTime(0));
	}
}
