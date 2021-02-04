package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.wkp;

import java.util.List;
import java.util.Optional;

/**
 * The Interface WkpCalSetMonthlyActualFlexRepository.
 */
public interface WkpFlexMonthActCalSetRepo {

	/**
	 * Find by cid and wkp id.
	 *
	 * @param cid the cid
	 * @param wkpId the wkp id
	 * @return the optional
	 */
	Optional<WkpFlexMonthActCalSet> find(String cid, String wkpId);
	
	/**
	 * Find by Cid
	 * @param cid
	 * @return
	 */
	List<WkpFlexMonthActCalSet> findByCid(String cid);

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
