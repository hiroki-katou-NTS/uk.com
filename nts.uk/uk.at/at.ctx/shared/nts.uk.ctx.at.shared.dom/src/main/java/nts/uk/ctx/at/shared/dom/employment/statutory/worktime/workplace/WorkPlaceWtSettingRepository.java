/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.employment.statutory.worktime.workplace;

/**
 * The Interface CompanySettingRepository.
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
	 */
	void remove(String companyId);

	/**
	 * Find.
	 *
	 * @param companyId the company id
	 * @return the work place setting
	 */
	WorkPlaceWtSetting find(String companyId);
}
