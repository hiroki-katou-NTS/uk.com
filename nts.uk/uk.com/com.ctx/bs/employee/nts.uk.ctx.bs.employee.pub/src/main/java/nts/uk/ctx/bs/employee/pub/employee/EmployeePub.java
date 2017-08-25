/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pub.employee;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * The Interface EmployeePub.
 */
public interface EmployeePub {

	/**
	 * Gets the workplace id.
	 *
	 * @param companyId the company id
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the workplace id
	 */
	String getWorkplaceId(String companyId,String employeeId, GeneralDate baseDate);

	/**
	 * Gets the employment code.
	 *
	 * @param companyId the company id
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the employment code
	 */
	String getEmploymentCode(String companyId, String employeeId, GeneralDate baseDate);

	/**
	 * Find by wpk ids.
	 *
	 * @param companyId the company id
	 * @param workplaceIds the workplace ids
	 * @param baseDate the base date
	 * @return the list
	 */
	List<EmployeeDto> findByWpkIds(String companyId, List<String> workplaceIds, GeneralDate baseDate);
}
