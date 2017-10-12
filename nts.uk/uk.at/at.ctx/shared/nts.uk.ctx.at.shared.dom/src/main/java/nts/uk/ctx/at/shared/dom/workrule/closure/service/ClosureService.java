/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrule.closure.service;

import nts.arc.time.YearMonth;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Interface ClosureService.
 */
public interface ClosureService {

	/**
	 * Gets the closure period.
	 *
	 * @param closureId the closure id
	 * @param processingYm the processing ym
	 * @return the closure period
	 */
	// 当月の期間を算出する
	public DatePeriod getClosurePeriod(int closureId, YearMonth processingYm);
}
