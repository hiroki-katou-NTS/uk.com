/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.login.adapter;

import java.util.Optional;

import nts.uk.ctx.sys.gateway.dom.login.dto.EmployeeImport;

/**
 * The Interface SysEmployeeAdapter.
 */
public interface SysEmployeeAdapter {

	/**
	 * Gets the current info by scd.
	 *
	 * @param companyId the company id
	 * @param employeeCode the employee code
	 * @return the current info by scd
	 */
	Optional<EmployeeImport> getCurrentInfoByScd(String companyId,String employeeCode);
}
