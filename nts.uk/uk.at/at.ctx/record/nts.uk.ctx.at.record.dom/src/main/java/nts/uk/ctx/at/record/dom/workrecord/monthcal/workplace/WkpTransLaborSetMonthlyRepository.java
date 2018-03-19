/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace;

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
	Optional<WkpDeforLaborMonthActCalSet> findByCidAndWkpid(String cid, String wkpId);

	/**
	 * Adds the.
	 *
	 * @param wkpTransLaborSetMonthly the wkp trans labor set monthly
	 */
	void add(WkpDeforLaborMonthActCalSet wkpTransLaborSetMonthly);

	/**
	 * Update.
	 *
	 * @param wkpTransLaborSetMonthly the wkp trans labor set monthly
	 */
	void update(WkpDeforLaborMonthActCalSet wkpTransLaborSetMonthly);

	/**
	 * Delete.
	 *
	 * @param wkpTransLaborSetMonthly the wkp trans labor set monthly
	 */
	void delete(WkpDeforLaborMonthActCalSet wkpTransLaborSetMonthly);

}
