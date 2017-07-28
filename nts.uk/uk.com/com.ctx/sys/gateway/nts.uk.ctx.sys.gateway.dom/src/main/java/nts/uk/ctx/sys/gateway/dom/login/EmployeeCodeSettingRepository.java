/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.login;

/**
 * The Interface EmployeeCodeSettingRepository.
 */
public interface EmployeeCodeSettingRepository {

	/**
	 * Gets the by company id.
	 *
	 * @param companyId the company id
	 * @return the by company id
	 */
	EmployeeCodeSetting getbyCompanyId(String companyId);
}
