package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.ortherpackage.classfunction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * インターバル時間     (遅刻・早退用)
 * @author keisuke_hoshina
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IntervalExemptionTime {
	//免除時間
	private AttendanceTime exemptionTime;
	
	public static IntervalExemptionTime defaultValue() {
		return new IntervalExemptionTime(new AttendanceTime(0));
	}
}
