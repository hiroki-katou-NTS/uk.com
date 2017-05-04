/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.payment.contact;

import lombok.Getter;

/**
 * The Class MonthEmComment.
 */
@Getter
public class EmpComment {

	/** The emp cd. */
	private String empCd;

	/** The comment. */
	private ReportComment initialComment;

	/** The comment month cp. */
	private ReportComment monthlyComment;

}
