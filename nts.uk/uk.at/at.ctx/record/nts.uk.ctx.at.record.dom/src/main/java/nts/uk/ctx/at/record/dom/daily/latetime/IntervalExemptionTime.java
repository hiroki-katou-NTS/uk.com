package nts.uk.ctx.at.record.dom.daily.latetime;

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
	//インターバル時間
	private AttendanceTime intervalTime;
	
	//インターバル出勤時刻
	private AttendanceTime intervalAttendanceClock;
	
	//免除時間
	private AttendanceTime exemptionTime;
}
