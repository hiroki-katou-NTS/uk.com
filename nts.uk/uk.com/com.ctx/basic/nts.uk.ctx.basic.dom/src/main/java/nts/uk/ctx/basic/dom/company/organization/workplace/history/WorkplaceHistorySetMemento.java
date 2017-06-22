/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.workplace.history;

import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeId;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceId;

/**
 * The Interface WorkplaceHistorySetMemento.
 */
public interface WorkplaceHistorySetMemento {
	
	/**
	 * Sets the.
	 *
	 * @param period the period
	 */
	void setPeriod(Period period);
	
	
	/**
	 * Sets the employee id.
	 *
	 * @param employeeId the new employee id
	 */
	void setEmployeeId(EmployeeId employeeId);
	
	
	/**
	 * Sets the work place id.
	 *
	 * @param workplaceId the new work place id
	 */
	void setWorkplaceId(WorkplaceId workplaceId);

}
