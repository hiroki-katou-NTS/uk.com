/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.personallaborcondition;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * The Interface PersonalLaborConditionRepository.
 */
public interface PersonalLaborConditionRepository {
	
	/**
	 * Find by id.
	 *
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the optional
	 */
	public Optional<PersonalLaborCondition> findById(String employeeId, GeneralDate baseDate);

}
