package nts.uk.ctx.sys.gateway.dom.login;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.uk.ctx.sys.gateway.dom.outage.CheckSystemAvailability;
import nts.uk.ctx.sys.shared.dom.company.CompanyInforImport;

/**
 * ログインできるかチェックする
 */
public class CheckIfCanLogin {

	public static void check(Require require, IdentifiedEmployeeInfo identified) {

		String companyId = identified.getCompanyId();
		
		// 会社の廃止
		val company = require.getCompanyInforImport(companyId);
		if (company.isAbolished()) {
			throw new BusinessException("Msg_281");
		}
		
		String tenantCode = identified.getTenantCode();
		String userId = identified.getUserId();
		
		// システムが利用できるかチェックする
		val status = CheckSystemAvailability.isAvailable(require, tenantCode, companyId, userId);
		if (!status.isAvailable()) {
			throw new BusinessException("Msg_285");
		}
		
		// 社員がログインできるかチェックする
		CheckEmployeeAvailability.check(require, identified);

	}
	
	public static interface Require extends
			CheckSystemAvailability.Require,
			CheckEmployeeAvailability.Require{
		
		CompanyInforImport getCompanyInforImport(String companyId);
		
	}
}
