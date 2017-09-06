/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.access.workplace;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.access.workplace.dto.WorkplaceImport;
import nts.uk.ctx.bs.employee.dom.access.workplace.dto.WorkplaceHierarchyImport;

/**
 * The Interface PersonAdapter.
 */
public interface SyWorkplaceAdapter {

	/**
	 * Find by wpk id.
	 *
	 * @param workplaceId the workplace id
	 * @return the optional
	 */
	WorkplaceImport findByWkpId(String workplaceId);

	/**
	 * Find by wpk ids.
	 *
	 * @param workplaceId the workplace id
	 * @return the list
	 */
	List<WorkplaceImport> findByWkpIds(List<String> workplaceIds);

	/**
	 * Find by person ids.
	 *
	 * @param personIds the person ids
	 * @return the list
	 */
	List<WorkplaceImport> findAllWorkplaceOfCompany(String companyId, GeneralDate baseDate);

	/**
	 * Find all hierarchy child.
	 *
	 * @param companyId the company id
	 * @param workplaceId the workplace id
	 * @return the list
	 */
	List<WorkplaceHierarchyImport> findAllHierarchyChild(String companyId, String workplaceId);
}
