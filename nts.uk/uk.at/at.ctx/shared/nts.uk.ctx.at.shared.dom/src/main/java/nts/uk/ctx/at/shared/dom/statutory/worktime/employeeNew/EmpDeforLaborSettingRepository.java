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
public interface EmpDeforLaborSettingRepository {

	/**
	 * Find emp defor labor setting by cid and emp id and year.
	 *
	 * @param cid the cid
	 * @param empId the emp id
	 * @param year the year
	 * @return the optional
	 */
	Optional<EmpDeforLaborSetting> findEmpDeforLaborSettingByCidAndEmpIdAndYear(String cid, String empId, Year year);

	/**
	 * Adds the.
	 *
	 * @param empDeforLaborSetting the emp defor labor setting
	 */
	void add(EmpDeforLaborSetting empDeforLaborSetting);

	/**
	 * Update.
	 *
	 * @param empDeforLaborSetting the emp defor labor setting
	 */
	void update(EmpDeforLaborSetting empDeforLaborSetting);

	/**
	 * Delete.
	 *
	 * @param empDeforLaborSetting the emp defor labor setting
	 */
	void delete(EmpDeforLaborSetting empDeforLaborSetting);

}
