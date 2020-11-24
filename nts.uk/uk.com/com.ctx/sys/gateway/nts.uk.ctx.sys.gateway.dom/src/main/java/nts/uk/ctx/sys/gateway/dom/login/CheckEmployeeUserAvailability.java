package nts.uk.ctx.sys.gateway.dom.login;

import java.util.Optional;

import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.AccountLockPolicy;

/**
 * 社員がログインできるかチェックする
 */
public class CheckEmployeeUserAvailability {

	public static Result check(Require require, IdentifiedEmployeeInfo identified) {

		String tenantCode = identified.getUser().getContractCode().v();
		String companyId = identified.getEmployee().getCompanyId();
		String userId = identified.getUser().getUserID();
		
		
		// アカウントロック
		boolean isAccountLocked = require.getAccountLockPolicy(tenantCode)
				.map(p -> p.isLocked(require, userId))
				.orElse(false);
		if (isAccountLocked) {
			
		}
	}
	
	public static class Result {
		
	}
	
	public static interface Require extends 
			AccountLockPolicy.RequireIsLocked {

		Optional<AccountLockPolicy> getAccountLockPolicy(String tenantCode);
	}
}
