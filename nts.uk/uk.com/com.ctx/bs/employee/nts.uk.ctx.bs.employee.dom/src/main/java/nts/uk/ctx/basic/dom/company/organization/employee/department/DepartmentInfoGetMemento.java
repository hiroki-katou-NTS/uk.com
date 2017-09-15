/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.employee.department;

import nts.uk.ctx.bs.employee.dom.common.CompanyId;

/**
 * The Interface DepartmentInfoGetMemento.
 */
public interface DepartmentInfoGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();
	
	/**
	 * Gets the dep history id.
	 *
	 * @return the dep history id
	 */
	String getDepHistoryId();
	
	/**
	 * Gets the department id.
	 *
	 * @return the department id
	 */
	String getDepartmentId();
	
	/**
	 * Gets the department code.
	 *
	 * @return the department code
	 */
	DepartmentCode getDepartmentCode();
	
	/**
	 * Gets the department name.
	 *
	 * @return the department name
	 */
	DepartmentName getDepartmentName();
	
	/**
	 * Gets the dep generic name.
	 *
	 * @return the dep generic name
	 */
	DepartmentGenericName getDepGenericName();
}
