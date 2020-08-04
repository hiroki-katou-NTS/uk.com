/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew;

import java.util.List;
import java.util.Optional;

/**
 * The Interface EmpNormalSettingRepository.
 */
public interface ShainNormalSettingRepository {

	/**
	 * Find emp normal setting by cid and emp id and year.
	 *
	 * @param cid the cid
	 * @param empId the emp id
	 * @param year the year
	 * @return the optional
	 */
  Optional<ShainNormalSetting> find(String cid, String empId, int year);

	/**
	 * Adds the.
	 *
	 * @param empNormalSetting the emp normal setting
	 */
  void add(ShainNormalSetting empNormalSetting);

	/**
	 * Update.
	 *
	 * @param empNormalSetting the emp normal setting
	 */
  void update(ShainNormalSetting empNormalSetting);

	/**
   * Delete.
   *
   * @param empNormalSetting the emp normal setting
   */
  void delete(String cid, String empId, int year);
  

  /**
   * Find list.
   *
   * @param cid the cid
   * @param empId the emp id
   * @return the list
   */
  List<ShainNormalSetting> findList(String cid, String empId);
}
