/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.salarydetail.printsetting.find;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.report.app.salarydetail.printsetting.find.dto.SalaryPrintSettingDto;
import nts.uk.ctx.pr.report.dom.salarydetail.printsetting.SalaryPrintSetting;
import nts.uk.ctx.pr.report.dom.salarydetail.printsetting.SalaryPrintSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SalaryPrintSettingFinder.
 */
@Stateless
public class SalaryPrintSettingFinder {

	/** The repository. */
	@Inject
	private SalaryPrintSettingRepository repository;

	/**
	 * Find.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the salary print setting dto
	 */
	public SalaryPrintSettingDto find() {
		SalaryPrintSetting domain = repository.find(AppContexts.user().companyCode());
		SalaryPrintSettingDto dto = SalaryPrintSettingDto.builder().outputDistinction(domain.getOutputDistinction())
				.showHierarchy1(domain.getShowHierarchy1()).showHierarchy2(domain.getShowHierarchy2())
				.showHierarchy3(domain.getShowHierarchy3()).showHierarchy4(domain.getShowHierarchy4())
				.showHierarchy5(domain.getShowHierarchy5()).build();
		return dto;
	}
}
