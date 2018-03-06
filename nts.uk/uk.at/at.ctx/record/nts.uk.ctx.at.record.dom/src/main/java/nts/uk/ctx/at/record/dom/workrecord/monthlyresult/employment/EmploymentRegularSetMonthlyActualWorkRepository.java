/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employment;

import java.util.Optional;

/**
 * The Interface EmploymentRegularSetMonthlyActualWorkRepository.
 */
public interface EmploymentRegularSetMonthlyActualWorkRepository {

	/**
	 * Find by cid and empl code.
	 *
	 * @param cid the cid
	 * @param emplCode the empl code
	 * @return the optional
	 */
	Optional<EmploymentRegularSetMonthlyActualWork> findByCidAndEmplCode(String cid, String emplCode);

	/**
	 * Adds the.
	 *
	 * @param EmplRegSetMonthlyActualWork the empl reg set monthly actual work
	 */
	void add(EmploymentRegularSetMonthlyActualWork EmplRegSetMonthlyActualWork);

	/**
	 * Update.
	 *
	 * @param EmplRegSetMonthlyActualWork the empl reg set monthly actual work
	 */
	void update(EmploymentRegularSetMonthlyActualWork EmplRegSetMonthlyActualWork);

	/**
	 * Delete.
	 *
	 * @param EmplRegSetMonthlyActualWork the empl reg set monthly actual work
	 */
	void delete(EmploymentRegularSetMonthlyActualWork EmplRegSetMonthlyActualWork);

}
