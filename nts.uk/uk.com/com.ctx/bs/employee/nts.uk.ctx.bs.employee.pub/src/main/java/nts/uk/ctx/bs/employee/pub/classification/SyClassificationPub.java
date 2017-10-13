/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pub.classification;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * The Interface WorkplacePub.
 */
public interface SyClassificationPub {

	/**
	 * Find S job hist by sid.
	 *
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the optional
	 */
	// RequestList #32
	Optional<SClsHistExport> findSClsHistBySid(String companyId, String employeeId, GeneralDate baseDate);

}
