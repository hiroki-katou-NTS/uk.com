package nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class DailyAttenTimeLateLeaveImport {
	//遅刻時間
	AttendanceTime lateTime;
	//早退時間
	AttendanceTime leaveEarlyTime;
}
