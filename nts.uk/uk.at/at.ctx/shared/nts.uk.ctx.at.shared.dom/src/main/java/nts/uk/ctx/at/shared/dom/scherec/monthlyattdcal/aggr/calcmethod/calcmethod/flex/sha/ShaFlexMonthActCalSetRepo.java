package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.sha;

import java.util.List;
import java.util.Optional;

/**
 * The Interface EmployeeCalSetMonthlyFlexRepository.
 */
public interface ShaFlexMonthActCalSetRepo {

	/**
	 * Find employee cal set monthly flex by cid and emp id.
	 *
	 * @param cid the cid
	 * @param sId the emp id
	 * @return the optional
	 */
  Optional<ShaFlexMonthActCalSet> find(String cid, String sId);
  
	  /**
	   * Find By Cid
	   * @param cid
	   * @return
	   */
  List<ShaFlexMonthActCalSet> findAllShaByCid(String cid);

	/**
	 * Adds the.
	 *
	 * @param empCalSetMonthlyFlex the emp cal set monthly flex
	 */
  void add(ShaFlexMonthActCalSet empCalSetMonthlyFlex);

	/**
	 * Update.
	 *
	 * @param empCalSetMonthlyFlex the emp cal set monthly flex
	 */
  void update(ShaFlexMonthActCalSet empCalSetMonthlyFlex);

	/**
   * Delete.
   *
   * @param empCalSetMonthlyFlex the emp cal set monthly flex
   */
  void remove(String cid, String sId);
}
