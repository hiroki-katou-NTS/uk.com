/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.employee.department;

import nts.uk.ctx.bs.employee.dom.common.CompanyId;

/**
 * The Interface DepartmentInfoSetMemento.
 */
public interface DepartmentInfoSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(CompanyId companyId);
	
	/**
	 * Sets the dep history id.
	 *
	 * @param depHistoryId the new dep history id
	 */
	void setDepHistoryId(String depHistoryId);
	
	/**
	 * Sets the department id.
	 *
	 * @param departmentId the new department id
	 */
	void setDepartmentId(String departmentId);
	
	/**
	 * Sets the department code.
	 *
	 * @param departmentCode the new department code
	 */
	void setDepartmentCode(DepartmentCode departmentCode);
	
	/**
	 * Sets the department name.
	 *
	 * @param departmentName the new department name
	 */
	void setDepartmentName(DepartmentName departmentName);
	
	/**
	 * Sets the dep generic name.
	 *
	 * @param depGenericName the new dep generic name
	 */
	void setDepGenericName(DepartmentGenericName depGenericName);
	
}
