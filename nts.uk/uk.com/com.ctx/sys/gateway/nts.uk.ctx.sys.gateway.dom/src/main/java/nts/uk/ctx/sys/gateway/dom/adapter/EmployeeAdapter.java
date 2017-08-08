/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.adapter;

import java.util.Optional;

/**
 * The Interface EmployeeAdapter.
 */
public interface EmployeeAdapter {

	/**
	 * Gets the by employee code.
	 *
	 * @param companyId the company id
	 * @param employeeCode the employee code
	 * @return the by employee code
	 */
	Optional<EmployeeDto> getByEmployeeCode(String companyId,String employeeCode);
}
