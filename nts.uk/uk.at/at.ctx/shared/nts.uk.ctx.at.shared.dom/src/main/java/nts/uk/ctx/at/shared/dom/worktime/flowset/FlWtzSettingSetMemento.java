/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Interface FlowWorkTimezoneSettingSetMemento.
 */
public interface FlWtzSettingSetMemento {

	/**
	 * Sets the work time rounding.
	 *
	 * @param trSet the new work time rounding
	 */
	void setWorkTimeRounding(TimeRoundingSetting trSet);

	/**
	 * Sets the lst OT timezone.
	 *
	 * @param lstTzone the new lst OT timezone
	 */
	void setLstOTTimezone(List<FlowOTTimezone> lstTzone);
}
