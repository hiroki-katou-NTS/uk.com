/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Interface StampReflectTimezoneGetMemento.
 */
public interface StampReflectTimezoneGetMemento {
	
	/**
	 * Gets the work no.
	 *
	 * @return the work no
	 */
	WorkNo getWorkNo();

	/**
	 * Gets the classification.
	 *
	 * @return the classification
	 */
	GoLeavingWorkAtr getClassification();

	/**
	 * Gets the end time.
	 *
	 * @return the end time
	 */
	TimeWithDayAttr getEndTime();

	/**
	 * Gets the start time.
	 *
	 * @return the start time
	 */
	TimeWithDayAttr getStartTime();
	
}
