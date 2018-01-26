package nts.uk.ctx.at.record.dom.daily;

import lombok.Getter;
import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.latetime.IntervalExemptionTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 日別実績の早退時間
 * @author ken_takasu
 *
 */
@Value
public class LeaveEarlyTimeOfDaily {
	//早退時間
	private TimeWithCalculation leaveEarlyTime;
	//早退控除時間
	private TimeWithCalculation leaveEarlyDeductionTime;
	//勤務No
	private WorkNo workNo;
	//休暇使用時間
	@Getter
	private TimevacationUseTimeOfDaily timePaidUseTime;
	//インターバル時間
	private IntervalExemptionTime intervalTime;
	
	
	public static LeaveEarlyTimeOfDaily noLeaveEarly(WorkNo workNo) {
		return new LeaveEarlyTimeOfDaily(TimeWithCalculation.sameTime(new AttendanceTime(0)),
									TimeWithCalculation.sameTime(new AttendanceTime(0)), 
									workNo, 
									new TimevacationUseTimeOfDaily(new AttendanceTime(0),new AttendanceTime(0),new AttendanceTime(0),new AttendanceTime(0)),
									new IntervalExemptionTime(new AttendanceTime(0), new AttendanceTime(0), new AttendanceTime(0)));
	}
	
}
