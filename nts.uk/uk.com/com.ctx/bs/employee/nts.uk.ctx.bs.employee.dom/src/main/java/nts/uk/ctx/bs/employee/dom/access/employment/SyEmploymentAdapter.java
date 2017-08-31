/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.access.employment;

import java.util.List;

import nts.uk.ctx.bs.employee.dom.access.employment.dto.AcEmploymentDto;

/**
 * The Interface PersonAdapter.
 */
public interface SyEmploymentAdapter {

	/**
	 * Find by wpk ids.
	 *
	 * @param workplaceId the workplace id
	 * @return the list
	 */
	List<AcEmploymentDto> findByEmpCodes(List<String> employmentCodes);
}
