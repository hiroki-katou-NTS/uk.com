/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employee;

import java.util.Optional;

/**
 * The Interface EmployeeCalSetMonthlyFlexRepository.
 */
public interface EmployeeCalSetMonthlyFlexRepository {

	/**
	 * Find employee cal set monthly flex by cid and emp id.
	 *
	 * @param cid the cid
	 * @param empId the emp id
	 * @return the optional
	 */
  Optional<EmployeeCalSetMonthlyFlex> findEmployeeCalSetMonthlyFlexByCidAndEmpId(String cid, String empId);

	/**
	 * Adds the.
	 *
	 * @param empCalSetMonthlyFlex the emp cal set monthly flex
	 */
  void add(EmployeeCalSetMonthlyFlex empCalSetMonthlyFlex);

	/**
	 * Update.
	 *
	 * @param empCalSetMonthlyFlex the emp cal set monthly flex
	 */
  void update(EmployeeCalSetMonthlyFlex empCalSetMonthlyFlex);

	/**
   * Delete.
   *
   * @param empCalSetMonthlyFlex the emp cal set monthly flex
   */
  void delete(EmployeeCalSetMonthlyFlex empCalSetMonthlyFlex);
}
