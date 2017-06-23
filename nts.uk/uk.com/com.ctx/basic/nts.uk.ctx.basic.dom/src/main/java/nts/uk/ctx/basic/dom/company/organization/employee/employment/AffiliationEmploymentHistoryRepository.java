/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.employee.employment;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * The Interface EmploymentHistoryRepository.
 */
public interface AffiliationEmploymentHistoryRepository {

	/**
	 * Search employee.
	 *
	 * @param baseDate the base date
	 * @param employmentCodes the employment codes
	 * @return the list
	 */
	List<AffiliationEmploymentHistory> searchEmployee(GeneralDate baseDate, List<String> employmentCodes );
	
	
	/**
	 * Search employee.
	 *
	 * @param employeeIds the employee ids
	 * @param baseDate the base date
	 * @param employmentCodes the employment codes
	 * @return the list
	 */
	List<AffiliationEmploymentHistory> searchEmployee(List<String> employeeIds, 
			GeneralDate baseDate, List<String> employmentCodes );
	
	
	

}
