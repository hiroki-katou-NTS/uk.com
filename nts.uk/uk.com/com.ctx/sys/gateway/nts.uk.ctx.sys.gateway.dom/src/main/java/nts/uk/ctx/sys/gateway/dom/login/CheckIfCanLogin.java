package nts.uk.ctx.sys.gateway.dom.login;

import lombok.val;
import nts.uk.ctx.sys.gateway.dom.outage.CheckSystemAvailability;
import nts.uk.ctx.sys.shared.dom.company.CompanyInforImport;

/**
 * ログインできるかチェックする
 */
public class CheckIfCanLogin {

	public static Result check(Require require, String tenantCode, String companyId, String employeeId) {
		
		val company = require.getCompanyInforImport(companyId);
		if (company.isAbolished()) {
			
		}
		
		val status = CheckSystemAvailability.isAvailable(require, tenantCode, companyId);
		if (!status.isAvailable()) {
			
		}
	}
	
	public static class Result {
		
	}
	
	public static interface Require extends CheckSystemAvailability.Require {
		CompanyInforImport getCompanyInforImport(String companyId);
	}
}
