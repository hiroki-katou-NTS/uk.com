/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.screen.app.report.accumulatedpayment.query;

import java.util.List;

import lombok.Data;

@Data
public class AccPaymentReportQuery {

	/** The year. */
	private int year;

	/** The emp id list. */
	private List<String> empIdList;

	/** The lower limit. */
	private Long lowerLimit;

	/** The upper limit. */
	private Long upperLimit;
}
