/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime;

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
	 * Creates the.
	 *
	 * @param setting the setting
	 */
	void create(UsageUnitSetting setting);

	/**
	 * Find by company.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	Optional<UsageUnitSetting> findByCompany(String companyId);
}
