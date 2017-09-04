/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.company.pub.workplace;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * The Interface WorkplacePub.
 */
public interface WorkplacePub {

	/**
	 * Find wpk ids.
	 *
	 * @param wpkCode the wpk code
	 * @param date the date
	 * @return the list
	 */
	// RequestList #41
	List<String> findWpkIdsByWkpCode(String companyId, String wpkCode, GeneralDate date);

	/**
	 * Find by wpk id.
	 *
	 * @param workplaceId the workplace id
	 * @return the optional
	 */
	WorkplaceExport findByWkpId(String workplaceId);

	/**
	 * Find by wpk ids.
	 *
	 * @param workplaceIds the workplace ids
	 * @return the list
	 */
	List<WorkplaceExport> findByWkpIds(List<String> workplaceIds);

	/**
	 * Find by person ids.
	 *
	 * @param personIds the person ids
	 * @return the list
	 */
	List<WorkplaceExport> findAllWorkplaceOfCompany(String companyId, GeneralDate baseDate);

	/**
	 * Find all hierarchy child.
	 *
	 * @param companyId the company id
	 * @param workplaceId the workplace id
	 * @return the list
	 */
	List<WorkplaceHierarchyExport> findAllHierarchyChild(String companyId, String workplaceId);
}
