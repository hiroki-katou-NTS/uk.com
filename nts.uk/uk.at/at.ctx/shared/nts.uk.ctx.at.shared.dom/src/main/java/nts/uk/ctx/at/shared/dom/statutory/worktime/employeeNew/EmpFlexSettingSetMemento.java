/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.common.MonthlyTime;
import nts.uk.ctx.at.shared.dom.common.Year;

/**
 * The Interface EmpFlexSettingSetMemento.
 */
public interface EmpFlexSettingSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(CompanyId companyId);

	/**
	 * Sets the employee id.
	 *
	 * @param employeeId the new employee id
	 */
	void setEmployeeId(EmployeeId employeeId);

	/**
	 * Sets the year.
	 *
	 * @param year the new year
	 */
	void setYear(Year year);

	/**
	 * Sets the statutory setting.
	 *
	 * @param statutorySetting the new statutory setting
	 */
	void setStatutorySetting(List<MonthlyTime> statutorySetting);

	/**
	 * Sets the specified setting.
	 *
	 * @param specifiedSetting the new specified setting
	 */
	void setSpecifiedSetting(List<MonthlyTime> specifiedSetting);

}
