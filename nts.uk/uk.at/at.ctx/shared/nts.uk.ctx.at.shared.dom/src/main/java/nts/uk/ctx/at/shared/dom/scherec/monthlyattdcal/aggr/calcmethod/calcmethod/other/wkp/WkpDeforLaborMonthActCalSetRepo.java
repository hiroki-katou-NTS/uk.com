package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp;

import java.util.Optional;

/**
 * The Interface WkpTransLaborSetMonthlyRepository.
 */
public interface WkpDeforLaborMonthActCalSetRepo {

	/**
	 * Find by cid and wkpid.
	 *
	 * @param cid the cid
	 * @param wkpId the wkp id
	 * @return the optional
	 */
	Optional<WkpDeforLaborMonthActCalSet> find(String cid, String wkpId);

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
	void remove(String cid, String wkpId);

}
