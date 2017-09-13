/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace.affiliate;

import nts.uk.ctx.bs.employee.dom.common.history.Period;

/**
 * The Interface AffiliationWorkplaceHistoryGetMemento.
 */
public interface AffWorkplaceHistoryGetMemento {
	
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
	
	
	/**
	 * Gets the work place id.
	 *
	 * @return the work place id
	 */
	WorkplaceId getWorkplaceId();

}
