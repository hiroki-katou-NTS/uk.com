/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.jobtitle;

import java.util.List;

/**
 * The Interface SequenceMasterRepository.
 */
public interface SequenceMasterRepository {

	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @param sequenceCode the sequence code
	 * @return the list
	 */
	List<SequenceMaster> findAll(String companyId, String sequenceCode);
}
