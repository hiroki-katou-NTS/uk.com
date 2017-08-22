/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.access.workplace;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.access.workplace.dto.AcWorkplaceDto;
import nts.uk.ctx.bs.employee.dom.access.workplace.dto.AcWorkplaceHierarchyDto;

/**
 * The Interface PersonAdapter.
 */
public interface WorkplaceAdapter {

	// List<AcWorkplaceDto> findAllWorkplaceOfCompany(String companyId, String
	// workplaceId, GeneralDate baseDate);

	/**
	 * Find by person ids.
	 *
	 * @param personIds the person ids
	 * @return the list
	 */
	List<AcWorkplaceDto> findAllWorkplaceOfCompany(String companyId, GeneralDate baseDate);

	/**
	 * Find all hierarchy child.
	 *
	 * @param companyId the company id
	 * @param workplaceId the workplace id
	 * @return the list
	 */
	List<AcWorkplaceHierarchyDto> findAllHierarchyChild(String companyId, String workplaceId);
}
