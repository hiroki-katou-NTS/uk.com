/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.jobtile.affiliate;

import nts.uk.ctx.bs.employee.dom.common.history.Period;

/**
 * The Class JobTitleHistorySetMemento.
 */
public interface AffJobTitleHistorySetMemento {
	
	
	/**
	 * Sets the period.
	 *
	 * @param period the new period
	 */
	void setPeriod(Period period);
	
	
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
