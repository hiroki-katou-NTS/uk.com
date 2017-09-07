/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.login.adapter;

import java.util.Optional;

import nts.uk.ctx.sys.gateway.dom.login.dto.EmployeeImport;

/**
 * The Interface EmployeeAdapter.
 */
public interface SysEmployeeAdapter {

	/**
	 * Gets the by employee code.
	 *
	 * @param companyId the company id
	 * @param employeeCode the employee code
	 * @return the by employee code
	 */
	Optional<EmployeeImport> getByEmployeeCode(String companyId,String employeeCode);
}
