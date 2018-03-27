package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository;

import java.util.List;

import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItemAuthority;

public interface DailyAttdItemAuthRepository {
	
	List<DailyAttendanceItemAuthority> getListDailyAttendanceItemAuthority(String companyId);
	
	
}
