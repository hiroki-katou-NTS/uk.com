/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.jobtitle_old;

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

	/**
	 * Find by job ids.
	 *
	 * @param companyId the company id
	 * @param jobIds the job ids
	 * @param baseDate the base date
	 * @return the list
	 */
	List<JobTitle> findByJobIds(String companyId, List<String> jobIds,
			GeneralDate baseDate);
}
