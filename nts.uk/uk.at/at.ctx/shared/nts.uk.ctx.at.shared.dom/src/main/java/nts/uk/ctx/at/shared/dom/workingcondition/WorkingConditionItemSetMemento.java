/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workingcondition;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;

/**
 * The Interface WorkingConditionItemSetMemento.
 */
public interface WorkingConditionItemSetMemento {
	
	/**
	 * Sets the hourly payment atr.
	 *
	 * @param hourlyPaymentAtr the new hourly payment atr
	 */
	void setHourlyPaymentAtr(HourlyPaymentAtr hourlyPaymentAtr);

	/**
	 * Sets the schedule management atr.
	 *
	 * @param scheduleManagementAtr the new schedule management atr
	 */
	void setScheduleManagementAtr(ManageAtr scheduleManagementAtr);

	/**
	 * Sets the vacation added time atr.
	 *
	 * @param vacationAddedTimeAtr the new vacation added time atr
	 */
	void setVacationAddedTimeAtr(NotUseAtr vacationAddedTimeAtr);

	/**
	 * Sets the labor system.
	 *
	 * @param laborSystem the new labor system
	 */
	void setLaborSystem(WorkingSystem laborSystem);

	/**
	 * Sets the work category.
	 *
	 * @param workCategory the new work category
	 */
	void setWorkCategory(PersonalWorkCategory workCategory, String employeeId);

	/**
	 * Sets the contract time.
	 *
	 * @param contractTime the new contract time
	 */
	void setContractTime(LaborContractTime contractTime);

	/**
	 * Sets the auto interval set atr.
	 *
	 * @param autoIntervalSetAtr the new auto interval set atr
	 */
	void setAutoIntervalSetAtr(NotUseAtr autoIntervalSetAtr);

	/**
	 * Sets the history id.
	 *
	 * @param historyId the new history id
	 */
	void setHistoryId(String historyId);

	/**
	 * Sets the work day of week.
	 *
	 * @param workDayOfWeek the new work day of week
	 */
	void setWorkDayOfWeek(PersonalDayOfWeek workDayOfWeek, String employeeId);

	/**
	 * Sets the employee id.
	 *
	 * @param employeeId the new employee id
	 */
	void setEmployeeId(String employeeId);

	/**
	 * Sets the auto stamp set atr.
	 *
	 * @param autoStampSetAtr the new auto stamp set atr
	 */
	void setAutoStampSetAtr(NotUseAtr autoStampSetAtr);

	/**
	 * Sets the schedule method.
	 *
	 * @param scheduleMethod the new schedule method
	 */
	void setScheduleMethod(Optional<ScheduleMethod> scheduleMethod, String employeeId);

	/**
	 * Sets the holiday add time set.
	 *
	 * @param holidayAddTimeSet the new holiday add time set
	 */
	void setHolidayAddTimeSet(Optional<BreakdownTimeDay> holidayAddTimeSet);
	
	/**
	 * Sets the time apply.
	 *
	 * @param timeApply the new time apply
	 */
	void setTimeApply(Optional<BonusPaySettingCode> timeApply);
	
	/**
	 * Sets the monthly pattern.
	 *
	 * @param monthlyPattern the new monthly pattern
	 */
	void setMonthlyPattern(Optional<MonthlyPatternCode> monthlyPattern);
}
