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
	 * @param period the period
	 * @return the list
	 */
	List<String> findEmployeeByEntryDate(DatePeriod period);

	/**
	 * Find employee by retirement date.
	 *
	 * @param period the period
	 * @return the list
	 */
	List<String> findEmployeeByRetirementDate(DatePeriod period);
}
