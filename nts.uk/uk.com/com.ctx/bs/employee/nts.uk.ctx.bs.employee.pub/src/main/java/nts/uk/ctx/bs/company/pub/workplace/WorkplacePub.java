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
	 * Find wpk ids by wkp code.
	 *
	 * @param companyId the company id
	 * @param wpkCode the wpk code
	 * @param baseDate the base date
	 * @return the list
	 */
	// RequestList #41
	List<String> findWpkIdsByWkpCode(String companyId, String wpkCode, GeneralDate baseDate);

	/**
	 * Find by wkp id.
	 *
	 * @param companyId the company id
	 * @param workplaceId the workplace id
	 * @param baseDate the base date
	 * @return the list
	 */
	// RequestList #66
	List<WkpCdNameExport> findByWkpId(String companyId, String workplaceId, GeneralDate baseDate);

	/**
	 * Find by wkp id.
	 *
	 * @param workplaceId the workplace id
	 * @return the workplace export
	 */
	WorkplaceExport findByWkpId(String workplaceId);

	/**
	 * Find by wkp ids.
	 *
	 * @param workplaceIds the workplace ids
	 * @return the list
	 */
	List<WorkplaceExport> findByWkpIds(List<String> workplaceIds);

	/**
	 * Find all workplace of company.
	 *
	 * @param companyId the company id
	 * @param baseDate the base date
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
