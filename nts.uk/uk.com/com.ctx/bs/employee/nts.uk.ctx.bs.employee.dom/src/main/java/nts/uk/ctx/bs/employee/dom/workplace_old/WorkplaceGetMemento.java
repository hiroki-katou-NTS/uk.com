/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace_old;


import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Interface WorkplaceGetMemento.
 */
public interface WorkplaceGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();
	
	/**
	 * Gets the period.
	 *
	 * @return the period
	 */
	DatePeriod getPeriod();
	
	
	/**
	 * Gets the workplace id.
	 *
	 * @return the workplace id
	 */
	WorkplaceId getWorkplaceId();
	
	
	/**
	 * Gets the workplace code.
	 *
	 * @return the workplace code
	 */
	WorkplaceCode getWorkplaceCode();
	
	
	/**
	 * Gets the workplace name.
	 *
	 * @return the workplace name
	 */
	WorkplaceName getWorkplaceName();
}
