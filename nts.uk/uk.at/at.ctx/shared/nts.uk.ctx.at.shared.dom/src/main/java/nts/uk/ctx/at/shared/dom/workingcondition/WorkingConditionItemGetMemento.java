package nts.uk.ctx.at.shared.dom.workingcondition;

import java.util.Optional;

/**
 * The Interface WorkingConditionGetMemento.
 */
public interface WorkingConditionItemGetMemento {
	
	/**
	 * Gets the schedule management atr.
	 *
	 * @return the schedule management atr
	 */
	NotUseAtr getScheduleManagementAtr();
		
	/**
	 * Gets the vacation added time atr.
	 *
	 * @return the vacation added time atr
	 */
	NotUseAtr getVacationAddedTimeAtr();
	
	/**
	 * Gets the labor system.
	 *
	 * @return the labor system
	 */
	WorkingSystem getLaborSystem();
	
	/**
	 * Gets the work category.
	 *
	 * @return the work category
	 */
	PersonalWorkCategory getWorkCategory();
	
	/**
	 * Gets the contract time.
	 *
	 * @return the contract time
	 */
	LaborContractTime getContractTime();
	
	/**
	 * Gets the auto interval set atr.
	 *
	 * @return the auto interval set atr
	 */
	NotUseAtr getAutoIntervalSetAtr();
	
	/**
	 * Gets the history id.
	 *
	 * @return the history id
	 */
	String getHistoryId();
	
	/**
	 * Gets the work day of week.
	 *
	 * @return the work day of week
	 */
	PersonalDayOfWeek getWorkDayOfWeek();
	
	/**
	 * Gets the employee id.
	 *
	 * @return the employee id
	 */
	String getEmployeeId();
	
	/**
	 * Gets the auto stamp set atr.
	 *
	 * @return the auto stamp set atr
	 */
	NotUseAtr getAutoStampSetAtr();
	
	/**
	 * Gets the schedule method.
	 *
	 * @return the schedule method
	 */
	Optional<ScheduleMethod> getScheduleMethod();

	/**
	 * Gets the holiday add time set.
	 *
	 * @return the holiday add time set
	 */
	Optional<BreakdownTimeDay> getHolidayAddTimeSet();
}
