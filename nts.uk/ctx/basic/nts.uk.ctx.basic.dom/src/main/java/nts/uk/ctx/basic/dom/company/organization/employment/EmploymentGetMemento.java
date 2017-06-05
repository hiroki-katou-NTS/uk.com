/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.employment;

import nts.uk.ctx.basic.dom.company.organization.CompanyId;

/**
 * The Interface EmploymentGetMemento.
 */
public interface EmploymentGetMemento {
	
	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();
	
	/**
	 * Gets the employment code.
	 *
	 * @return the employment code
	 */
	EmploymentCode getEmploymentCode();
	
	/**
	 * Gets the employment name.
	 *
	 * @return the employment name
	 */
	EmploymentName getEmploymentName();
	
	/**
	 * Gets the work closure id.
	 *
	 * @return the work closure id
	 */
	Integer getWorkClosureId();
	
	/**
	 * Gets the salary closure id.
	 *
	 * @return the salary closure id
	 */
	Integer getSalaryClosureId();
}
