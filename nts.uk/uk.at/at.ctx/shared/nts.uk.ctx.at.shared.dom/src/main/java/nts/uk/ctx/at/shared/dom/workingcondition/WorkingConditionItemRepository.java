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
	 * Gets the by history id.
	 *
	 * @param historyId the history id
	 * @return the by history id
	 */
	Optional<WorkingConditionItem> getByHistoryId(String historyId);

	/**
	 * Find working condition item by pers work cat.
	 *
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the optional
	 */
	Optional<WorkingConditionItem> getBySidAndStandardDate(String employeeId, GeneralDate baseDate);
	
	/**
	 * Gets the by sid and hist id.
	 *
	 * @param employeeId the employee id
	 * @param historyId the history id
	 * @return the by sid and hist id
	 */
	Optional<WorkingConditionItem> getBySidAndHistId(String employeeId, String historyId);

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
	void delete(String historyId);

}
