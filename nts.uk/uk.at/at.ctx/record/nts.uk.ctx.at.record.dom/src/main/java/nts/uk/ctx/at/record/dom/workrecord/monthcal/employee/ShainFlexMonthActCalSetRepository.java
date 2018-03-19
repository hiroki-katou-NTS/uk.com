/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal.employee;

import java.util.Optional;

/**
 * The Interface EmployeeCalSetMonthlyFlexRepository.
 */
public interface ShainFlexMonthActCalSetRepository {

	/**
	 * Find employee cal set monthly flex by cid and emp id.
	 *
	 * @param cid the cid
	 * @param empId the emp id
	 * @return the optional
	 */
  Optional<ShainFlexMonthActCalSet> findEmployeeCalSetMonthlyFlexByCidAndEmpId(String cid, String empId);

	/**
	 * Adds the.
	 *
	 * @param empCalSetMonthlyFlex the emp cal set monthly flex
	 */
  void add(ShainFlexMonthActCalSet empCalSetMonthlyFlex);

	/**
	 * Update.
	 *
	 * @param empCalSetMonthlyFlex the emp cal set monthly flex
	 */
  void update(ShainFlexMonthActCalSet empCalSetMonthlyFlex);

	/**
   * Delete.
   *
   * @param empCalSetMonthlyFlex the emp cal set monthly flex
   */
  void delete(ShainFlexMonthActCalSet empCalSetMonthlyFlex);
}
