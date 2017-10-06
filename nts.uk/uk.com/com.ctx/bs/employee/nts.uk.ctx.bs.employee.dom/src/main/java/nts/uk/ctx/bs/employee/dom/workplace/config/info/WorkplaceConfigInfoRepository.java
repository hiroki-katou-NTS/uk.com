/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace.config.info;

import java.util.Optional;

/**
 * The Interface WorkplaceConfigInfoRepository.
 */
public interface WorkplaceConfigInfoRepository {
	
	/**
	 * Adds the.
	 *
	 * @param wkpConfigInfo the wkp config info
	 */
	void add(WorkplaceConfigInfo wkpConfigInfo);
	
	/**
	 * Update.
	 *
	 * @param wkpConfigInfo the wkp config info
	 */
	void update(WorkplaceConfigInfo wkpConfigInfo);
	
	/**
	 * Find.
	 *
	 * @param companyId the company id
	 * @param historyId the history id
	 * @return the optional
	 */
	Optional<WorkplaceConfigInfo> find(String companyId, String historyId);
	
	/**
	 * Find.
	 *
	 * @param companyId the company id
	 * @param historyId the history id
	 * @param wkpId the wkp id
	 * @return the optional
	 */
	Optional<WorkplaceConfigInfo> find(String companyId, String historyId, String wkpId);
}
