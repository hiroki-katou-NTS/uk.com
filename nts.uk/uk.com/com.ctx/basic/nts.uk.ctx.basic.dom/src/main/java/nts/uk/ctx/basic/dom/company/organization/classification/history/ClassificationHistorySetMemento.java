/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.classification.history;

import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.classification.ClassificationCode;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeId;

/**
 * The Interface ClassificationHistorySetMemento.
 */
public interface ClassificationHistorySetMemento {

	/**
	 * Sets the classification code.
	 *
	 * @param classificationCode the new classification code
	 */
	void setClassificationCode(ClassificationCode classificationCode);
	
	
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
}
