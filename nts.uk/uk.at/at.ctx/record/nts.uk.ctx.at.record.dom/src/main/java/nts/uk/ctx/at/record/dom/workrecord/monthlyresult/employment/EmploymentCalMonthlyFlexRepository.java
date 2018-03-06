/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employment;

import java.util.Optional;

/**
 * The Interface EmploymentCalMonthlyFlexRepository.
 */
public interface EmploymentCalMonthlyFlexRepository {

	/**
	 * Find by cid and empl code.
	 *
	 * @param cid the cid
	 * @param emplCode the empl code
	 * @return the optional
	 */
	Optional<EmploymentCalMonthlyFlex> findByCidAndEmplCode(String cid, String emplCode);

	/**
	 * Adds the.
	 *
	 * @param EmplCalMonthlyFlex the empl cal monthly flex
	 */
	void add(EmploymentCalMonthlyFlex EmplCalMonthlyFlex);

	/**
	 * Update.
	 *
	 * @param EmplCalMonthlyFlex the empl cal monthly flex
	 */
	void update(EmploymentCalMonthlyFlex EmplCalMonthlyFlex);

	/**
	 * Delete.
	 *
	 * @param EmplCalMonthlyFlex the empl cal monthly flex
	 */
	void delete(EmploymentCalMonthlyFlex EmplCalMonthlyFlex);
}
