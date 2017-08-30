/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.company.pub.jobtitle;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * The Interface JobtitlePub.
 */
public interface JobtitlePub {

	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @param referenceDate the reference date
	 * @return the list
	 */
	List<PubJobtitleDto> findAll(String companyId, GeneralDate referenceDate);

	/**
	 * Find by sid.
	 *
	 * @param companyId the company id
	 * @param employeeId the employee id
	 * @return the list
	 */
	List<PubJobtitleDto> findByJobIds(List<String> jobIds);
}
