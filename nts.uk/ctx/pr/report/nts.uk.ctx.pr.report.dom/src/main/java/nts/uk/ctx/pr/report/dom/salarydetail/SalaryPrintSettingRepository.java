/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.salarydetail;

import nts.uk.ctx.pr.report.dom.company.CompanyCode;

/**
 * The Interface SalaryPrintSettingRepository.
 */
public interface SalaryPrintSettingRepository {
	
	/**
	 * Find.
	 *
	 * @param companyCode the company code
	 * @return the salary print setting
	 */
	SalaryPrintSetting find(CompanyCode companyCode);
	
	/**
	 * Save.
	 *
	 * @param salaryPrintSetting the salary print setting
	 */
	void save(SalaryPrintSetting salaryPrintSetting);
	
	/**
	 * Removes the.
	 *
	 * @param salaryPrintSetting the salary print setting
	 */
	void remove(SalaryPrintSetting salaryPrintSetting);
}
