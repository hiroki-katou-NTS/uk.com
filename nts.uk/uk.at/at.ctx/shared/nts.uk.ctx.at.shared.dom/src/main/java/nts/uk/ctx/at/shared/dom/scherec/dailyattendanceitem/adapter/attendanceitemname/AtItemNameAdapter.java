package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname;

import java.util.List;

import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItem;

public interface AtItemNameAdapter {
	List<AttItemNameImport> getNameOfAttendanceItem(List<Integer> attendanceItemIds, TypeOfItemImport type);

	List<AttItemNameImport> getNameOfDailyAttendanceItem(List<DailyAttendanceItem> attendanceItems);

	List<AttItemNameImport> getNameOfMonthlyAttendanceItem(List<MonthlyAttendanceItem> attendanceItems);
}
