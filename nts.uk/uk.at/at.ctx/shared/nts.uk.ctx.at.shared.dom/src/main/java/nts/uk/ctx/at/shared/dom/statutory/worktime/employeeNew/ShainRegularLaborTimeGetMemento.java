/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;

/**
 * The Interface EmployeeRegularWorkHourGetMemento.
 */
public interface ShainRegularLaborTimeGetMemento {

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
	 * Gets the working time setting new.
	 *
	 * @return the working time setting new
	 */
	WorkingTimeSetting getWorkingTimeSet();

}
