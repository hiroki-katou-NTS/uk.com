/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.salarydetail.outputsetting;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.pr.report.dom.company.CompanyCode;

/**
 * The Class SalaryOutputSetting.
 */
@Getter
public class SalaryOutputSetting {
	
	/** The code. */
	private SalaryOutputSettingCode code;
	
	/** The name. */
	private SalaryOutputSettingName name;
	
	/** The company code. */
	private CompanyCode companyCode;
	
	/** The category settings. */
	private List<SalaryCategorySetting> categorySettings;
}
