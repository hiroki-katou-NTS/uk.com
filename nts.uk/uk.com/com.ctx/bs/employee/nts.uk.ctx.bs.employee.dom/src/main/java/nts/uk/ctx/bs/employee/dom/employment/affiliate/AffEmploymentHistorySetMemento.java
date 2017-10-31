/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.employment.affiliate;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Interface EmploymentHistorySetMemento.
 */
public interface AffEmploymentHistorySetMemento {

	/**
	 * Sets the employment code.
	 *
	 * @param employmentCode the new employment code
	 */
	void setEmploymentCode(EmploymentCode employmentCode);
	
	
	/**
	 * Sets the period.
	 *
	 * @param period the new period
	 */
	void setPeriod(DatePeriod period);
	
	
	/**
	 * Sets the employee id.
	 *
	 * @param employeeId the new employee id
	 */
	void setEmployeeId(String employeeId);
}
