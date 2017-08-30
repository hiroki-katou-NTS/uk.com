/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.jobtitle;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * The Interface JobTitleRepository.
 */
public interface JobTitleRepository {

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	List<JobTitle> findAll(String companyId, GeneralDate referenceDate);

	/**
	 * Find by job ids.
	 *
	 * @param jobIds the job ids
	 * @return the list
	 */
	List<JobTitle> findByJobIds(List<String> jobIds);
}
