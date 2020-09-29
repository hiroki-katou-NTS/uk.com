/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Interface StampReflectTimezoneSetMemento.
 */
public interface StampReflectTimezoneSetMemento {

	/**
	 * Gets the work no.
	 *
	 * @param workNo the work no
	 * @return the work no
	 */
	void setWorkNo(WorkNo workNo);

	/**
	 * Gets the classification.
	 *
	 * @param classification the classification
	 * @return the classification
	 */
	void setClassification(GoLeavingWorkAtr classification);

	/**
	 * Gets the end time.
	 *
	 * @param endTime the end time
	 * @return the end time
	 */
	void setEndTime(TimeWithDayAttr endTime);

	/**
	 * Gets the start time.
	 *
	 * @param startTime the start time
	 * @return the start time
	 */
	void setStartTime(TimeWithDayAttr startTime);
	
}
