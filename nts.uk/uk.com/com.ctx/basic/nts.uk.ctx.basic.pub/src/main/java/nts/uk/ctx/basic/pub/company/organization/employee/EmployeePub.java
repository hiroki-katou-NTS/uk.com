/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.pub.company.organization.employee;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * The Interface EmployeePub.
 */
public interface EmployeePub {

	/**
	 * Find.
	 *
	 * @param employeeId the employee id
	 * @param referenceDate the reference date
	 * @return the optional
	 */
	Optional<EmployeeDto> find(String employeeId, GeneralDate baseDate);
}
