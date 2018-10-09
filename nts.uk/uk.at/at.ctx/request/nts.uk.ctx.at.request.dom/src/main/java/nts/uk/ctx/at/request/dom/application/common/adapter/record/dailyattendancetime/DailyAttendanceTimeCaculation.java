package nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface DailyAttendanceTimeCaculation {
	public DailyAttendanceTimeCaculationImport getCalculation(String employeeID,
																	GeneralDate ymd,
																	String workTypeCode,
																	String workTimeCode,
																	Integer workStartTime,
																	Integer workEndTime,
																	List<Integer> breakStartTime,
																	List<Integer> breakEndTime);
	
	public DailyAttenTimeLateLeaveImport calcDailyLateLeave(DailyAttenTimeParam dailyAttenTimeParam);
}
