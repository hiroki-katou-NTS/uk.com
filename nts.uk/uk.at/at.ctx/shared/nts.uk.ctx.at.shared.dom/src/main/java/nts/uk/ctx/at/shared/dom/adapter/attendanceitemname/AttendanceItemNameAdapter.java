package nts.uk.ctx.at.shared.dom.adapter.attendanceitemname;

import java.util.List;
import java.util.Map;

public interface AttendanceItemNameAdapter {
	
	Map<Integer, String> getAttendanceItemNameAsMapName(List<Integer> dailyAttendanceItemIds, int typeOfAttendanceItem);

	List<MonthlyAttendanceItemNameDto> getMonthlyAttendanceItemName(List<Integer> attendanceItemIds);
}
