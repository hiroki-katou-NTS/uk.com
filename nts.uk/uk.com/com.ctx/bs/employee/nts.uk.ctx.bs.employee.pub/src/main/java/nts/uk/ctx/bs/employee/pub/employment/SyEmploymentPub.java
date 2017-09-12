/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pub.employment;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * The Interface WorkplacePub.
 */
public interface SyEmploymentPub {

	/**
	 * Find by emp codes.
	 *
	 * @param employmentCodes the employment codes
	 * @return the list
	 */
	List<EmploymentExport> findByEmpCodes(List<String> employmentCodes);

	/**
	 * Gets the employment code.
	 *
	 * @param companyId the company id
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the employment code
	 */
	String getEmploymentCode(String companyId, String employeeId, GeneralDate baseDate);

}
