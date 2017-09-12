/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pub.jobtitle;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * The Interface JobtitlePub.
 */
public interface SyJobTitlePub {

	/**
	 * Find job title by sid.
	 *
	 * @param employeeId the employee id
	 * @return the list
	 */
	// RequestList #17
	List<JobTitleExport> findJobTitleBySid(String employeeId);

	/**
	 * Find job title by sid.
	 *
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the list
	 */
	// RequestList #33
	Optional<JobTitleExport> findJobTitleBySid(String employeeId, GeneralDate baseDate);

	/**
	 * Find job title by position id.
	 *
	 * @param companyId the company id
	 * @param positionId the position id
	 * @param baseDate the base date
	 * @return the list
	 */
	// RequestList #67-1
	Optional<JobTitleExport> findJobTitleByPositionId(String companyId, String positionId, GeneralDate baseDate);

	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @param referenceDate the reference date
	 * @return the list
	 */
	List<JobTitleExport> findAll(String companyId, GeneralDate referenceDate);

	/**
	 * Find by sid.
	 *
	 * @param companyId the company id
	 * @param employeeId the employee id
	 * @return the list
	 */
	List<JobTitleExport> findByJobIds(List<String> jobIds);

	/**
	 * Find by job ids.
	 *
	 * @param companyId the company id
	 * @param jobIds the job ids
	 * @param baseDate the base date
	 * @return the list
	 */
	List<JobTitleExport> findByJobIds(String companyId, List<String> jobIds,
			GeneralDate baseDate);
}
