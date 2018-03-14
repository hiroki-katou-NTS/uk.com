/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrule.closure.service;

import java.util.Optional;

import nts.arc.time.GeneralDate;
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

	/**
	 * 指定した年月日時点の締め期間を取得する
	 * @param closureId 締めID
	 * @param ymd 年月日
	 * @return 締め期間
	 */
	// 2018.3.11 add shuichi_ishida
	public Optional<ClosurePeriod> getClosurePeriodByYmd(int closureId, GeneralDate ymd);
}
