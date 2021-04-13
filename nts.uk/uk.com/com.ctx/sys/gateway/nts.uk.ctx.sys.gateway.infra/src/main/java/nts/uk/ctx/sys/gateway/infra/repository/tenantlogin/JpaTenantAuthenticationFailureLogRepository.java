package nts.uk.ctx.sys.gateway.infra.repository.tenantlogin;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthenticateFailureLog;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthenticateFailureLogRepository;
import nts.uk.ctx.sys.gateway.infra.entity.tenantlogin.SgwdtFailLogTenantAuth;
import nts.uk.ctx.sys.gateway.infra.entity.tenantlogin.SgwdtFailLogTenantAuthPK;

@Stateless
public class JpaTenantAuthenticationFailureLogRepository extends JpaRepository implements TenantAuthenticateFailureLogRepository{
	
	private SgwdtFailLogTenantAuth toEntity(TenantAuthenticateFailureLog domain) {
		return new SgwdtFailLogTenantAuth(
				new SgwdtFailLogTenantAuthPK(
					domain.getFailureTimestamps(),
					domain.getLoginClient().getIpAddress().toString(), 
					domain.getLoginClient().getUserAgent(),
					domain.getTriedTenantCode(), 
					domain.getTriedPassword()));
	}

	@Override
	public void insert(TenantAuthenticateFailureLog domain) {
		this.commandProxy().insert(toEntity(domain));
	}
}
