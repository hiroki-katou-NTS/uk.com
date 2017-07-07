/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.employee.classification;

import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.classification.ClassificationCode;

/**
 * The Interface AffiliationClassificationHistoryGetMemento.
 */
public interface AffiliationClassificationHistoryGetMemento {

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
	Period getPeriod();
	
	
	/**
	 * Gets the employee id.
	 *
	 * @return the employee id
	 */
	String getEmployeeId();
}
