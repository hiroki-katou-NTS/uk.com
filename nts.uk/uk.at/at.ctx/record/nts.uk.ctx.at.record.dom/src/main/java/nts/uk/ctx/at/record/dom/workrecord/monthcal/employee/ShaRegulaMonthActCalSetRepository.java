/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal.employee;

import java.util.Optional;

/**
 * The Interface EmployeeRegularSetMonthlyActualRepository.
 */
public interface ShaRegulaMonthActCalSetRepository {

	/**
	 * Find emp reg set monthly actual by cid and emp id.
	 *
	 * @param cid the cid
	 * @param empId the emp id
	 * @return the optional
	 */
	Optional<ShaRegulaMonthActCalSet> find(String cid, String sId);

	/**
	 * Adds the.
	 *
	 * @param empRegSetMonthlyActual the emp reg set monthly actual
	 */
	void add(ShaRegulaMonthActCalSet empRegSetMonthlyActual);

	/**
	 * Update.
	 *
	 * @param empRegSetMonthlyActual the emp reg set monthly actual
	 */
	void update(ShaRegulaMonthActCalSet empRegSetMonthlyActual);

	/**
	 * Delete.
	 *
	 * @param empRegSetMonthlyActual the emp reg set monthly actual
	 */
	void remove(String cid, String sId);

}
