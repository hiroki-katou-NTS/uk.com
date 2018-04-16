/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew;

import java.util.Optional;

/**
 * The Interface ComNormalSettingRepository.
 */
public interface ComNormalSettingRepository {

	/**
	 * Creates the.
	 *
	 * @param setting the setting
	 */
	void create(ComNormalSetting setting);

	/**
	 * Update.
	 *
	 * @param setting the setting
	 */
	void update(ComNormalSetting setting);

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
	 * @return the optional
	 */
	Optional<ComNormalSetting> find(String companyId, int year);

}
