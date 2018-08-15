/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pub.classification;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

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
	// RequestList32
	Optional<SClsHistExport> findSClsHistBySid(String companyId, String employeeId, GeneralDate baseDate);

	/**
	 * Find S cls hist by sid.
	 *
	 * @param companyId the company id
	 * @param employeeId the employee id
	 * @param datePeriod the date period
	 * @return the optional
	 */
	// RequestList32-3
	List<SClsHistExport> findSClsHistBySid(String companyId, List<String> employeeIds, DatePeriod datePeriod);
}
