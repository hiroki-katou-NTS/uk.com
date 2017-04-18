/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.salarydetail.printsetting.service.internal;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.uk.ctx.pr.report.dom.salarydetail.printsetting.SalaryPrintSetting;
import nts.uk.ctx.pr.report.dom.salarydetail.printsetting.service.SalaryPrintSettingService;

/**
 * The Class SalaryPrintSettingServiceImpl.
 */
@Stateless
public class SalaryPrintSettingServiceImpl implements SalaryPrintSettingService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.service.
	 * SalaryPrintSettingService#validateRequiredItem(nts.uk.ctx.pr.report.dom.
	 * salarydetail.printsetting.SalaryPrintSetting)
	 */
	@Override
	public void validateRequiredItem(SalaryPrintSetting setting) {
		if (setting.getHrchyIndex1() == null || setting.getHrchyIndex2() == null || setting.getHrchyIndex3() == null
				|| setting.getHrchyIndex4() == null || setting.getHrchyIndex5() == null
				|| setting.getHrchyIndex6() == null || setting.getHrchyIndex7() == null
				|| setting.getHrchyIndex8() == null || setting.getHrchyIndex9() == null
				|| setting.getMonthTotalSet() == null || setting.getShowPayment() == null
				|| setting.getTotalSet() == null || setting.getSumDepHrchyIndexSet() == null
				|| setting.getSumEachDeprtSet() == null || setting.getSumMonthDepHrchySet() == null
				|| setting.getSumMonthDeprtSet() == null || setting.getSumMonthPersonSet() == null
				|| setting.getSumPersonSet() == null) {
			throw new BusinessException("ER001");
		}
	}

}
