package nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;

public interface DailyAttendanceTimeCaculation {
	public DailyAttendanceTimeCaculationImport getCalculation(
			String employeeID,
			GeneralDate ymd,
			String workTypeCode,
			String workTimeCode,
			List<TimeZone> lstTimeZone,
			List<Integer> breakStartTimes,
			List<Integer> breakEndTime);
	
	public DailyAttenTimeLateLeaveImport calcDailyLateLeave(DailyAttenTimeParam dailyAttenTimeParam);
}
