/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.employee;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.company.organization.CompanyId;

/**
 * The Interface EmployeeSetMemento.
 */
public interface EmployeeSetMemento {
	
	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(CompanyId companyId);
	
	/**
	 * Sets the person id.
	 *
	 * @param personId the new person id
	 */
	void setPersonId(PersonId personId);
	
	
	/**
	 * Sets the join date.
	 *
	 * @param joinDate the new join date
	 */
	void setJoinDate(GeneralDate joinDate);
	
	/**
	 * Sets the employee id.
	 *
	 * @param employeeId the new employee id
	 */
	void setEmployeeId(EmployeeId employeeId);
	
	
	/**
	 * Sets the employee code.
	 *
	 * @param employeeCode the new employee code
	 */
	void setEmployeeCode(EmployeeCode employeeCode);
	
	
	/**
	 * Sets the employee mail address.
	 *
	 * @param employeeMailAddress the new employee mail address
	 */
	void setEmployeeMailAddress(EmployeeMailAddress employeeMailAddress);
	
	
	/**
	 * Sets the retirement date.
	 *
	 * @param retirementDate the new retirement date
	 */
	void setRetirementDate(GeneralDate retirementDate);

}
