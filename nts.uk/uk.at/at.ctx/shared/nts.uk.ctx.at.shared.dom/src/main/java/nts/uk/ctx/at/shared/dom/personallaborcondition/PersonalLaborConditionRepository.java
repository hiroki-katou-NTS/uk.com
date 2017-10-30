/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.personallaborcondition;

import java.util.Optional;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Interface PersonalLaborConditionRepository.
 */
public interface PersonalLaborConditionRepository {
	
	/**
	 * Find by id.
	 *
	 * @param employeeId the employee id
	 * @param period the period
	 * @return the optional
	 */
	public Optional<PersonalLaborCondition> findById(String employeeId, DatePeriod period);

}
