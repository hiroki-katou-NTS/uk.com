/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset;

import java.util.List;

import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Interface PrescribedTimezoneSettingGetMemento.
 */
public interface PrescribedTimezoneSettingGetMemento {
	
	/**
	 * Gets the morning end time.
	 *
	 * @return the morning end time
	 */
	TimeWithDayAttr getMorningEndTime();
	
	
	/**
	 * Gets the afternoon start time.
	 *
	 * @return the afternoon start time
	 */
	TimeWithDayAttr getAfternoonStartTime();
	
	
	/**
	 * Gets the lst timezone.
	 *
	 * @return the lst timezone
	 */
	List<TimezoneUse> getLstTimezone();

}
