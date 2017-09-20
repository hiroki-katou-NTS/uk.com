/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace.configinfo;

import java.util.Optional;

/**
 * The Interface WorkplaceConfigInfoRepository.
 */
public interface WorkplaceConfigInfoRepository {

	/**
	 * Adds the.
	 *
	 * @param workplaceConfigInfo the workplace config info
	 */
	void add(WorkplaceConfigInfo workplaceConfigInfo);
	
	/**
	 * Adds the list.
	 *
	 * @param WkpConfigInfo the wkp config info
	 */
	void addList(WorkplaceConfigInfo WkpConfigInfo);
	
	/**
	 * Find.
	 *
	 * @param companyId the company id
	 * @param historyId the history id
	 * @return the optional
	 */
	Optional<WorkplaceConfigInfo> find(String companyId, String historyId);
}
