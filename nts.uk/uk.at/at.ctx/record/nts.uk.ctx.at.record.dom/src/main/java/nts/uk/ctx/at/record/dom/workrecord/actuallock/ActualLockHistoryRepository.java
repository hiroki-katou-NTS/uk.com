/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.actuallock;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDateTime;

/**
 * The Interface ActualLockHistoryRepository.
 */
public interface ActualLockHistoryRepository {

	/**
	 * Adds the.
	 *
	 * @param actualLockHistory the actual lock history
	 */
	void add(ActualLockHistory actualLockHistory);
	
	/**
	 * Update.
	 *
	 * @param actualLockHistory the actual lock history
	 */
	void update(ActualLockHistory actualLockHistory);
	
	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<ActualLockHistory> findAll(String companyId);
	
	/**
	 * Find by target month.
	 *
	 * @param companyId the company id
	 * @param closureId the closure id
	 * @param targetMonth the target month
	 * @return the list
	 */
	List<ActualLockHistory> findByTargetMonth(String companyId, int closureId, int targetMonth);
	/**
	 * Find by closure id.
	 *
	 * @param companyId the company id
	 * @param closureId the closure id
	 * @return the list
	 */
	List<ActualLockHistory> findByClosureId(String companyId, int closureId);
	
	/**
	 * Find by lock date.
	 *
	 * @param companyId the company id
	 * @param closureId the closure id
	 * @param lockDate the lock date
	 * @return the optional
	 */
	Optional<ActualLockHistory> findByLockDate(String companyId, int closureId, GeneralDateTime lockDate);
	
	/**
	 * Removes the.
	 *
	 * @param companyId the company id
	 * @param closureId the closure id
	 * @param lockDate the lock date
	 */
	void remove(String companyId, int closureId, GeneralDateTime lockDate);
}
