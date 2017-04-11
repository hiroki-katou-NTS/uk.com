/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.salarydetail;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.report.dom.salarydetail.SalaryPrintSetting;
import nts.uk.ctx.pr.report.dom.salarydetail.SalaryPrintSettingRepository;

/**
 * The Class JpaSalaryPrintSettingRepository.
 */
@Stateless
public class JpaSalaryPrintSettingRepository implements SalaryPrintSettingRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.SalaryPrintSettingRepository#find(java.lang.String)
	 */
	@Override
	public SalaryPrintSetting find(String companyCode) {
		// TODO wait for entity
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.SalaryPrintSettingRepository#save(nts.uk.ctx.pr.report.dom.salarydetail.SalaryPrintSetting)
	 */
	@Override
	public void save(SalaryPrintSetting salaryPrintSetting) {
		// TODO wait for entity

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.SalaryPrintSettingRepository#remove(nts.uk.ctx.pr.report.dom.salarydetail.SalaryPrintSetting)
	 */
	@Override
	public void remove(SalaryPrintSetting salaryPrintSetting) {
		// TODO wait for entity

	}

}
