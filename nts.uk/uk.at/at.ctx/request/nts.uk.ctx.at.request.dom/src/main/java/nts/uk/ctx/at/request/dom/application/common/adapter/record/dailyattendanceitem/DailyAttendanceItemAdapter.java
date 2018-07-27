package nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendanceitem;

import java.util.List;

import nts.uk.shr.com.time.calendar.period.DatePeriod;
/**
 * 
 * @author Anh.Bd
 *
 */
public interface DailyAttendanceItemAdapter {
	public List<AttendanceResultImport> getValueOf(List<String> employeeId, DatePeriod workingDate, List<Integer> itemIds);
}
