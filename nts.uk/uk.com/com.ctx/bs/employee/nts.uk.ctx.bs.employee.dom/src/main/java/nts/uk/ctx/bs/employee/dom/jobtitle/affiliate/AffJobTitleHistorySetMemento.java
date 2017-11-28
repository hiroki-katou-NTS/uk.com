/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.jobtitle.affiliate;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class JobTitleHistorySetMemento.
 */
public interface AffJobTitleHistorySetMemento {
	
	
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
	
	
	/**
	 * Sets the job title id.
	 *
	 * @param jobTitleId the new job title id
	 */
	void setJobTitleId(PositionId jobTitleId);

}
