/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace;

import java.util.Optional;

/**
 * The Interface WkpRegularSetMonthlyActualWorkRepository.
 */
public interface WkpRegulaMonthActCalSetRepository {

	/**
	 * Find by cid and wkp id.
	 *
	 * @param cid the cid
	 * @param wkpId the wkp id
	 * @return the optional
	 */
	Optional<WkpRegulaMonthActCalSet> find(String cid, String wkpId);

	/**
	 * Adds the.
	 *
	 * @param wkpRegularSetMonthlyActualWork the wkp regular set monthly actual work
	 */
	void add(WkpRegulaMonthActCalSet wkpRegularSetMonthlyActualWork);

	/**
	 * Update.
	 *
	 * @param wkpRegularSetMonthlyActualWork the wkp regular set monthly actual work
	 */
	void update(WkpRegulaMonthActCalSet wkpRegularSetMonthlyActualWork);

	/**
	 * Delete.
	 *
	 * @param wkpRegularSetMonthlyActualWork the wkp regular set monthly actual work
	 */
	void remove(String cid, String wkpId);

}
