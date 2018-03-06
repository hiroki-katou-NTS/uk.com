/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.workplace;

import java.util.Optional;

/**
 * The Interface WkpRegularSetMonthlyActualWorkRepository.
 */
public interface WkpRegularSetMonthlyActualWorkRepository {

	/**
	 * Find by cid and wkp id.
	 *
	 * @param cid the cid
	 * @param wkpId the wkp id
	 * @return the optional
	 */
	Optional<WkpRegularSetMonthlyActualWork> findByCidAndWkpId(String cid, String wkpId);

	/**
	 * Adds the.
	 *
	 * @param wkpRegularSetMonthlyActualWork the wkp regular set monthly actual work
	 */
	void add(WkpRegularSetMonthlyActualWork wkpRegularSetMonthlyActualWork);

	/**
	 * Update.
	 *
	 * @param wkpRegularSetMonthlyActualWork the wkp regular set monthly actual work
	 */
	void update(WkpRegularSetMonthlyActualWork wkpRegularSetMonthlyActualWork);

	/**
	 * Delete.
	 *
	 * @param wkpRegularSetMonthlyActualWork the wkp regular set monthly actual work
	 */
	void delete(WkpRegularSetMonthlyActualWork wkpRegularSetMonthlyActualWork);

}
