package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp;

import java.util.Optional;

/**
 * The Interface WkpRegularSetMonthlyActualWorkRepository.
 */
public interface WkpRegulaMonthActCalSetRepo {

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
