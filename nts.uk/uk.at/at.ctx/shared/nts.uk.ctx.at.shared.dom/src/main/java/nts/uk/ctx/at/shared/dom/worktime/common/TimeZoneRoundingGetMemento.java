/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Interface TimeZoneRoundingGetMemento.
 */
public interface TimeZoneRoundingGetMemento {

	/**
 	 * Gets the rounding.
 	 *
 	 * @return the rounding
 	 */
 	TimeRoundingSetting getRounding();

	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	 TimeWithDayAttr getStart();

	/**
	 * Gets the end.
	 *
	 * @return the end
	 */
	 TimeWithDayAttr getEnd();
}
