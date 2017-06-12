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
	public void add(ClosureHistory closureHistory);
	
	/**
	 * Update.
	 *
	 * @param closureHistory the closure history
	 */
	public void update(ClosureHistory closureHistory);
	
	
	/**
	 * Find by closure id.
	 *
	 * @param companyId the company id
	 * @param closureId the closure id
	 * @return the list
	 */
	public List<ClosureHistory> findByClosureId(String companyId, int closureId);
	
	
	/**
	 * Find by company id.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	public List<ClosureHistory> findByCompanyId(String companyId);
	
	/**
	 * Find by company id.
	 *
	 * @param companyId the company id
	 * @param closureId the closure id
	 * @param historyId the history id
	 * @return the list
	 */
	public Optional<ClosureHistory> findByHistoryId(String companyId, int closureId, String historyId);
	
	
	
	/**
	 * Find by last history.
	 *
	 * @param companyId the company id
	 * @param closureId the closure id
	 * @return the optional
	 */
	public Optional<ClosureHistory> findByLastHistory(String companyId, int closureId);
}
