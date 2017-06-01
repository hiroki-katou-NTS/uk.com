/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.employee;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.company.organization.CompanyId;

/**
 * The Class Employee.
 */

/**
 * Gets the retirement date.
 *
 * @return the retirement date
 */
@Getter
public class Employee extends AggregateRoot{
	
	/** The company id. */
	private CompanyId companyId;
	
	/** The person id. */
	private PersonId personId;
	
	/** The join date. */
	private GeneralDate joinDate;
	
	/** The employee id. */
	private EmployeeId employeeId;
	
	/** The employee code. */
	private EmployeeCode employeeCode;
	
	/** The employee mail address. */
	private EmployeeMailAddress employeeMailAddress;
	
	/** The retirement date. */
	private GeneralDate retirementDate;
	
}
