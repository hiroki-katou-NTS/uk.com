package nts.uk.ctx.sys.portal.app.find.user;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.adapter.person.PersonInfoAdapter;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class UserPortalFinder {
	
	@Inject
	private PersonInfoAdapter personInfoAdapter;
	
	public String userName() {
		LoginUserContext userCtx = AppContexts.user();
		return personInfoAdapter.getBusinessName(userCtx.employeeId()); 
	}
}
