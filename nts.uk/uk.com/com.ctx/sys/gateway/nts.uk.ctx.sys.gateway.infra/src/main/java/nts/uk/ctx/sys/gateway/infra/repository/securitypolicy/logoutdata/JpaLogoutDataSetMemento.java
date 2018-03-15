package nts.uk.ctx.sys.gateway.infra.repository.securitypolicy.logoutdata;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.gateway.dom.login.ContractCode;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.logoutdata.LogType;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.logoutdata.LoginMethod;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.logoutdata.LogoutDataSetMemento;
import nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.logoutdata.SgwmtLogoutData;

public class JpaLogoutDataSetMemento implements LogoutDataSetMemento {

	public JpaLogoutDataSetMemento(SgwmtLogoutData entity) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setUserId(String userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLogoutDateTime(GeneralDate logoutDateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLogType(LogType logType) {
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
