/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Interface FlowWorkTimezoneSettingGetMemento.
 */
public interface FlWtzSettingGetMemento {

	/**
	 * Gets the work time rounding.
	 *
	 * @return the work time rounding
	 */
	TimeRoundingSetting getWorkTimeRounding();

	/**
	 * Gets the lst OT timezone.
	 *
	 * @return the lst OT timezone
	 */
	List<FlowOTTimezone> getLstOTTimezone();
}
