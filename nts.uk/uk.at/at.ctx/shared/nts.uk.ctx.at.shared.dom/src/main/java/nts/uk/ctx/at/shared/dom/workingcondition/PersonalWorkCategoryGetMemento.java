/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workingcondition;

/**
 * The Interface PersonalWorkCategoryGetMemento.
 */
public interface PersonalWorkCategoryGetMemento {

	/**
	 * Gets the holiday work.
	 *
	 * @return the holiday work
	 */
	public SingleDaySchedule getHolidayWork();
	
	/**
	 * Gets the weekday time.
	 *
	 * @return the weekday time
	 */
	public SingleDaySchedule getWeekdayTime();
	
	/**
	 * Get day of week
	 * @return
	 */
	public PersonalDayOfWeek getDayOfWeek();
	

}
