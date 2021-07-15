/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workingcondition;

import java.util.Optional;

/**
 * The Interface PersonalWorkCategorySetMemento.
 */
public interface PersonalWorkCategorySetMemento {

	/**
	 * Sets the holiday work.
	 *
	 * @param holidayWork the new holiday work
	 */
	public void setHolidayWork(SingleDaySchedule holidayWork);
	
	
	/**
	 * Sets the weekday time.
	 *
	 * @param weekdayTime the new weekday time
	 */
	public void setWeekdayTime(SingleDaySchedule weekdayTime);
	
	
	public void setDayOfWeek(PersonalDayOfWeek dayOfWeek );
	
	
	
	
	
	
}
