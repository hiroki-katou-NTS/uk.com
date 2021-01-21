package nts.uk.ctx.sys.gateway.dom.login;

import java.util.Optional;

import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.AccountLockPolicy;

/**
 * 社員がログインできるかチェックする
 */
public class CheckEmployeeUserAvailability {

	public static Result check(Require require, IdentifiedEmployeeInfo identified) {

		String tenantCode = identified.getTenantCode();
		String companyId = identified.getCompanyId();
		String userId = identified.getUserId();
		
		
		// アカウントロック
		boolean isAccountLocked = require.getAccountLockPolicy(tenantCode)
				.map(p -> p.isLocked(require, userId))
				.orElse(false);
		if (isAccountLocked) {
			
		}
		
		return null;
	}
	
	public static class Result {
		
	}
	
	public static interface Require extends 
			AccountLockPolicy.RequireIsLocked {

		Optional<AccountLockPolicy> getAccountLockPolicy(String tenantCode);
	}
}
