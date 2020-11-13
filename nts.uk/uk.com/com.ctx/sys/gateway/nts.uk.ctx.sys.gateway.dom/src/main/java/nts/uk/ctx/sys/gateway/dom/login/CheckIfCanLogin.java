package nts.uk.ctx.sys.gateway.dom.login;

import lombok.val;
import nts.uk.ctx.sys.gateway.dom.loginold.dto.CompanyInforImport;
import nts.uk.ctx.sys.gateway.dom.outage.CheckSystemAvailability;

/**
 * ログインできるかチェックする
 */
public class CheckIfCanLogin {

	public static void check(Require require, String tenantCode, String companyId, String employeeId) {
		
		val company = require.getCompanyInforImport(companyId);
		if (company.isAbolished()) {
			
		}
		
		val status = CheckSystemAvailability.isAvailable(require, tenantCode, companyId);
		if (!status.isAvailable()) {
			
		}
	}
	
	
	public static interface Require extends CheckSystemAvailability.Require {
		CompanyInforImport getCompanyInforImport(String companyId);
	}
}
