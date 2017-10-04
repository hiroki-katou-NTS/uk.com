/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.actuallock;

import java.util.List;
import java.util.Optional;

/**
 * The Interface ActualLockRepository.
 */
public interface ActualLockRepository {

	/**
	 * Adds the.
	 *
	 * @param actualLock the actual lock
	 */
	void add(ActualLock actualLock);
	
	/**
	 * Update.
	 *
	 * @param actualLock the actual lock
	 */
	void update(ActualLock actualLock);
	
	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<ActualLock> findAll(String companyId);
	
	/**
	 * Find by id.
	 *
	 * @param companyId the company id
	 * @param closureId the closure id
	 * @return the optional
	 */
	Optional<ActualLock> findById(String companyId, int closureId);
	
	/**
	 * Removes the.
	 *
	 * @param companyId the company id
	 * @param closureId the closure id
	 */
	void remove(String companyId, int closureId);
}
