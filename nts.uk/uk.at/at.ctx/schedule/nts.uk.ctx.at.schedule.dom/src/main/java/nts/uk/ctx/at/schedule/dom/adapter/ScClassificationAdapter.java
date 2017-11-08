/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.adapter;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.ClassificationDto;

/**
 * The Class ScClassificationAdapter.
 */
public interface ScClassificationAdapter {
	
	/**
	 * Find by date.
	 *
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the optional
	 */
	public Optional<ClassificationDto> findByDate(String employeeId, GeneralDate baseDate);

}
