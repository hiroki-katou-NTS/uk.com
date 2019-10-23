/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.shortworktime;

import java.util.List;
import java.util.Optional;

/**
 * The Interface SWorkTimeHistItemRepository.
 */
public interface SWorkTimeHistItemRepository {

	/**
	 * Adds the.
	 *
	 * @param domain the domain
	 */
	void add(ShortWorkTimeHistoryItem domain);
	
	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	void update(ShortWorkTimeHistoryItem domain);
	
	/**
	 * Find by key.
	 *
	 * @param empId the emp id
	 * @param histId the hist id
	 * @return the optional
	 */
	Optional<ShortWorkTimeHistoryItem> findByKey(String empId, String histId);
	
	void delete(String sid, String hist);
	
	/**
	 * Find by emp and period.
	 *
	 * @param empIdList the emp id list
	 * @param date the date
	 * @return the map
	 */
	List<ShortWorkTimeHistoryItem> findByHistIds(List<String> histIds);
}
