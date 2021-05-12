package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;

/**
 * 
 * @author sonnlb
 *
 *         日別実績
 */
@Builder
@Getter
public class DailyResult {
	// 年月日
	private GeneralDate ymd;
	// 日別勤怠の勤務情報
	private WorkInfoOfDailyAttendance workInfo;
	// 日別勤怠の勤怠時間
	private Optional<AttendanceTimeOfDailyAttendance> attendanceTime;
}
