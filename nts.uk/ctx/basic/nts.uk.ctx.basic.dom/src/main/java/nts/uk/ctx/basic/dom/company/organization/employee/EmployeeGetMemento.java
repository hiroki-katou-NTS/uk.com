/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.employee;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.company.organization.CompanyId;

/**
 * The Interface EmployeeGetMemento.
 */
public interface EmployeeGetMemento {
	
	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();
	
	
	/**
	 * Gets the person id.
	 *
	 * @return the person id
	 */
	PersonId getPersonId();
	
	
	/**
	 * Gets the join date.
	 *
	 * @return the join date
	 */
	GeneralDate  getJoinDate();
	
	/**
	 * Gets the employee id.
	 *
	 * @return the employee id
	 */
	EmployeeId getEmployeeId();
	
	
	/**
	 * Gets the employee code.
	 *
	 * @return the employee code
	 */
	EmployeeCode getEmployeeCode();
	
	
	/**
	 * Gets the employee mail address.
	 *
	 * @return the employee mail address
	 */
	EmployeeMailAddress getEmployeeMailAddress();
	
	
	/**
	 * Gets the retirement date.
	 *
	 * @return the retirement date
	 */
	GeneralDate getRetirementDate();
}
