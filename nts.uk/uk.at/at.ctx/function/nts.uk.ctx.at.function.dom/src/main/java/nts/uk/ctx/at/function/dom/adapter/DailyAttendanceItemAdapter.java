package nts.uk.ctx.at.function.dom.adapter;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.dailyattendanceitem.AttendanceResultImport;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 	日次の勤怠項目を取得するAdapter
 */
public interface DailyAttendanceItemAdapter {
	
	List<DailyAttendanceItemAdapterDto> getDailyAttendanceItem(String companyId, List<Integer> dailyAttendanceItemIds);
	
	List<DailyAttendanceItemAdapterDto> getDailyAttendanceItemList(String companyId);
	
	AttendanceResultImport getValueOf(String employeeId, GeneralDate workingDate, List<Integer> itemIds);

	List<AttendanceResultImport> getValueOf(List<String> employeeIds, DatePeriod workingDate, List<Integer> itemIds);
}
