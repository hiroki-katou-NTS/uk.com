/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.access.jobtitle;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.access.jobtitle.dto.JobTitleImport;

/**
 * The Interface PersonAdapter.
 */
public interface SyJobTitleAdapter {

	/**
	 * Find by person ids.
	 *
	 * @param personIds the person ids
	 * @return the list
	 */
	List<JobTitleImport> findAll(String companyId, GeneralDate referenceDate);

	/**
	 * Find by job ids.
	 *
	 * @param jobIds the job ids
	 * @return the list
	 */
	List<JobTitleImport> findByJobIds(List<String> jobIds);

	/**
	 * Find by job ids.
	 *
	 * @param companyId the company id
	 * @param jobIds the job ids
	 * @param baseDate the base date
	 * @return the list
	 */
	List<JobTitleImport> findByJobIds(String companyId, List<String> jobIds,
			GeneralDate baseDate);
}
