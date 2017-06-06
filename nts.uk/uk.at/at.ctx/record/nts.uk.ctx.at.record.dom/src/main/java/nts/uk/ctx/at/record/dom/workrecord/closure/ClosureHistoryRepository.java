/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.closure;

import java.util.List;

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
	 * @param closureId the closure id
	 * @return the list
	 */
	public List<ClosureHistory> findByClosureId(ClosureId closureId);
}
