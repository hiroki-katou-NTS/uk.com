/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pub.employee;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * The Interface EmployeePub.
 */
public interface SyEmployeePub {

	/**
	 * Find by wpk ids.
	 *
	 * @param companyId the company id
	 * @param workplaceIds the workplace ids
	 * @param baseDate the base date
	 * @return the list
	 */
	List<EmployeeExport> findByWpkIds(String companyId, List<String> workplaceIds, GeneralDate baseDate);

	/**
	 * Gets the concurrent employee.
	 *
	 * @param companyId the company id
	 * @param jobId the job id
	 * @param baseDate the base date
	 * @return the concurrent employee
	 */
	// RequestList #77
	List<ConcurrentEmployeeExport> getConcurrentEmployee(String companyId, String jobId, GeneralDate baseDate);
	
	/**
	 * Find by list id.
	 *
	 * @param companyId the company id
	 * @param empIdList the emp id list
	 * @return the list
	 */
	List<EmployeeExport> findByListId(String companyId, List<String> empIdList);
}
