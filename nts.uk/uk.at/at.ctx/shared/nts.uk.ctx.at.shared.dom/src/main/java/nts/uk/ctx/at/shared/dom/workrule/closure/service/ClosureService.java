/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrule.closure.service;

import java.util.List;

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
	
	//全締めの当月と期間を取得する
	public List<ClosureInfor> getClosureInfo();
}
