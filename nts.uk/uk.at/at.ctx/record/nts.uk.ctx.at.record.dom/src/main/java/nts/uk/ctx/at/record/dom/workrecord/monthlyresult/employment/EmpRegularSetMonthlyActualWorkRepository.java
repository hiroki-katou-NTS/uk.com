/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employment;

import java.util.Optional;

/**
 * The Interface EmploymentRegularSetMonthlyActualWorkRepository.
 */
public interface EmpRegularSetMonthlyActualWorkRepository {

	/**
	 * Find by cid and empl code.
	 *
	 * @param cid the cid
	 * @param emplCode the empl code
	 * @return the optional
	 */
	Optional<EmpRegularSetMonthlyActualWork> findByCidAndEmplCode(String cid, String emplCode);

	/**
	 * Adds the.
	 *
	 * @param EmplRegSetMonthlyActualWork the empl reg set monthly actual work
	 */
	void add(EmpRegularSetMonthlyActualWork EmplRegSetMonthlyActualWork);

	/**
	 * Update.
	 *
	 * @param EmplRegSetMonthlyActualWork the empl reg set monthly actual work
	 */
	void update(EmpRegularSetMonthlyActualWork EmplRegSetMonthlyActualWork);

	/**
	 * Delete.
	 *
	 * @param EmplRegSetMonthlyActualWork the empl reg set monthly actual work
	 */
	void delete(EmpRegularSetMonthlyActualWork EmplRegSetMonthlyActualWork);

}
