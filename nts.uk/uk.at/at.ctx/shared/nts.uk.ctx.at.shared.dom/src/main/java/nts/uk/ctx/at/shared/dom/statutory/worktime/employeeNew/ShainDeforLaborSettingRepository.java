/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.Year;

/**
 * The Interface EmpDeforLaborSettingRepository.
 */
public interface ShainDeforLaborSettingRepository {

	/**
	 * Find emp defor labor setting by cid and emp id and year.
	 *
	 * @param cid the cid
	 * @param empId the emp id
	 * @param year the year
	 * @return the optional
	 */
	Optional<ShainDeforLaborSetting> findEmpDeforLaborSettingByCidAndEmpIdAndYear(String cid, String empId, Year year);

	/**
	 * Adds the.
	 *
	 * @param empDeforLaborSetting the emp defor labor setting
	 */
	void add(ShainDeforLaborSetting empDeforLaborSetting);

	/**
	 * Update.
	 *
	 * @param empDeforLaborSetting the emp defor labor setting
	 */
	void update(ShainDeforLaborSetting empDeforLaborSetting);

	/**
	 * Delete.
	 *
	 * @param empDeforLaborSetting the emp defor labor setting
	 */
	void delete(ShainDeforLaborSetting empDeforLaborSetting);

}
