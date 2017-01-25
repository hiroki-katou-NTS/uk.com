/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.salarydetail.outputsetting;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.pr.report.dom.salarydetail.SalaryCategory;

/**
 * The Class SalaryCategorySetting.
 */
@Getter
public class SalaryCategorySetting {
	
	/** The category. */
	private SalaryCategory category;
	
	/** The items. */
	private List<SalaryOutputItem> items;
}
