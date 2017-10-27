/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.basicworkregister;

import java.util.Optional;

/**
 * The Interface CompanyBasicWorkRepository.
 */
public interface CompanyBasicWorkRepository {
	
	/**
	 * Insert.
	 *
	 * @param companyBasicWork the company basic work
	 */
	void insert(CompanyBasicWork companyBasicWork);
	
	/**
	 * Update.
	 *
	 * @param companyBasicWork the company basic work
	 */
	void update(CompanyBasicWork companyBasicWork);

	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	Optional<CompanyBasicWork> findAll(String companyId);
}
