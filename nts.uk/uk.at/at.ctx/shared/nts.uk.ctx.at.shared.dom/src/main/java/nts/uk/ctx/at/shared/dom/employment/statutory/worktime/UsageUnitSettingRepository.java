/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.employment.statutory.worktime;

/**
 * The Interface UsageUnitSettingRepository.
 */
public interface UsageUnitSettingRepository {

	/**
	 * Update.
	 *
	 * @param setting the setting
	 */
	void update(UsageUnitSetting setting);

	/**
	 * Find.
	 *
	 * @param companyId the company id
	 * @return the usage unit setting
	 */
	UsageUnitSetting find(String companyId);
}
