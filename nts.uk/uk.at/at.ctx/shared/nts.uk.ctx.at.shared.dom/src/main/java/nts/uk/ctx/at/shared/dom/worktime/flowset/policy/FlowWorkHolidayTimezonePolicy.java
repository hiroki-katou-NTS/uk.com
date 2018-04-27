/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset.policy;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkHolidayTimeZone;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface FlowWorkHolidayTimezonePolicy.
 */
public interface FlowWorkHolidayTimezonePolicy {

	/**
	 * Validate.
	 *
	 * @param be the be
	 * @param predTime the pred time
	 * @param flowHoliday the flow holiday
	 */
	void validate(BundledBusinessException be, PredetemineTimeSetting predTime, FlowWorkHolidayTimeZone flowHoliday);
}
