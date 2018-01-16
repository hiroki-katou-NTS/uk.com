/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pub.employment;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * The Interface WorkplacePub.
 */
public interface SyEmploymentPub {

	/**
	 * Find S job hist by sid.
	 *
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the optional
	 */
	// RequestList #31
	Optional<SEmpHistExport> findSEmpHistBySid(String companyId, String employeeId, GeneralDate baseDate);

	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	// RequestList #89
	List<EmpCdNameExport> findAll(String companyId);
	
	/**
	 * Find by emp codes.
	 *
	 * @param companyId the company id
	 * @param empCodes the emp codes
	 * @return the list
	 */
	List<ShEmploymentExport> findByEmpCodes(String companyId, List<String> empCodes);
}
