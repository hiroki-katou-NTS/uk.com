package nts.uk.ctx.sys.gateway.dom.login;

import java.util.Optional;

import lombok.val;
import nts.uk.ctx.sys.gateway.dom.outage.CheckSystemAvailability;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.AccountLockPolicy;
import nts.uk.ctx.sys.shared.dom.company.CompanyInforImport;
import nts.uk.ctx.sys.shared.dom.user.FindUser;

/**
 * ログインできるかチェックする
 */
public class CheckIfCanLogin {

	public static Result check(Require require, String tenantCode, String companyId, String employeeId) {
		
		// 会社の廃止
		val company = require.getCompanyInforImport(companyId);
		if (company.isAbolished()) {
			
		}
		
		val user = FindUser.byEmployeeId(require, employeeId).get();
		
		// システム利用停止
		val status = CheckSystemAvailability.isAvailable(require, tenantCode, companyId, user.getUserID());
		if (!status.isAvailable()) {
			
		}
		
		// アカウントロック
		boolean isAccountLocked = require.getAccountLockPolicy(tenantCode)
				.map(p -> p.isLocked(require, user.getUserID()))
				.orElse(false);
		if (isAccountLocked) {
			
		}
		
		return null;
	}
	
	public static class Result {
		
	}
	
	public static interface Require extends
			CheckSystemAvailability.Require,
			AccountLockPolicy.RequireIsLocked,
			FindUser.RequireByEmployeeId {
		
		CompanyInforImport getCompanyInforImport(String companyId);
		
		Optional<AccountLockPolicy> getAccountLockPolicy(String tenantCode);
	}
}
