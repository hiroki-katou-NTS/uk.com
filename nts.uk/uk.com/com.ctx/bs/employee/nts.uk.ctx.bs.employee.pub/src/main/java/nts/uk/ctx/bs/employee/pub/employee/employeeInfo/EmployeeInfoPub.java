/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pub.employee.employeeInfo;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * The Interface EmployeePub.
 */
public interface EmployeeInfoPub {

	/**
	 * Find by wpk ids.
	 *
	 * @param companyId
	 *            the company id
	 * @param workplaceIds
	 *            the workplace ids
	 * @param baseDate
	 *            the base date
	 * @return the list
	 */
	EmployeeInfoDto findByCid(String companyId, String employeeCode);

}
