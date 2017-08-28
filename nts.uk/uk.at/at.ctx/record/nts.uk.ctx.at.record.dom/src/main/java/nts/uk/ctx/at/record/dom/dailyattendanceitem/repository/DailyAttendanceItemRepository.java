package nts.uk.ctx.at.record.dom.dailyattendanceitem.repository;

import java.util.List;

import nts.uk.ctx.at.record.dom.dailyattendanceitem.DailyAttendanceItem;

public interface DailyAttendanceItemRepository {
	
	List<DailyAttendanceItem> getListTobeUsed(String companyId, int userCanUpdateAtr);
}
