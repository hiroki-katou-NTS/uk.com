/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.workplace.history;

import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeId;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceId;

/**
 * The Interface WorkplaceHistoryGetMemento.
 */
public interface WorkplaceHistoryGetMemento {
	
	/**
	 * Gets the period.
	 *
	 * @return the period
	 */
	Period getPeriod();
	
	
	/**
	 * Gets the employee id.
	 *
	 * @return the employee id
	 */
	EmployeeId getEmployeeId();
	
	
	/**
	 * Gets the work place id.
	 *
	 * @return the work place id
	 */
	WorkplaceId getWorkplaceId();

}
