/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workingcondition;

import java.util.Optional;

/**
 * The Interface WorkingConditionRepository.
 */
public interface WorkingConditionRepository {

	/**
	 * Gets the working condition history.
	 *
	 * @param companyId the company id
	 * @param sId the s id
	 * @param historyId the history id
	 * @return the working condition history
	 */
	Optional<WorkingCondition> getWorkingConditionHistory(String companyId, String sId,
			String historyId);

	/**
	 * Gets the all woking condition.
	 *
	 * @param companyId the company id
	 * @param sId the s id
	 * @return the all woking condition
	 */
	Optional<WorkingCondition> getBySid(String companyId, String sId);

	/**
	 * Gets the by sid.
	 *
	 * @param sId the s id
	 * @return the by sid
	 */
	Optional<WorkingCondition> getBySid(String sId);

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
	 * Delete.
	 *
	 * @param historyId the history id
	 */
	void  remove(String employeeId);

}
