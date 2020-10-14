package nts.uk.ctx.sys.gateway.dom.tenantlogin;

import java.util.Optional;

import javax.ejb.Stateless;

@Stateless
public class FindTenant {
	
	public static Optional<TenantAuthentication> byTenantCode(Require require, String tenantCode){
		return require.getTenantAuthentication(tenantCode);
	}
	
	public static interface Require{
		Optional<TenantAuthentication> getTenantAuthentication(String tenantCode);
	}


}
