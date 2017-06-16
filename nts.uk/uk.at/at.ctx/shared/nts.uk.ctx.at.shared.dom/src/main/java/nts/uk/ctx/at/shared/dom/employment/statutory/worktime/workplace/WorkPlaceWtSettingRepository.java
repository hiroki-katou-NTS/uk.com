/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.employment.statutory.worktime.workplace;

/**
 * The Interface WorkPlaceWtSettingRepository.
 */
public interface WorkPlaceWtSettingRepository {

	/**
	 * Creates the.
	 *
	 * @param setting the setting
	 */
	void create(WorkPlaceWtSetting setting);

	/**
	 * Update.
	 *
	 * @param setting the setting
	 */
	void update(WorkPlaceWtSetting setting);

	/**
	 * Removes the.
	 *
	 * @param companyId the company id
	 * @param year the year
	 */
	void remove(String companyId, int year);

	/**
	 * Find.
	 *
	 * @param companyId the company id
	 * @param year the year
	 * @return the work place wt setting
	 */
	WorkPlaceWtSetting find(String companyId, int year);
}
