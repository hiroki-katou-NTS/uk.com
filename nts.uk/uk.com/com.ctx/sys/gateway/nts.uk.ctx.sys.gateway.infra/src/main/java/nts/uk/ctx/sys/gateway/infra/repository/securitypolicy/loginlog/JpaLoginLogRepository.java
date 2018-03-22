package nts.uk.ctx.sys.gateway.infra.repository.securitypolicy.loginlog;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.loginlog.LoginLog;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.loginlog.LoginLogRepository;

@Stateless
public class JpaLoginLogRepository extends JpaRepository implements LoginLogRepository{
	
	@Override
	public Integer getLoginLogByConditions(String userId, GeneralDateTime startTime){
		return 0;
	}
	
	@Override
	public void add(LoginLog loginLog){
		
	}
}
