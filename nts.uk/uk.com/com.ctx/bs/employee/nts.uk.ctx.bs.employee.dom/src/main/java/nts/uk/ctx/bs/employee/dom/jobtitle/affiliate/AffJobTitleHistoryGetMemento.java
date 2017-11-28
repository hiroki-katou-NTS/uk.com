/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.jobtitle.affiliate;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Interface JobTitleHistoryGetMemento.
 */
public interface AffJobTitleHistoryGetMemento {

	
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
	
	
	/**
	 * Gets the job title id.
	 *
	 * @return the job title id
	 */
	PositionId getJobTitleId();
}
