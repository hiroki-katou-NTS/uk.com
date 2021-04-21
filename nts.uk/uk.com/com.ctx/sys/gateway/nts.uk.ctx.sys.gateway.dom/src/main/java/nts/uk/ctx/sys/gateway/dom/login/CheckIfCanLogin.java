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
		
		// 会社が廃止されていないかチェックする
		checkAboloshCompany(require, identified);
		
		// システムが利用できるかチェックする
		checkAbailableSystem(require, identified);
		
		// ログインできる社員かチェックする
		CheckEmployeeAvailability.check(require, identified);
	}
	
	/**
	 * 会社が廃止されていないかチェックする
	 * @param require
	 * @param identified
	 */
	private static void checkAboloshCompany(Require require, IdentifiedEmployeeInfo identified) {
		val company = require.getCompanyInforImport(identified.getCompanyId());
		
		if (company.isAbolished()) {
			throw new BusinessException("Msg_281");
		}
	}
	
	/**
	 * システムが利用できるかチェックする
	 * @param require
	 * @param identified
	 */
	private static void checkAbailableSystem(Require require, IdentifiedEmployeeInfo identified) {
		val status = CheckSystemAvailability.isAvailable(require, 
				identified.getTenantCode(), 
				identified.getCompanyId(), 
				identified.getUserId());
		
		if (!status.isAvailable()) {
			throw new BusinessException("Msg_285");
		}
	}
	
	public static interface Require extends
			CheckSystemAvailability.Require,
			CheckEmployeeAvailability.Require{
		CompanyInforImport getCompanyInforImport(String companyId);
	}
}
