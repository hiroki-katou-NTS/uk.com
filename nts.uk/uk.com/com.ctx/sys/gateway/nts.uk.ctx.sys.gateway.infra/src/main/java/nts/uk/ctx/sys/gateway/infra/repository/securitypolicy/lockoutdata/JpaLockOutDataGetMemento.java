package nts.uk.ctx.sys.gateway.infra.repository.securitypolicy.lockoutdata;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.dom.login.ContractCode;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockOutDataGetMemento;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockType;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LoginMethod;
import nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.lockoutdata.SgwmtLockoutData;

public class JpaLockOutDataGetMemento implements LockOutDataGetMemento {

	public JpaLockOutDataGetMemento(SgwmtLockoutData sgwmtLogoutData) {
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
