/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.screen.app.report.accumulatedpayment.query;

import java.util.List;

import lombok.Data;

/**
 * Instantiates a new acc payment report query.
 */
@Data
public class AccPaymentReportQuery {

	/** The target year. */
	private int targetYear;

	/** The emp id list. */
	private List<String> empIdList;
	
	/** The is lower limit. */
	private boolean isLowerLimit;
	
	/** The is upper limit. */
	private boolean isUpperLimit;

	/** The lower limit value. */
	private Long lowerLimitValue;

	/** The upper limit value. */
	private Long upperLimitValue;
}
