/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.Year;

/**
 * The Interface EmpFlexSettingRepository.
 */
public interface EmpFlexSettingRepository {

	/**
	 * Find emp flex setting by cid and emp id and year.
	 *
	 * @param cid the cid
	 * @param empId the emp id
	 * @param year the year
	 * @return the optional
	 */
	Optional<EmpFlexSetting> findEmpFlexSettingByCidAndEmpIdAndYear(String cid, String empId, Year year);

	/**
	 * Adds the.
	 *
	 * @param empFlexSetting the emp flex setting
	 */
	void add(EmpFlexSetting empFlexSetting);

	/**
	 * Update.
	 *
	 * @param empFlexSetting the emp flex setting
	 */
	void update(EmpFlexSetting empFlexSetting);

	/**
	 * Delete.
	 *
	 * @param empFlexSetting the emp flex setting
	 */
	void delete(EmpFlexSetting empFlexSetting);

}
