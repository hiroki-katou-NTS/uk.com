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
	 * Find by person ids.
	 *
	 * @param personIds the person ids
	 * @return the list
	 */
	List<PubWorkplaceDto> findAllWorkplaceOfCompany(String companyId, GeneralDate baseDate);

	/**
	 * Find all hierarchy child.
	 *
	 * @param companyId the company id
	 * @param workplaceId the workplace id
	 * @return the list
	 */
	List<PubWorkplaceHierarchyDto> findAllHierarchyChild(String companyId, String workplaceId);
}
