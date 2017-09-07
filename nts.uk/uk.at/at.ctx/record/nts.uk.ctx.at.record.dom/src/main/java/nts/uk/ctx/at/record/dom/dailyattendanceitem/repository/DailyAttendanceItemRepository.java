package nts.uk.ctx.at.record.dom.dailyattendanceitem.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.record.dom.dailyattendanceitem.DailyAttendanceItem;

public interface DailyAttendanceItemRepository {
	
	List<DailyAttendanceItem> getListTobeUsed(String companyId, int userCanUpdateAtr);
	List<DailyAttendanceItem> getList(String companyId);
	Optional<DailyAttendanceItem> getDailyAttendanceItem(String companyId, int attendanceItemId);
}
