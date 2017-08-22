/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.pub.company.organization.employee;

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
	String getWorkplaceId(String employeeId, GeneralDate baseDate);

	/**
	 * Gets the employee code.
	 *
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the employee code
	 */
	String getEmploymentCode(String employeeId, GeneralDate baseDate);
}
