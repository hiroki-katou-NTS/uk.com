/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.classification.affiliate;

import nts.uk.ctx.bs.employee.dom.classification.ClassificationCode;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Interface AffiliationClassificationHistorySetMemento.
 */
public interface AffClassHistorySetMemento {

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
	void setPeriod(DatePeriod period);
	
	
	/**
	 * Sets the employee id.
	 *
	 * @param employeeId the new employee id
	 */
	void setEmployeeId(String employeeId);
}
