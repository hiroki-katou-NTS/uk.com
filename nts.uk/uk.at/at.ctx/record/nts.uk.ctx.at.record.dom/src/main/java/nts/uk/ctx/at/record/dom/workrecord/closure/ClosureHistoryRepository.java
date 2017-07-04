/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.closure;

import java.util.List;
import java.util.Optional;

/**
 * The Interface ClosureHistoryRepository.
 */
public interface ClosureHistoryRepository {

	/**
	 * Adds the.
	 *
	 * @param closureHistory the closure history
	 */
	void add(ClosureHistory closureHistory);
	
	/**
	 * Update.
	 *
	 * @param closureHistory the closure history
	 */
	void update(ClosureHistory closureHistory);
	
	
	/**
	 * Find by closure id.
	 *
	 * @param companyId the company id
	 * @param closureId the closure id
	 * @return the list
	 */
	List<ClosureHistory> findByClosureId(String companyId, int closureId);
	
	
	/**
	 * Find by company id.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<ClosureHistory> findByCompanyId(String companyId);
	
	/**
	 * Find by company id.
	 *
	 * @param companyId the company id
	 * @param closureId the closure id
	 * @param historyId the history id
	 * @return the list
	 */
	Optional<ClosureHistory> findById(String companyId, int closureId, 
			String historyId);
	
	
	/**
	 * Find by selected year month.
	 *
	 * @param companyId the company id
	 * @param closureId the closure id
	 * @param yearMonth the year month
	 * @return the optional
	 */
	Optional<ClosureHistory> findBySelectedYearMonth(String companyId, int closureId, 
			int yearMonth);
	
	
	/**
	 * Find by history last.
	 *
	 * @param companyId the company id
	 * @param closureId the closure id
	 * @return the optional
	 */
	Optional<ClosureHistory> findByHistoryLast(String companyId, int closureId);
}
