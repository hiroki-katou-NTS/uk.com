package nts.uk.ctx.at.record.dom.daily;

import lombok.Getter;
import lombok.Value;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkNo;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 日別実績の早退時間
 * @author ken_takasu
 *
 */
@Value
public class LeaveEarlyTimeOfDaily {
	
	private TimeWithCalculation leaveEarlyTime;
	private TimeWithCalculation leaveEarlyDeductionTime;
	private WorkNo workNo;
	@Getter
	private TimevacationUseTimeOfDaily timePaidUseTime;
	
	public static LeaveEarlyTimeOfDaily noLeaveEarly(WorkNo workNo) {
		return new LeaveEarlyTimeOfDaily(TimeWithCalculation.sameTime(new AttendanceTime(0)),
									TimeWithCalculation.sameTime(new AttendanceTime(0)), 
									workNo, 
									new TimevacationUseTimeOfDaily(new AttendanceTime(0),new AttendanceTime(0),new AttendanceTime(0),new AttendanceTime(0)));
	}
	
}
