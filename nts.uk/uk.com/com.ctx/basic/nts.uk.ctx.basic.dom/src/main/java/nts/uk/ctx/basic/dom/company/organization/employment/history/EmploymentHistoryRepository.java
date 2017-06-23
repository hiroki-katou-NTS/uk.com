/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.employment.history;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * The Interface EmploymentHistoryRepository.
 */
public interface EmploymentHistoryRepository {

	/**
	 * Search employee.
	 *
	 * @param baseDate the base date
	 * @param employmentCodes the employment codes
	 * @return the list
	 */
	List<EmploymentHistory> searchEmployee(GeneralDate baseDate, List<String> employmentCodes );
	
	
	/**
	 * Search employee.
	 *
	 * @param employeeIds the employee ids
	 * @param baseDate the base date
	 * @param employmentCodes the employment codes
	 * @return the list
	 */
	List<EmploymentHistory> searchEmployee(List<String> employeeIds, 
			GeneralDate baseDate, List<String> employmentCodes );
	
	
	

}
