/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.Year;

/**
 * The Interface EmpNormalSettingRepository.
 */
public interface EmpNormalSettingRepository {

	/**
	 * Find emp normal setting by cid and emp id and year.
	 *
	 * @param cid the cid
	 * @param empId the emp id
	 * @param year the year
	 * @return the optional
	 */
  Optional<EmpNormalSetting> findEmpNormalSettingByCidAndEmpIdAndYear(String cid, String empId, Year year);

	/**
	 * Adds the.
	 *
	 * @param empNormalSetting the emp normal setting
	 */
  void add(EmpNormalSetting empNormalSetting);

	/**
	 * Update.
	 *
	 * @param empNormalSetting the emp normal setting
	 */
  void update(EmpNormalSetting empNormalSetting);

	/**
   * Delete.
   *
   * @param empNormalSetting the emp normal setting
   */
  void delete(EmpNormalSetting empNormalSetting);
}
