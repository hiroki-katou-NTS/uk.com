/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.workplace;

import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.CompanyId;

/**
 * The Interface WorkplaceSetMemento.
 */
public interface WorkplaceSetMemento {
	
	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(CompanyId companyId);
	
	
	/**
	 * Sets the period.
	 *
	 * @param period the new period
	 */
	void setPeriod(Period period);
	
	
	/**
	 * Sets the workplace id.
	 *
	 * @param workplaceId the new workplace id
	 */
	void setWorkplaceId(WorkplaceId workplaceId);
	
	
	/**
	 * Sets the workplace code.
	 *
	 * @param workplaceCode the new workplace code
	 */
	void setWorkplaceCode(WorkplaceCode workplaceCode);
	
	
	/**
	 * Sets the workplace name.
	 *
	 * @param workplaceName the new workplace name
	 */
	void setWorkplaceName(WorkplaceName workplaceName);

}
