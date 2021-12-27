/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Interface WorkTimezoneShortTimeWorkSetGetMemento.
 */
public interface WorkTimezoneShortTimeWorkSetGetMemento {

	/**
	 * Gets the nurs timezone work use.
	 *
	 * @return the nurs timezone work use
	 */
	 boolean getNursTimezoneWorkUse();

	/**
	 * Gets the child care work use.
	 *
	 * @return the child care work use
	 */
	 boolean getChildCareWorkUse();

	/**
	 * Gets the rouding setting.
	 *
	 * @return the rouding setting
	 */
	 TimeRoundingSetting getRoudingSet();
}
