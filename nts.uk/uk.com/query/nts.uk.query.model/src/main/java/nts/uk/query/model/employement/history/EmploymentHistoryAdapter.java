/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.model.employement.history;

import java.util.List;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Interface EmploymentHistoryAdapter.
 */
public interface EmploymentHistoryAdapter {

	/**
	 * Find S ids by emp cds and period.
	 *
	 * @param empCds the emp cds
	 * @param period the period
	 * @return the list
	 */
	// 雇用（List）と期間から雇用履歴項目を取得する
	List<String> findSIdsByEmpCdsAndPeriod(List<String> empCds, DatePeriod period);
}
