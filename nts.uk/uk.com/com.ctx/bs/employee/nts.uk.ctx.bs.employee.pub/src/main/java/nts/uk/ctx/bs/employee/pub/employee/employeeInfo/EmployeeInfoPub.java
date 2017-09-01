/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pub.employee.employeeInfo;

import java.util.Optional;

/**
 * The Interface EmployeePub.
 */
public interface EmployeeInfoPub {

	/**
	 * Find Employee by companyId,employeeCode
	 *
	 * @param companyId
	 *            the company id
	 * @param workplaceIds
	 *            the workplace ids
	 * @param baseDate
	 *            the base date
	 * @return the list
	 */
	Optional<EmployeeInfoDtoExport> findByCidSid(String companyId, String employeeCode);

}
