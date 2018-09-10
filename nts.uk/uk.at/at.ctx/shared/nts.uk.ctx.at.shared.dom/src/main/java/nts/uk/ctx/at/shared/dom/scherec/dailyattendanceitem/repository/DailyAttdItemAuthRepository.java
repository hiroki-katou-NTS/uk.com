package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItemAuthority;

public interface DailyAttdItemAuthRepository {
	
	
	Optional<DailyAttendanceItemAuthority> getDailyAttdItem(String companyID,String authorityDailyId);
	
	void updateDailyAttdItemAuth(DailyAttendanceItemAuthority dailyAttendanceItemAuthority);
	
	void addDailyAttdItemAuth(DailyAttendanceItemAuthority dailyAttendanceItemAuthority);

	Optional<DailyAttendanceItemAuthority> getDailyAttdItemByUse(String companyId,
			String roleId,List<Integer> attendanceItemIds,int toUse);
	
	Optional<DailyAttendanceItemAuthority> getAllDailyAttdItemByUse(String companyId,
			String roleId, int toUse);
	
	
}
