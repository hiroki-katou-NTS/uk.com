package nts.uk.ctx.sys.gateway.dom.outage;

import java.util.Optional;

import lombok.val;
import nts.uk.shr.com.context.loginuser.role.LoginUserRoles;

/**
 * システムが利用できるかチェックする
 */
public class CheckSystemAvailability {
	
	public static PlannedOutage.Status isAvailable(Require require, String tenantCode, String companyId) {

		val roles = require.getLoginUserRoles();
		
		val byTenant = require.getPlannedOutageByTenant(tenantCode)
				.map(o -> o.statusFor(roles));
		
		if (byTenant.isPresent() && byTenant.get().isOutage()) {
			return byTenant.get();
		}
		
		val byCompany = require.getPlannedOutageByCompany(companyId)
				.map(o -> o.statusFor(roles));
		
		if (byCompany.isPresent() && byCompany.get().isOutage()) {
			return byCompany.get();
		}
		
		return PlannedOutage.Status.available();
	}
	
	public static interface Require {

		Optional<PlannedOutageByTenant> getPlannedOutageByTenant(String tenantCode);
		
		Optional<PlannedOutageByCompany> getPlannedOutageByCompany(String companyId);
		
		LoginUserRoles getLoginUserRoles();
	}
}
