/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
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
	
	/**
	 * Find by id.
	 *
	 * @param companyId the company id
	 * @param workdayAtr the workday atr
	 * @return the optional
	 */
	Optional<CompanyBasicWork> findById(String companyId, int workdayAtr);
}
