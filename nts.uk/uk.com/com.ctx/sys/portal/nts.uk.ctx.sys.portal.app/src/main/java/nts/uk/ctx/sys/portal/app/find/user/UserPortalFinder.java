package nts.uk.ctx.sys.portal.app.find.user;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.adapter.employee.EmployeeAdapter;
import nts.uk.ctx.sys.portal.dom.adapter.employee.ShortEmployeeDto;
import nts.uk.ctx.sys.portal.dom.adapter.user.UserAdapter;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class UserPortalFinder {

	@Inject
	private EmployeeAdapter employeeAdapter;
	
	@Inject
	private UserAdapter userAdapter;
	
	public String userName() {
		LoginUserContext userCtx = AppContexts.user();
		List<ShortEmployeeDto> employees = employeeAdapter.getEmployeesByPId(userCtx.personId());
		Optional<ShortEmployeeDto> empOpt = employees.stream()
				.filter(e -> e.getCompanyId().equals(userCtx.companyId())).findFirst();
		if (empOpt.isPresent()) {
			return empOpt.get().getPersonName();
		}
		return userAdapter.getUserInfo(userCtx.userId()).map(u -> u.getUserName()).orElse("");
	}
}
