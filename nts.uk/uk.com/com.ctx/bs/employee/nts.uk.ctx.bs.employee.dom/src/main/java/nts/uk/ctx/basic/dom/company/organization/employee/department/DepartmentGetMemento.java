/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.employee.department;

import nts.uk.ctx.bs.employee.dom.common.CompanyId;

/**
 * The Interface DepartmentGetMemento.
 */
public interface DepartmentGetMemento {
	
	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	String getId();
	
	/**
	 * Gets the dep history.
	 *
	 * @return the dep history
	 */
	DepartmentHistory getDepHistory();

}
