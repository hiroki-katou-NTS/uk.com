/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.personallaborcondition;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.time.BreakDownTimeDay;

/**
 * The Interface PersonalLaborConditionSetMemento.
 */
public interface PersonalLaborConditionSetMemento {
	
	/**
	 * Sets the schedule management atr.
	 *
	 * @param scheduleManagementAtr the new schedule management atr
	 */
	public void setScheduleManagementAtr(UseAtr scheduleManagementAtr);
	
	
	/**
	 * Sets the holiday add time set.
	 *
	 * @param holidayAddTimeSet the new holiday add time set
	 */
	public void setHolidayAddTimeSet(BreakDownTimeDay holidayAddTimeSet);
	
	
	/**
	 * Sets the work category.
	 *
	 * @param workCategory the new work category
	 */
	public void setWorkCategory(PersonalWorkCategory workCategory);
	
	
	/**
	 * Sets the work day of week.
	 *
	 * @param workDayOfWeek the new work day of week
	 */
	public void setWorkDayOfWeek(PersonalDayOfWeek workDayOfWeek);
	
	
	/**
	 * Sets the period.
	 *
	 * @param period the new period
	 */
	public void setPeriod(DatePeriod period);
	
	
	/**
	 * Sets the employee id.
	 *
	 * @param employeeId the new employee id
	 */
	public void setEmployeeId(String employeeId);
	
	
	/**
	 * Sets the auto stamp set atr.
	 *
	 * @param autoStampSetAtr the new auto stamp set atr
	 */
	public void setAutoStampSetAtr(UseAtr autoStampSetAtr);

}
