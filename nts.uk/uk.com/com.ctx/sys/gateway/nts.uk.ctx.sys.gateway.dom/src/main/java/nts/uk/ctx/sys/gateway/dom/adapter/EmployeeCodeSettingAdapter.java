/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.adapter;

import java.util.Optional;

/**
 * The Interface EmployeeCodeSettingAdapter.
 */
public interface EmployeeCodeSettingAdapter {

	/**
	 * Gets the by company id.
	 *
	 * @param companyId the company id
	 * @return the by company id
	 */
	Optional<EmployeeCodeSettingDto> getbyCompanyId(String companyId);
}
