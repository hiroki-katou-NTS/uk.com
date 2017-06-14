/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.workplace;

import java.util.List;

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
	 * @param date the date
	 * @param format the format
	 * @return the list
	 */
	List<Workplace> findAll(String companyId, String date, String format);

}
