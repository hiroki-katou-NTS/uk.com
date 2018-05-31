package nts.uk.ctx.at.shared.app.find.scherec.dailyattendanceitem;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItemAuthority;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttdItemAuthRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DailyAttendanceItemAuthorityFinder {
	@Inject
	private DailyAttdItemAuthRepository repo;
	
	public DailyAttendanceItemAuthorityDto getDailyAttdItemByRoleID(String roleID) {
		String companyID = AppContexts.user().companyId();
		Optional<DailyAttendanceItemAuthority> data = repo.getDailyAttdItem(companyID, roleID);
		if(data.isPresent())
			return DailyAttendanceItemAuthorityDto.fromDomain(data.get());
		return null;
	}
}
