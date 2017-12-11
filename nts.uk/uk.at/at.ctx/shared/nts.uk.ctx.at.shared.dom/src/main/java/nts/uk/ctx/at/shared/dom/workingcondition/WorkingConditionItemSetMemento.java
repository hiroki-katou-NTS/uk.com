package nts.uk.ctx.at.shared.dom.workingcondition;

/**
 * The Interface WorkingConditionSetMemento.
 */

public interface WorkingConditionItemSetMemento {
	
	/**
	 * Sets the schedule management atr.
	 *
	 * @param scheduleManagementAtr the new schedule management atr
	 */
	public void setScheduleManagementAtr(NotUseAtr scheduleManagementAtr);
	
	/**
	 * Sets the vacation added time atr.
	 *
	 * @param vacationAddedTimeAtr the new vacation added time atr
	 */
	public void setVacationAddedTimeAtr(NotUseAtr vacationAddedTimeAtr);
	
	/**
	 * Sets the labor system.
	 *
	 * @param laborSystem the new labor system
	 */
	public void setLaborSystem(WorkingSystem laborSystem);
	
	/**
	 * Sets the work category.
	 *
	 * @param workCategory the new work category
	 */
	public void setWorkCategory(PersonalWorkCategory workCategory);
	
	/**
	 * Sets the contract time.
	 *
	 * @param contractTime the new contract time
	 */
	public void setContractTime(LaborContractTime contractTime);
	
	/**
	 * Sets the auto interval set atr.
	 *
	 * @param autoIntervalSetAtr the new auto interval set atr
	 */
	public void setAutoIntervalSetAtr(NotUseAtr autoIntervalSetAtr);
	
	/**
	 * Sets the history id.
	 *
	 * @param historyId the new history id
	 */
	public void setHistoryId(String historyId);
	
	/**
	 * Sets the work day of week.
	 *
	 * @param workDayOfWeek the new work day of week
	 */
	public void setWorkDayOfWeek(PersonalDayOfWeek workDayOfWeek);
	
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
	public void setAutoStampSetAtr(NotUseAtr autoStampSetAtr);
	
	/**
	 * Sets the schedule method.
	 *
	 * @param scheduleMethod the new schedule method
	 */
	public void setScheduleMethod(ScheduleMethod scheduleMethod);

	/**
	 * Sets the holiday add time set.
	 *
	 * @param holidayAddTimeSet the new holiday add time set
	 */
	public void setHolidayAddTimeSet(BreakdownTimeDay holidayAddTimeSet);
}
