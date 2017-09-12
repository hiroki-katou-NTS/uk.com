/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.employment.affiliate;

import nts.uk.ctx.bs.employee.dom.common.history.Period;

/**
 * The Interface AffiliationEmploymentHistoryGetMemento.
 */
public interface AffEmploymentHistoryGetMemento {
	
	/**
	 * Gets the employment code.
	 *
	 * @return the employment code
	 */
	EmploymentCode getEmploymentCode();
	
	
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
	String getEmployeeId();

}
