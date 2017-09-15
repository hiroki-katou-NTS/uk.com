/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.employee.department;

import nts.uk.ctx.bs.employee.dom.common.history.Period;

/**
 * The Interface DepartmentHistoryGetMemento.
 */
public interface DepartmentHistoryGetMemento {

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	String getId();
	
	/**
	 * Gets the period.
	 *
	 * @return the period
	 */
	Period getPeriod();
}
