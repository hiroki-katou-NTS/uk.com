/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset;

import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class TimeSheetSetMemento.
 */
public interface TimeSheetSetMemento {
	
	/**
	 * Sets the start time.
	 *
	 * @param startTime the new start time
	 */
	void setStartTime(TimeWithDayAttr startTime);
	
	
	/**
	 * Sets the end time.
	 *
	 * @param endTime the new end time
	 */
	void setEndTime(TimeWithDayAttr endTime);

}
