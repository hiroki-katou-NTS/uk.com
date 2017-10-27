/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.employment.affiliate;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

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
	DatePeriod getPeriod();
	
	
	/**
	 * Gets the employee id.
	 *
	 * @return the employee id
	 */
	String getEmployeeId();

}
