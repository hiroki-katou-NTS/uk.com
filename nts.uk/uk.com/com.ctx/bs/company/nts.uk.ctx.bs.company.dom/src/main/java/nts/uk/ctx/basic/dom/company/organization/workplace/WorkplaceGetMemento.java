/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.workplace;


import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.CompanyId;

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
	Period getPeriod();
	
	
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
