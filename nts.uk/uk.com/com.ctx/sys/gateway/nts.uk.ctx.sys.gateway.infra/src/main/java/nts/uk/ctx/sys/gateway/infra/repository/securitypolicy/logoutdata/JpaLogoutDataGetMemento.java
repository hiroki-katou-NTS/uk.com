package nts.uk.ctx.sys.gateway.infra.repository.securitypolicy.logoutdata;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.gateway.dom.login.ContractCode;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.logoutdata.LogType;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.logoutdata.LoginMethod;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.logoutdata.LogoutDataGetMemento;
import nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.logoutdata.SgwmtLogoutData;

public class JpaLogoutDataGetMemento implements LogoutDataGetMemento {

	public JpaLogoutDataGetMemento(SgwmtLogoutData sgwmtLogoutData) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneralDate getLogoutDateTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LogType getLogType() {
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
