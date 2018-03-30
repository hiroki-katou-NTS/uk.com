/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal.employment;

import java.util.Optional;

/**
 * The Interface EmpFlexMonthActCalSetRepository.
 */
public interface EmpFlexMonthActCalSetRepository {

	/**
	 * Find by cid and empl code.
	 *
	 * @param cid the cid
	 * @param empCode the empl code
	 * @return the optional
	 */
	Optional<EmpFlexMonthActCalSet> find(String cid, String empCode);

	/**
	 * Adds the.
	 *
	 * @param EmplCalMonthlyFlex the empl cal monthly flex
	 */
	void add(EmpFlexMonthActCalSet EmplCalMonthlyFlex);

	/**
	 * Update.
	 *
	 * @param EmplCalMonthlyFlex the empl cal monthly flex
	 */
	void update(EmpFlexMonthActCalSet EmplCalMonthlyFlex);

	/**
	 * Delete.
	 *
	 * @param EmplCalMonthlyFlex the empl cal monthly flex
	 */
	void remove(String cid, String empCode);
}
