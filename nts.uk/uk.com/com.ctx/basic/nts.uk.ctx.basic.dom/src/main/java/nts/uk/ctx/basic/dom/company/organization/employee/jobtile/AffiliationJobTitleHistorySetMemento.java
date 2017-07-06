/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.employee.jobtile;

import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeId;
import nts.uk.ctx.basic.dom.company.organization.jobtitle.PositionId;

/**
 * The Class JobTitleHistorySetMemento.
 */
public interface AffiliationJobTitleHistorySetMemento {
	
	
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
	void setEmployeeId(EmployeeId employeeId);
	
	
	/**
	 * Sets the job title id.
	 *
	 * @param jobTitleId the new job title id
	 */
	void setJobTitleId(PositionId jobTitleId);

}
