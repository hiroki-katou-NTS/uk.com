/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.adapter.executionlog;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.ShortWorkTimeDto;

/**
 * The Interface ScShortWorkTimeAdapter.
 */
public interface ScShortWorkTimeAdapter {

	/**
	 * Find short work time.
	 *
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the optional
	 */
	Optional<ShortWorkTimeDto> findShortWorkTime(String employeeId, GeneralDate baseDate);
}
