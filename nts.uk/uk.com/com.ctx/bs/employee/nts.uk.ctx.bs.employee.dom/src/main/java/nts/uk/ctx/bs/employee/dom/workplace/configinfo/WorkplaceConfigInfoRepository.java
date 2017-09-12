/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace.configinfo;

import java.util.List;

/**
 * The Interface WorkplaceConfigInfoRepository.
 */
public interface WorkplaceConfigInfoRepository {

	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @param historyId the history id
	 * @return the list
	 */
	List<WorkplaceConfigInfo> findAll(String companyId, String historyId);
}
