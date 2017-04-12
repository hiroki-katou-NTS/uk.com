/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.salarydetail.find;

import javax.inject.Inject;

import nts.uk.ctx.pr.report.dom.salarydetail.SalaryPrintSetting;
import nts.uk.ctx.pr.report.dom.salarydetail.SalaryPrintSettingRepository;

/**
 * The Class SalaryPrintSettingFinder.
 */
public class SalaryPrintSettingFinder {

	/** The repository. */
	@Inject
	private SalaryPrintSettingRepository repository;

	/**
	 * Find.
	 *
	 * @param companyCode the company code
	 * @return the salary print setting dto
	 */
	public SalaryPrintSettingDto find(String companyCode) {
		SalaryPrintSetting salaryPrintSetting = repository.find(companyCode);
		// TODO convert to Dto
		return null;
	}
}
