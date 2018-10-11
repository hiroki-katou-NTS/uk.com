package nts.uk.ctx.sys.auth.pubimp.role;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.auth.app.find.role.workplace.InitValueAuthManagement;

@Stateless
public class InitValueAuthManagementImpl {

	@Inject
	private InitValueAuthManagement initData;
	
	public void initValueAuth(String companyIDCopy  ){
		this.initData.initRole(companyIDCopy);
	}
	
	
}
