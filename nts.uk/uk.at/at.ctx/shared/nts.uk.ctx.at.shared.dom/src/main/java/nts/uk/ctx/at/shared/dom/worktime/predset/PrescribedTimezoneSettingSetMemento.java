/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset;

import java.util.List;

import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Interface PrescribedTimezoneSettingSetMemento.
 */
public interface PrescribedTimezoneSettingSetMemento {

	/**
	 * Sets the morning end time.
	 *
	 * @param morningEndTime the new morning end time
	 */
	void setMorningEndTime(TimeWithDayAttr morningEndTime);
	
	
	/**
	 * Sets the afternoon start time.
	 *
	 * @param afternoonStartTime the new afternoon start time
	 */
	void setAfternoonStartTime(TimeWithDayAttr afternoonStartTime);
	
	
	/**
	 * Sets the lst timezone.
	 *
	 * @param lstTimezone the new lst timezone
	 */
	void setLstTimezone(List<TimezoneUse> lstTimezone);
}
