/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pub.employee.employeeInfo;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * The Interface EmployeePub.
 */
public interface EmployeeInfoPub {

	/**
	 * Find Employee by companyId,employeeCode
	 * For request No.18
	 *
	 */
	Optional<EmployeeInfoDtoExport> getEmployeeInfo(String companyId, String employeeCode , GeneralDate entryDate);

}
