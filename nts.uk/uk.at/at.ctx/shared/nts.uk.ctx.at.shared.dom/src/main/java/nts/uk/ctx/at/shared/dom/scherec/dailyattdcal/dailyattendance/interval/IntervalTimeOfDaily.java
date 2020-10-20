package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.interval;

import java.io.Serializable;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.shr.com.time.AttendanceClock;

/** 日別勤怠のインターバル時間 */
@Getter
public class IntervalTimeOfDaily implements Serializable {

	/***/
	private static final long serialVersionUID = 1L;

	/** インターバル出勤時刻: 勤怠時刻 */
	private AttendanceClock intervalAttendance;
	
	/** インターバル時間: 勤怠時間 */
	private AttendanceTime intervalTime;

	private IntervalTimeOfDaily(AttendanceClock intervalAttendance, AttendanceTime intervalTime) {
		super();
		this.intervalAttendance = intervalAttendance;
		this.intervalTime = intervalTime;
	}
	
	public static IntervalTimeOfDaily empty() {
		return new IntervalTimeOfDaily(new AttendanceClock(0), new AttendanceTime(0));
	}
	
	public static IntervalTimeOfDaily of(AttendanceClock intervalAttendance, AttendanceTime intervalTime) {
		return new IntervalTimeOfDaily(intervalAttendance, intervalTime);
	}
}
