/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.workplace;

import java.util.Optional;

/**
 * The Interface WkpTransLaborSetMonthlyRepository.
 */
public interface WkpTransLaborSetMonthlyRepository {

	/**
	 * Find by cid and wkpid.
	 *
	 * @param cid the cid
	 * @param wkpId the wkp id
	 * @return the optional
	 */
	Optional<WkpTransLaborSetMonthly> findByCidAndWkpid(String cid, String wkpId);

	/**
	 * Adds the.
	 *
	 * @param wkpTransLaborSetMonthly the wkp trans labor set monthly
	 */
	void add(WkpTransLaborSetMonthly wkpTransLaborSetMonthly);

	/**
	 * Update.
	 *
	 * @param wkpTransLaborSetMonthly the wkp trans labor set monthly
	 */
	void update(WkpTransLaborSetMonthly wkpTransLaborSetMonthly);

	/**
	 * Delete.
	 *
	 * @param wkpTransLaborSetMonthly the wkp trans labor set monthly
	 */
	void delete(WkpTransLaborSetMonthly wkpTransLaborSetMonthly);

}
