/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pub.employee.jobtitle;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * The Interface EmployeePub.
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
	List<JobTitleExport> findJobTitleBySid(String employeeId, GeneralDate baseDate);

	/**
	 * Find job title by position id.
	 *
	 * @param companyId the company id
	 * @param positionId the position id
	 * @param baseDate the base date
	 * @return the list
	 */
	// RequestList #67-1
	List<JobTitleExport> findJobTitleByPositionId(String companyId, String positionId, GeneralDate baseDate);
}
