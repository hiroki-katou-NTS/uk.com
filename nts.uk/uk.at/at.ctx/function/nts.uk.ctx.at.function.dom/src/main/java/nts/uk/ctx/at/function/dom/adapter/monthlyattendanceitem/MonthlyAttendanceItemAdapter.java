package nts.uk.ctx.at.function.dom.adapter.monthlyattendanceitem;

import java.util.List;

public interface MonthlyAttendanceItemAdapter {

	List<MonthlyAttendanceItemFunImport> getMonthlyAttendanceItem(String companyId, List<Integer> dailyAttendanceItemIds);
}
