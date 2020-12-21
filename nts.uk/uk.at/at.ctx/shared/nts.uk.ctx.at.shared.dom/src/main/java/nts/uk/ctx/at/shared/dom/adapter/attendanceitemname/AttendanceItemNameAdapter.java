package nts.uk.ctx.at.shared.dom.adapter.attendanceitemname;

import java.util.List;
import java.util.Map;

public interface AttendanceItemNameAdapter {
	
	Map<Integer, String> getAttendanceItemNameAsMapName(List<Integer> dailyAttendanceItemIds, int typeOfAttendanceItem);

	List<MonthlyAttendanceItemNameDto> getMonthlyAttendanceItemName(List<Integer> attendanceItemIds);
	/**
	 * 
	 * @param typeOfAttendanceItem 0 スケジュール, 1 日次 , 2 月次, 3 週次, 4 任意期間
	 * @return
	 */
	List<MonthlyAttendanceItemNameDto> getMonthlyAttendanceItem(int typeOfAttendanceItem);
}
