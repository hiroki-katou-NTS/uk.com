package nts.uk.ctx.at.shared.app.find.scherec.monthlyattditem;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.MonthlyItemControlByAuthRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.MonthlyItemControlByAuthority;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class MonthlyItemControlByAuthFinder {
	
	@Inject
	private MonthlyItemControlByAuthRepository repo;
	
	public MonthlyItemControlByAuthDto getMonthlyItemControlByRoleID(String roleID) {
		String companyID = AppContexts.user().companyId();
		Optional<MonthlyItemControlByAuthority> data = repo.getMonthlyAttdItem(companyID, roleID);
		if(data.isPresent())
			return MonthlyItemControlByAuthDto.fromDomain(data.get());
		return null;
	}

}
