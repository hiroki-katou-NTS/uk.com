/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workingcondition;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * The Interface SingleDayScheduleGetMemento.
 */
public interface SingleDayScheduleGetMemento {

	/**
	 * Gets the work type code.
	 *
	 * @return the work type code
	 */
	Optional<WorkTypeCode> getWorkTypeCode();

	/**
	 * Gets the working hours.
	 *
	 * @return the working hours
	 */
	 List<TimeZone> getWorkingHours();

	/**
	 * Gets the work time code.
	 *
	 * @return the work time code
	 */
	Optional<WorkTimeCode> getWorkTimeCode();

}
