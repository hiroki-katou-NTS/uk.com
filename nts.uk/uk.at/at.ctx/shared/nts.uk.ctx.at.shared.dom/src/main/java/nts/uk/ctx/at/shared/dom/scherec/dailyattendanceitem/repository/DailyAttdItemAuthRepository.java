package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItemAuthority;

public interface DailyAttdItemAuthRepository {
	
	
	Optional<DailyAttendanceItemAuthority> getDailyAttdItem(String companyID,String authorityDailyId);
	
	void updateDailyAttdItemAuth(DailyAttendanceItemAuthority dailyAttendanceItemAuthority);
	
	void addDailyAttdItemAuth(DailyAttendanceItemAuthority dailyAttendanceItemAuthority);
	
	/**
	 * 権限別日次項目制御
	 */
	Optional<DailyAttendanceItemAuthority> getDailyAttdItemByAttItemId(String companyID, String authorityDailyId,
			List<Integer> attendanceItemIds);
}
