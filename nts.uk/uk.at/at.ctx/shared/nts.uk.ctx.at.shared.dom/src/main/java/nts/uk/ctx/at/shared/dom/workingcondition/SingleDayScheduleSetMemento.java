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
 * The Interface SingleDayScheduleSetMemento.
 */
public interface SingleDayScheduleSetMemento {

	/**
	 * Sets the work type code.
	 *
	 * @param workTypeCode the new work type code
	 */
	void setWorkTypeCode(Optional<WorkTypeCode> workTypeCode);

	/**
	 * Sets the working hours.
	 *
	 * @param workingHours the new working hours
	 */
	void setWorkingHours(List<TimeZone> workingHours);

	/**
	 * Sets the work time code.
	 *
	 * @param workTimeCode the new work time code
	 */
	void setWorkTimeCode(Optional<WorkTimeCode> workTimeCode);

}
