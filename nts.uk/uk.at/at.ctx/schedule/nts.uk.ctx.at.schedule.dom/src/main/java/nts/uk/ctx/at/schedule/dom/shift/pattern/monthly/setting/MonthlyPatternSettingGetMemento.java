/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.setting;

import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternCode;

/**
 * The Interface MonthlyPatternSettingGetMemento.
 */
public interface MonthlyPatternSettingGetMemento {
	
	/**
	 * Gets the monthly pattern code.
	 *
	 * @return the monthly pattern code
	 */
	public MonthlyPatternCode getMonthlyPatternCode();

	/**
	 * Gets the employee id.
	 *
	 * @return the employee id
	 */
	public String getEmployeeId();
}
