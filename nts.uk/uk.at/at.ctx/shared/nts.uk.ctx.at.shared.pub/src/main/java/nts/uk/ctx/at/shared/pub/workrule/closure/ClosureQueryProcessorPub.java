/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.pub.workrule.closure;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * The Interface ClosureQueryProcessorPub.
 */
public interface ClosureQueryProcessorPub {

	/**
	 * Find closure by reference date.
	 *
	 * @param refDate the ref date
	 * @return the list
	 */
	// request 140: 会社の締めを取得する
	public List<ClosureExport> findClosureByReferenceDate(GeneralDate refDate);

	/**
	 * Find closure by employment code.
	 *
	 * @param empCode the emp code
	 * @return the integer
	 */
	// request 142: 雇用に紐づく締めを取得する
	public Integer findClosureByEmploymentCode(String empCode);
}
