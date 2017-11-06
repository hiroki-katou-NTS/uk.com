/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.personallaborcondition;

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
	 * Sets the holiday time.
	 *
	 * @param holidayTime the new holiday time
	 */
	public void setHolidayTime(SingleDaySchedule holidayTime);
	
	
	/**
	 * Sets the weekday time.
	 *
	 * @param weekdayTime the new weekday time
	 */
	public void setWeekdayTime(SingleDaySchedule weekdayTime);
	
	
	/**
	 * Sets the public holiday work.
	 *
	 * @param publicHolidayWork the new public holiday work
	 */
	public void setPublicHolidayWork(Optional<SingleDaySchedule> publicHolidayWork);
	
	
	/**
	 * Sets the in law break time.
	 *
	 * @param inLawBreakTime the new in law break time
	 */
	public void setInLawBreakTime(Optional<SingleDaySchedule> inLawBreakTime);
	
	
	/**
	 * Sets the outside law break time.
	 *
	 * @param outsideLawBreakTime the new outside law break time
	 */
	public void setOutsideLawBreakTime(Optional<SingleDaySchedule> outsideLawBreakTime);
	
	
	/**
	 * Sets the holiday attendance time.
	 *
	 * @param holidayAttendanceTime the new holiday attendance time
	 */
	public void setHolidayAttendanceTime(Optional<SingleDaySchedule> holidayAttendanceTime);
}
