/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.personallaborcondition;

import java.util.Optional;

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
	 * Gets the holiday time.
	 *
	 * @return the holiday time
	 */
	public SingleDaySchedule getHolidayTime();
	
	/**
	 * Gets the weekday time.
	 *
	 * @return the weekday time
	 */
	public SingleDaySchedule getWeekdayTime();
	
	/**
	 * Gets the public holiday work.
	 *
	 * @return the public holiday work
	 */
	public Optional<SingleDaySchedule> getPublicHolidayWork();
	
	
	/**
	 * Gets the in law break time.
	 *
	 * @return the in law break time
	 */
	public Optional<SingleDaySchedule> getInLawBreakTime();
	
	
	/**
	 * Gets the outside law break time.
	 *
	 * @return the outside law break time
	 */
	public Optional<SingleDaySchedule> getOutsideLawBreakTime();
	
	
	/**
	 * Gets the holiday attendance time.
	 *
	 * @return the holiday attendance time
	 */
	public Optional<SingleDaySchedule> getHolidayAttendanceTime();
}
