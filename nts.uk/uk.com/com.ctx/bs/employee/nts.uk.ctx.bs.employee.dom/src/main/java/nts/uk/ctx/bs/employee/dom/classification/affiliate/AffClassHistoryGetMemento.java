/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.classification.affiliate;

import nts.uk.ctx.bs.employee.dom.classification.ClassificationCode;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Interface AffiliationClassificationHistoryGetMemento.
 */
public interface AffClassHistoryGetMemento {

	/**
	 * Gets the classification code.
	 *
	 * @return the classification code
	 */
	ClassificationCode getClassificationCode();
	
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
