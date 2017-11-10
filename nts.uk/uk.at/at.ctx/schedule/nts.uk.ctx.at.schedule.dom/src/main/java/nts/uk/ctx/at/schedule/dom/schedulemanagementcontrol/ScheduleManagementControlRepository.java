/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.schedulemanagementcontrol;

import java.util.Optional;

/**
 * The Interface ScheduleManagementControlRepository.
 */
public interface ScheduleManagementControlRepository {

	/**
	 * Find by id.
	 *
	 * @param employeeId the employee id
	 * @return the optional
	 */
	public Optional<ScheduleManagementControl> findById(String employeeId);
}
