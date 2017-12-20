/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.adapter.executionlog;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.EmploymentStatusDto;

/**
 * The Interface ScEmploymentStatusAdapter.
 */
public interface ScEmploymentStatusAdapter {
	
	/**
	 * Gets the status employment.
	 *
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the status employment
	 */
	public EmploymentStatusDto getStatusEmployment(String employeeId, GeneralDate baseDate);

}
