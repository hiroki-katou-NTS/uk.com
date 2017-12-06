/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset;

import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Interface TimeSheetGetMemento.
 */
public interface TimeSheetGetMemento {
	
	/**
	 * Gets the start time.
	 *
	 * @return the start time
	 */
	TimeWithDayAttr getStartTime();
	
	
	/**
	 * Gets the end time.
	 *
	 * @return the end time
	 */
	TimeWithDayAttr getEndTime();

}
