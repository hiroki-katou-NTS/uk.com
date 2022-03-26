/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Interface WorkTimezoneShortTimeWorkSetSetMemento.
 */
public interface WorkTimezoneShortTimeWorkSetSetMemento {

	/**
	 * Sets the nurs timezone work use.
	 *
	 * @param val the new nurs timezone work use
	 */
	 void setNursTimezoneWorkUse(boolean val);

	/**
	 * Sets the child care work use.
	 *
	 * @param val the new child care work use
	 */
	 void setChildCareWorkUse(boolean val);

	/**
	 * Sets the rounding setting.
	 *
	 * @param val the new rounding setting
	 */
	void setRoudingSet(TimeRoundingSetting val);
}
