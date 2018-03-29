/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew;

import java.util.Optional;

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
	Optional<ShainDeforLaborSetting> find(String cid, String empId, int year);

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
	void delete(String cid, String empId, int year);

}
