/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.pub.schedule.basicschedule;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * The Interface ScBasicSchedulePub.
 */
public interface ScBasicSchedulePub {

	/**
	 * Find by id.
	 * Request List #4
	 * 
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the optional
	 */
	public Optional<ScBasicScheduleExport> findById(String employeeId, GeneralDate baseDate);
}
