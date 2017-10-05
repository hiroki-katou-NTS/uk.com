/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.jobtitle.info;

import java.util.List;

/**
 * The Interface SequenceMasterRepository.
 */
public interface SequenceMasterRepository {

	/**
	 * Find by company id.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<SequenceMaster> findByCompanyId(String companyId);
	
	/**
	 * Adds the.
	 *
	 * @param domain the domain
	 */
	void add(SequenceMaster domain);
	
	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	void update(SequenceMaster domain);
	
	/**
	 * Removes the.
	 *
	 * @param companyId the company id
	 * @param sequenceCode the sequence code
	 */
	void remove(String companyId, String sequenceCode);
	
}
