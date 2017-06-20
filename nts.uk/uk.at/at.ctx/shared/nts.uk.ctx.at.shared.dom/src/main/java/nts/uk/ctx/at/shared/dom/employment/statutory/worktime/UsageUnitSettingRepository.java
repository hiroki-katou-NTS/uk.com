/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.employment.statutory.worktime;

import java.util.Optional;

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
	 * Find by company.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	Optional<UsageUnitSetting> findByCompany(String companyId);
}
