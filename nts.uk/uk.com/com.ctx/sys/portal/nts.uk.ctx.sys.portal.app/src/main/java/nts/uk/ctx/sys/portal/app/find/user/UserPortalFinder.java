package nts.uk.ctx.sys.portal.app.find.user;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.adapter.person.PersonInfoAdapter;
import nts.uk.ctx.sys.portal.dom.adapter.role.LoginResponsibleDto;
import nts.uk.ctx.sys.portal.dom.adapter.role.RoleGrantAdapter;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class UserPortalFinder {
	
	@Inject
	private PersonInfoAdapter personInfoAdapter;
	
	@Inject
	private RoleGrantAdapter roleGrantAdapter;
	
	public String userName() {
		LoginUserContext userCtx = AppContexts.user();
		return personInfoAdapter.getBusinessName(userCtx.employeeId()); 
	}
	
	public boolean showManual() {
		LoginUserContext userCtx = AppContexts.user();
		if (userCtx.roles().forSystemAdmin() != null || userCtx.roles().forCompanyAdmin() != null) 
			return true;
		
		LoginResponsibleDto responsible = roleGrantAdapter.getLoginResponsible();
		if (responsible.isWork() || responsible.isSalary() || responsible.isPersonalInfo())
			return true;
		
		return false;
	}
}
