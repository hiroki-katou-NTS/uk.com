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

	public static Result check(Require require, IdentifiedEmployeeInfo identified) {

		String tenantCode = identified.getTenantCode();
		String companyId = identified.getCompanyId();
		String userId = identified.getUserId();
		
		// 会社の廃止
		val company = require.getCompanyInforImport(companyId);
		if (company.isAbolished()) {
			
		}
		
		// システム利用停止
		val status = CheckSystemAvailability.isAvailable(require, tenantCode, companyId, userId);
		if (!status.isAvailable()) {
			
		}
		
		return null;
	}
	
	public static class Result {
		
	}
	
	public static interface Require extends
			CheckSystemAvailability.Require {
		
		CompanyInforImport getCompanyInforImport(String companyId);
		
	}
}
