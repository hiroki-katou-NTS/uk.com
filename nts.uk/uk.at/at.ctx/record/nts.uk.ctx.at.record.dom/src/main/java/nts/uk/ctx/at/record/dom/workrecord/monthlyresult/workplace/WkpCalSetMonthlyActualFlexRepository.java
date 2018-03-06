/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.workplace;

import java.util.Optional;

/**
 * The Interface WkpCalSetMonthlyActualFlexRepository.
 */
public interface WkpCalSetMonthlyActualFlexRepository {

	/**
	 * Find by cid and wkp id.
	 *
	 * @param cid the cid
	 * @param wkpId the wkp id
	 * @return the optional
	 */
	Optional<WkpCalSetMonthlyActualFlex> findByCidAndWkpId(String cid, String wkpId);

	/**
	 * Adds the.
	 *
	 * @param wkpCalSetMonthlyActualFlex the wkp cal set monthly actual flex
	 */
	void add(WkpCalSetMonthlyActualFlex wkpCalSetMonthlyActualFlex);

	/**
	 * Update.
	 *
	 * @param wkpCalSetMonthlyActualFlex the wkp cal set monthly actual flex
	 */
	void update(WkpCalSetMonthlyActualFlex wkpCalSetMonthlyActualFlex);

	/**
	 * Delete.
	 *
	 * @param wkpCalSetMonthlyActualFlex the wkp cal set monthly actual flex
	 */
	void delete(WkpCalSetMonthlyActualFlex wkpCalSetMonthlyActualFlex);

}
