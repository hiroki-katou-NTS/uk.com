package nts.uk.ctx.sys.gateway.dom.login;

import lombok.val;
import nts.uk.ctx.sys.gateway.dom.outage.CheckSystemAvailability;
import nts.uk.ctx.sys.shared.dom.company.CompanyInforImport;

/**
 * ログインできるかチェックする
 */
public class CheckIfCanLogin {

	public static void check(Require require, IdentifiedEmployeeInfo identified) {

		String tenantCode = identified.getTenantCode();
		String companyId = identified.getCompanyId();
		String userId = identified.getUserId();
		
		// 会社の廃止
		val company = require.getCompanyInforImport(companyId);
		if (company.isAbolished()) {
			
		}
		
		// システムが利用できるかチェックする
		val status = CheckSystemAvailability.isAvailable(require, tenantCode, companyId, userId);
		if (!status.isAvailable()) {
			
		}
		
		// 社員がログインできるかチェックする
		CheckEmployeeUserAvailability.check(require, identified);

	}
	
	public static interface Require extends
			CheckSystemAvailability.Require,
			CheckEmployeeUserAvailability.Require{
		
		CompanyInforImport getCompanyInforImport(String companyId);
		
	}
}
