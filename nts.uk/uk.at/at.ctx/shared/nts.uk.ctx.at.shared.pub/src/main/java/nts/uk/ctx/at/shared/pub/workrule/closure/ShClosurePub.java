/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.pub.workrule.closure;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Interface ShWorkTimeSettingPub.
 */
public interface ShClosurePub {

	/**
	 * Find.
	 *
	 * @param cId the c id
	 * @param closureId the closure id
	 * @return the optional
	 */
	// 処理年月と締め期間を取得する
	// RequestList 336.
	Optional<PresentClosingPeriodExport> find(String cId, int closureId);
	
	Optional<PresentClosingPeriodExport> find(String cId, int closureId, GeneralDate date);
	
	Map<Integer, DatePeriod> findAllPeriod(String cId, List<Integer> closureId, GeneralDate date);
	
}
