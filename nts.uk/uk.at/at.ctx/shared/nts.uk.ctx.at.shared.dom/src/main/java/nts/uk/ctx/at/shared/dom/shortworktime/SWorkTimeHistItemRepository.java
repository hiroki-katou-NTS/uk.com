/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.shortworktime;

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
}
