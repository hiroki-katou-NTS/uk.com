/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.employee.department;

import nts.uk.ctx.bs.employee.dom.common.history.Period;

/**
 * The Interface DepartmentHistorySetMemento.
 */
public interface DepartmentHistorySetMemento {

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	void setId(String id);
	
	/**
	 * Sets the period.
	 *
	 * @param period the new period
	 */
	void setPeriod(Period period);
}
