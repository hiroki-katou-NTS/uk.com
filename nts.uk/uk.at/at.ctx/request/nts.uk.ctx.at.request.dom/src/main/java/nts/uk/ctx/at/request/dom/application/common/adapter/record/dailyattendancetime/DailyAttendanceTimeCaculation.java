package nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime;

import nts.arc.time.GeneralDate;

public interface DailyAttendanceTimeCaculation {
	public DailyAttendanceTimeCaculationImport getCalculation(String employeeID,
																	GeneralDate ymd,
																	String workTypeCode,
																	String workTimeCode,
																	Integer workStartTime,
																	Integer workEndTime,
																	Integer breakStartTime,
																	Integer breakEndTime);
	
	public DailyAttenTimeLateLeaveImport calcDailyLateLeave(DailyAttenTimeParam dailyAttenTimeParam);
}
