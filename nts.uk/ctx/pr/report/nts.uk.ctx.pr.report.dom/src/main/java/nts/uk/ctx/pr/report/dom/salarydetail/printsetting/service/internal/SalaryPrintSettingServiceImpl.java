/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.salarydetail.printsetting.service.internal;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.report.dom.salarydetail.printsetting.SalaryPrintSetting;
import nts.uk.ctx.pr.report.dom.salarydetail.printsetting.service.SalaryPrintSettingService;

@Stateless
public class SalaryPrintSettingServiceImpl implements SalaryPrintSettingService {

	@Override
	public void validateRequiredItem(SalaryPrintSetting salaryPrintSetting) {
		// TODO throw new BusinessException("ER001");

	}

}
