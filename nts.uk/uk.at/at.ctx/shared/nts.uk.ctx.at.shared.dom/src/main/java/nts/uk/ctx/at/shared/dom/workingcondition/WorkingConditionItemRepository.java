/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workingcondition;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * The Interface WorkingConditionItemRepository.
 */
public interface WorkingConditionItemRepository {

	/**
	 * Find working condition item by pers work cat.
	 *
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the optional
	 */
	Optional<WorkingConditionItem> findWorkingConditionItemByPersWorkCat(String employeeId, GeneralDate baseDate);

	/**
	 * Adds the.
	 *
	 * @param item the item
	 */
	void add(WorkingConditionItem item);

	/**
	 * Update.
	 *
	 * @param item the item
	 */
	void update(WorkingConditionItem item);

	/**
	 * Delete.
	 *
	 * @param historyId the history id
	 */
	void remove(String historyId);

}
