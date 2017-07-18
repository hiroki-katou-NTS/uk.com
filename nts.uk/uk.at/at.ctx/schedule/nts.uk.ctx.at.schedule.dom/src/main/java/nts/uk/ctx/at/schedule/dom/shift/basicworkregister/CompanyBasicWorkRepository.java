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
	
	
//	void remove(String companyId, String workTypeCode);
	
	/**
	 * Find.
	 *
	 * @param companyId the company id
	 * @param workTypeCode the work type code
	 * @return the optional
	 */
	Optional<CompanyBasicWork> find(String companyId, String workTypeCode);

//	List<CompanyBasicWork> findAll(String CompanyId);
}
