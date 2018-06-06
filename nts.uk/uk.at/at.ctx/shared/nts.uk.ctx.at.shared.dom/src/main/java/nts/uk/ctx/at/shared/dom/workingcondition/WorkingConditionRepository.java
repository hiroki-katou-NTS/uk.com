/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workingcondition;

import java.util.List;
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
	 * Gets the by sid.
	 *
	 * @param companyId the company id
	 * @param sId the s id
	 * @return the by sid
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
	 * @param baseDate the base date
	 * @return the by sid and standard date
	 */
	Optional<WorkingCondition> getBySidAndStandardDate(String employeeId, GeneralDate baseDate);
	
	/**
	 * Gets the by sid and standard date.
	 *
	 * @param companyId the company id
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the by sid and standard date
	 */
	Optional<WorkingCondition> getBySidAndStandardDate(String companyId, String employeeId, GeneralDate baseDate);

	/**
	 * Gets the by sids.
	 *
	 * @param sId the s id
	 * @return the by sids
	 */
	List<WorkingCondition> getBySidsAndBaseDate(List<String> sIds, GeneralDate baseDate);
	
	/**
	 * Save.
	 *
	 * @param workingCondition the working condition
	 */
	void save(WorkingCondition workingCondition);

	/**
	 * Delete.
	 *
	 * @param employeeId the employee id
	 */
	void delete(String employeeId);

}
