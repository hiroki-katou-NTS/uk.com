/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.salarydetail.outputsetting;

import nts.uk.ctx.pr.report.dom.company.CompanyCode;

/**
 * The Interface SalaryOutputSettingRepository.
 */
public interface SalaryOutputSettingRepository {
	
	/**
	 * Save.
	 *
	 * @param setting the setting
	 */
	void save(SalaryOutputSetting setting);
	
	/**
	 * Removes the.
	 *
	 * @param setting the setting
	 */
	void remove(SalaryOutputSetting setting);
	
	/**
	 * Find.
	 *
	 * @param code the code
	 * @param companyCode the company code
	 * @return the salary output setting
	 */
	SalaryOutputSetting findByCode(SalaryOutputSettingCode code, CompanyCode companyCode);
}
