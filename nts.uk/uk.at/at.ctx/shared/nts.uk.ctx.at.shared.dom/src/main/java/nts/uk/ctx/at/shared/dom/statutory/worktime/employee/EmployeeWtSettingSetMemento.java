/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.employee;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WorkingTimeSetting;

/**
 * The Interface EmployeeSettingSetMemento.
 */
public interface EmployeeWtSettingSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(CompanyId companyId);

	/**
	 * Sets the working time setting.
	 *
	 * @param workingTimeSetting the new working time setting
	 */
	void setWorkingTimeSetting(WorkingTimeSetting workingTimeSetting);

	/**
	 * Sets the year month.
	 *
	 * @param YearMonth the new year month
	 */
	void setYearMonth(YearMonth YearMonth);

	/**
	 * Sets the employee id.
	 *
	 * @param EmployeeId the new employee id
	 */
	void setEmployeeId(String EmployeeId);

}
