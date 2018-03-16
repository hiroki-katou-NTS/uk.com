/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employee;

import java.util.Optional;

/**
 * The Interface EmployeeRegularSetMonthlyActualRepository.
 */
public interface ShainRegulaMonthActCalSetRepository {

	/**
	 * Find emp reg set monthly actual by cid and emp id.
	 *
	 * @param cid the cid
	 * @param empId the emp id
	 * @return the optional
	 */
	Optional<ShainRegulaMonthActCalSet> findEmpRegSetMonthlyActualByCidAndEmpId(String cid, String empId);

	/**
	 * Adds the.
	 *
	 * @param empRegSetMonthlyActual the emp reg set monthly actual
	 */
	void add(ShainRegulaMonthActCalSet empRegSetMonthlyActual);

	/**
	 * Update.
	 *
	 * @param empRegSetMonthlyActual the emp reg set monthly actual
	 */
	void update(ShainRegulaMonthActCalSet empRegSetMonthlyActual);

	/**
	 * Delete.
	 *
	 * @param empRegSetMonthlyActual the emp reg set monthly actual
	 */
	void delete(ShainRegulaMonthActCalSet empRegSetMonthlyActual);

}
