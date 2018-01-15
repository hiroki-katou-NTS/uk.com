/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.employment.affiliate;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * The Interface EmploymentHistoryRepository.
 */
public interface AffEmploymentHistoryRepository {

	/**
	 * Search employee.
	 *
	 * @param baseDate the base date
	 * @param employmentCodes the employment codes
	 * @return the list
	 */
	List<AffEmploymentHistory> searchEmployee(GeneralDate baseDate, List<String> employmentCodes );

	/**
	 * Search employee.
	 *
	 * @param employeeIds the employee ids
	 * @param baseDate the base date
	 * @param employmentCodes the employment codes
	 * @return the list
	 */
	List<AffEmploymentHistory> searchEmployee(List<String> employeeIds, 
			GeneralDate baseDate, List<String> employmentCodes );

	/**
	 * Search employment of sids.
	 *
	 * @param employeeIds the employee ids
	 * @param baseDate the base date
	 * @return the list
	 */
	List<AffEmploymentHistory> searchEmploymentOfSids(List<String> employeeIds,
			GeneralDate baseDate);

}
