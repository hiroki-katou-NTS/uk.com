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
 * The Interface EmpFlexSettingGetMemento.
 */
public interface EmpFlexSettingGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();

	/**
	 * Gets the employee id.
	 *
	 * @return the employee id
	 */
	EmployeeId getEmployeeId();

	/**
	 * Gets the year.
	 *
	 * @return the year
	 */
	Year getYear();

	/**
	 * Gets the statutory setting.
	 *
	 * @return the statutory setting
	 */
	List<MonthlyTime> getStatutorySetting();

	/**
	 * Gets the specified setting.
	 *
	 * @return the specified setting
	 */
	List<MonthlyTime> getSpecifiedSetting();

}
