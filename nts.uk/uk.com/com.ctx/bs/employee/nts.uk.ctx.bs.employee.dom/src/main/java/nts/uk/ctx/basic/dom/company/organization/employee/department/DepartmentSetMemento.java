/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.employee.department;

import nts.uk.ctx.bs.employee.dom.common.CompanyId;

/**
 * The Interface DepartmentSetMemento.
 */
public interface DepartmentSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(CompanyId companyId);
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	void setId(String id);
	
	/**
	 * Sets the dep history.
	 *
	 * @param depHistory the new dep history
	 */
	void setDepHistory(DepartmentHistory depHistory);
}
