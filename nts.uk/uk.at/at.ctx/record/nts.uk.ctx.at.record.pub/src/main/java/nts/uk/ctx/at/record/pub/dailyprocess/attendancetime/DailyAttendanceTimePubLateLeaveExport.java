package nts.uk.ctx.at.record.pub.dailyprocess.attendancetime;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;


/**
 * RequestList No.13 OutPut
 * @author keisuke_hoshina
 *
 */

@Getter
public class DailyAttendanceTimePubLateLeaveExport {
	//遅刻時間
	AttendanceTime lateTime;
	//早退時間
	AttendanceTime leaveEarlyTime;
	
	/**
	 * Constructor 
	 */
	public DailyAttendanceTimePubLateLeaveExport(AttendanceTime lateTime, AttendanceTime leaveEarlyTime) {
		super();
		this.lateTime = lateTime;
		this.leaveEarlyTime = leaveEarlyTime;
	}
}
