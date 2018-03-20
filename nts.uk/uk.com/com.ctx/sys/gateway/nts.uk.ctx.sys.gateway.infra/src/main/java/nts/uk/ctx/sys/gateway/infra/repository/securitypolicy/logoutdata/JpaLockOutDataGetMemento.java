package nts.uk.ctx.sys.gateway.infra.repository.securitypolicy.logoutdata;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.dom.login.ContractCode;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockOutDataGetMemento;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockType;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LoginMethod;
import nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.logoutdata.SgwmtLogoutData;

public class JpaLockOutDataGetMemento implements LockOutDataGetMemento {

	public JpaLockOutDataGetMemento(SgwmtLogoutData sgwmtLogoutData) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneralDateTime getLockOutDateTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LockType getLogType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContractCode getContractCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoginMethod getLoginMethod() {
		// TODO Auto-generated method stub
		return null;
	}

}
