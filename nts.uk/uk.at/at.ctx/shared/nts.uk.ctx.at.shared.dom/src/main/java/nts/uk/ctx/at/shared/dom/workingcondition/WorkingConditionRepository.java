/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workingcondition;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * The Interface WorkingConditionRepository.
 */
public interface WorkingConditionRepository {

	/**
	 * Gets the by sid.
	 *
	 * @param sId the s id
	 * @return the by sid
	 */
	Optional<WorkingCondition> getBySid(String sId);

	/**
	 * Gets the all woking condition.
	 *
	 * @param companyId the company id
	 * @param sId the s id
	 * @return the all woking condition
	 */
	Optional<WorkingCondition> getBySid(String companyId, String sId);

	/**
	 * Gets the by history id.
	 *
	 * @param historyId the history id
	 * @return the by history id
	 */
	Optional<WorkingCondition> getByHistoryId(String historyId);

	/**
	 * Gets the by sid and standard date.
	 *
	 * @param employeeId the employee id
	 * @return the by sid and standard date
	 */
	Optional<WorkingCondition> getBySidAndStandardDate(String employeeId, GeneralDate baseDate);

	/**
	 * Adds the.
	 *
	 * @param workingCondition the working condition
	 */
	void add(WorkingCondition workingCondition);

	/**
	 * Update.
	 *
	 * @param workingCondition the working condition
	 */
	void update(WorkingCondition workingCondition);
	
	/**
	 * Save.
	 *
	 * @param workingCondition the working condition
	 */
	void save(WorkingCondition workingCondition);

	/**
	 * Delete.
	 *
	 * @param historyId the history id
	 */
	void  delete(String employeeId);

}
