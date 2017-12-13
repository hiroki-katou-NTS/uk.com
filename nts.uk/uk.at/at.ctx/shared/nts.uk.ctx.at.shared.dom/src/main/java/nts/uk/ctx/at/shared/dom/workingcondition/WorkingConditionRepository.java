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
	 * Gets the all woking condition.
	 *
	 * @param companyId the company id
	 * @param sId the s id
	 * @return the all woking condition
	 */
	Optional<WorkingCondition> getAllWokingCondition(String companyId, String sId);
}
