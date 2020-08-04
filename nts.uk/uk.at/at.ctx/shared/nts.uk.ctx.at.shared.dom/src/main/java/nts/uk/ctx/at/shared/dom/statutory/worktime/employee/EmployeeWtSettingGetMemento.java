/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.employee;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WorkingTimeSetting;

/**
 * The Interface EmployeeSettingGetMemento.
 */
public interface EmployeeWtSettingGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();

	/**
	 * Gets the working time setting.
	 *
	 * @return the working time setting
	 */
	WorkingTimeSetting getWorkingTimeSetting();

	/**
	 * Gets the year month.
	 *
	 * @return the year month
	 */
	YearMonth getYearMonth();

	/**
	 * Gets the employee id.
	 *
	 * @return the employee id
	 */
	String getEmployeeId();

}
