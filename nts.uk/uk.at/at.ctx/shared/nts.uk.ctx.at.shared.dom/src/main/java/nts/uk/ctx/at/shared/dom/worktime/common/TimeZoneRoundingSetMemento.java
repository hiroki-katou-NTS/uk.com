/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Interface TimeZoneRoundingSetMemento.
 */
public interface TimeZoneRoundingSetMemento {

	/**
	 * Sets the rounding.
	 *
	 * @param rdSet the new rounding
	 */
 	void setRounding(TimeRoundingSetting rdSet);

	/**
	 * Sets the start.
	 *
	 * @param start the new start
	 */
	 void setStart(TimeWithDayAttr start);

	/**
	 * Sets the end.
	 *
	 * @param end the new end
	 */
	 void setEnd(TimeWithDayAttr end);
}
