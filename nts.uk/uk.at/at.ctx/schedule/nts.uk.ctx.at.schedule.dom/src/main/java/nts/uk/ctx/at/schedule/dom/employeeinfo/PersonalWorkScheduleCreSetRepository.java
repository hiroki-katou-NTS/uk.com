/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.employeeinfo;

import java.util.Optional;

/**
 * The Interface PersonalWorkScheduleCreSetRepository.
 */
public interface PersonalWorkScheduleCreSetRepository {
	
	/**
	 * Find by id.
	 *
	 * @param employeeId the employee id
	 * @return the optional
	 */
	public Optional<PersonalWorkScheduleCreSet> findById(String employeeId);


}
