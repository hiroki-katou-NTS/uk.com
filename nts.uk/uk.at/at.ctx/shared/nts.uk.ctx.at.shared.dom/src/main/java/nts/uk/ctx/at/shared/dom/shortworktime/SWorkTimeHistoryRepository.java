/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.shortworktime;

import java.util.Optional;

/**
 * The Interface SWorkTimeHistoryRepository.
 */
public interface SWorkTimeHistoryRepository {

	/**
	 * Adds the.
	 *
	 * @param domain the domain
	 */
	void add(ShortWorkTimeHistory domain);
	
	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	void update(ShortWorkTimeHistory domain);
	
	/**
	 * Find by key.
	 *
	 * @param empId the emp id
	 * @param histId the hist id
	 * @return the optional
	 */
	Optional<ShortWorkTimeHistory> findByKey(String empId, String histId);
}
