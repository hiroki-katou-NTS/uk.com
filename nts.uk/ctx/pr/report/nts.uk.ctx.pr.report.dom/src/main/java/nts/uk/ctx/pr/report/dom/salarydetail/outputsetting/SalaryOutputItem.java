/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.salarydetail.outputsetting;

import lombok.Getter;
import nts.uk.ctx.pr.report.dom.salarydetail.SalaryItemType;

/**
 * The Class SalaryOutputItem.
 */
@Getter
public class SalaryOutputItem {
	
	/** The linkage code. */
	private String linkageCode;
	
	/** The type. */
	private SalaryItemType type;
}
