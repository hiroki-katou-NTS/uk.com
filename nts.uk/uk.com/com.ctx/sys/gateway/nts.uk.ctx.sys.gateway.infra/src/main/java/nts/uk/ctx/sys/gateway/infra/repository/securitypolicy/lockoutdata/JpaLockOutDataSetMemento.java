package nts.uk.ctx.sys.gateway.infra.repository.securitypolicy.lockoutdata;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.dom.login.ContractCode;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockOutDataSetMemento;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockType;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LoginMethod;
import nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.lockoutdata.SgwmtLockoutData;

public class JpaLockOutDataSetMemento implements LockOutDataSetMemento {

	public JpaLockOutDataSetMemento(SgwmtLockoutData entity) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setUserId(String userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLogoutDateTime(GeneralDateTime logoutDateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLogType(LockType logType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setContractCode(ContractCode contractCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLoginMethod(LoginMethod loginMethod) {
		// TODO Auto-generated method stub
		
	}

}
