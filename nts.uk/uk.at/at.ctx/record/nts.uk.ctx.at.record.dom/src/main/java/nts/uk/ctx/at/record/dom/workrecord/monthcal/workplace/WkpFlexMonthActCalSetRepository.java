/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace;

import java.util.Optional;

/**
 * The Interface WkpCalSetMonthlyActualFlexRepository.
 */
public interface WkpFlexMonthActCalSetRepository {

	/**
	 * Find by cid and wkp id.
	 *
	 * @param cid the cid
	 * @param wkpId the wkp id
	 * @return the optional
	 */
	Optional<WkpFlexMonthActCalSet> find(String cid, String wkpId);

	/**
	 * Adds the.
	 *
	 * @param wkpCalSetMonthlyActualFlex the wkp cal set monthly actual flex
	 */
	void add(WkpFlexMonthActCalSet wkpCalSetMonthlyActualFlex);

	/**
	 * Update.
	 *
	 * @param wkpCalSetMonthlyActualFlex the wkp cal set monthly actual flex
	 */
	void update(WkpFlexMonthActCalSet wkpCalSetMonthlyActualFlex);

	/**
	 * Delete.
	 *
	 * @param wkpCalSetMonthlyActualFlex the wkp cal set monthly actual flex
	 */
	void remove(String cid, String wkpId);

}
