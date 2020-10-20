package nts.uk.ctx.sys.gateway.dom.tenantlogin;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;

@Stateless
public class FindTenant {
	
	public static Optional<TenantAuthentication> byTenantCode(Require require, String tenantCode){
		return require.getTenantAuthentication(tenantCode);
	}
	
	public static Optional<TenantAuthentication> byTenantCode(Require require, String tenantCode, GeneralDate date){
		return require.getTenantAuthentication(tenantCode, date);
	}

	
	public static interface Require{
		Optional<TenantAuthentication> getTenantAuthentication(String tenantCode);
		
		Optional<TenantAuthentication> getTenantAuthentication(String tenantCode, GeneralDate date);
	}
}
