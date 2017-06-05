/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.payment.contact;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class MonthEmComment.
 */
@Getter
@Setter
public class EmpComment {

	/** The emp cd. */
	private String empCd;
	
	private String employeeName;

	/** The comment. */
	private ReportComment initialComment;

	/** The comment month cp. */
	private ReportComment monthlyComment;

}
