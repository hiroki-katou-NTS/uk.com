/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.setting;

import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternCode;

/**
 * The Interface MonthlyPatternSettingSetMemento.
 */
public interface MonthlyPatternSettingSetMemento {
	
	/**
	 * Sets the monthly pattern code.
	 *
	 * @param monthlyPatternCode the new monthly pattern code
	 */
	public void setMonthlyPatternCode(MonthlyPatternCode monthlyPatternCode);
	
	/**
	 * Sets the employee id.
	 *
	 * @param employeeId the new employee id
	 */
	public void setEmployeeId(String employeeId);
}
