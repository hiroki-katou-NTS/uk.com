/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.workplace;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * The Interface WorkplaceRepository.
 */
public interface WorkplaceRepository {
	
	/**
	 * Adds the.
	 *
	 * @param workplace the workplace
	 */
	void add(Workplace workplace);
	
	/**
	 * Update.
	 *
	 * @param workplace the workplace
	 */
	void update(Workplace workplace);
	
	
	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @param generalDate the general date
	 * @return the list
	 */
	List<Workplace> findAll(String companyId, GeneralDate generalDate);

}
