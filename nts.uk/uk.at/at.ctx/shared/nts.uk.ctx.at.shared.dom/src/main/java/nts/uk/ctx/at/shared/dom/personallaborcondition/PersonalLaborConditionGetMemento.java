/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.personallaborcondition;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.time.BreakDownTimeDay;

/**
 * The Interface PersonalLaborConditionGetMemento.
 */
public interface PersonalLaborConditionGetMemento {
	
	/**
	 * Gets the schedule management atr.
	 *
	 * @return the schedule management atr
	 */
	public UseAtr getScheduleManagementAtr();
	
	
	/**
	 * Gets the holiday add time set.
	 *
	 * @return the holiday add time set
	 */
	public BreakDownTimeDay getHolidayAddTimeSet();
	
	
	/**
	 * Gets the work category.
	 *
	 * @return the work category
	 */
	public PersonalWorkCategory getWorkCategory();
	
	
	/**
	 * Gets the work day of week.
	 *
	 * @return the work day of week
	 */
	public PersonalDayOfWeek getWorkDayOfWeek();
	
	
	/**
	 * Gets the period.
	 *
	 * @return the period
	 */
	public DatePeriod getPeriod();
	
	
	/**
	 * Gets the employee id.
	 *
	 * @return the employee id
	 */
	public String getEmployeeId();
	
	
	/**
	 * Gets the auto stamp set atr.
	 *
	 * @return the auto stamp set atr
	 */
	public UseAtr getAutoStampSetAtr();

}
