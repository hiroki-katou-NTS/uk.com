/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.model.employee.history;

import java.util.List;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Interface EmployeeHistoryRepository.
 */
public interface EmployeeHistoryRepository {

	/**
	 * Find employee by entry date.
	 *
	 * @param comId the com id
	 * @param period the period
	 * @return the list
	 */
	List<String> findEmployeeByEntryDate(String comId, DatePeriod period);

	/**
	 * Find employee by retirement date.
	 *
	 * @param comId the com id
	 * @param period the period
	 * @return the list
	 */
	List<String> findEmployeeByRetirementDate(String comId, DatePeriod period);
}
