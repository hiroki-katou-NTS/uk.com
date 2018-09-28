package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname;

import java.util.List;

import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItem;

public interface AtItemNameAdapter {
	List<AttItemName> getNameOfAttendanceItem(List<Integer> attendanceItemIds, TypeOfItemImport type);

	List<AttItemName> getNameOfDailyAttendanceItem(List<DailyAttendanceItem> attendanceItems);

	List<AttItemName> getNameOfMonthlyAttendanceItem(List<MonthlyAttendanceItem> attendanceItems);
	
	List<AttItemName> getNameOfAttdItemByType(TypeOfItemImport type);
}
