package nts.uk.ctx.at.record.dom.dailyattendanceitem.repository;

import java.util.List;

import nts.uk.ctx.at.record.dom.dailyattendanceitem.DailyAttendanceItemAuthority;

public interface DailyAttdItemAuthRepository {
	
	List<DailyAttendanceItemAuthority> getListDailyAttendanceItemAuthority(int authorityId, String companyId);
	
	void updateListDailyAttendanceItemAuthority (List<DailyAttendanceItemAuthority> lstDailyAttendanceItemAuthority);
	
}
