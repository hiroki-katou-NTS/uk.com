/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew;

import java.util.Optional;

/**
 * The Interface ComFlexSettingRepository.
 */
public interface ComFlexSettingRepository {

	/**
	 * Creates the.
	 *
	 * @param setting the setting
	 */
	void create(ComFlexSetting setting);

	/**
	 * Update.
	 *
	 * @param setting the setting
	 */
	void update(ComFlexSetting setting);

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
	Optional<ComFlexSetting> find(String companyId, int year);
	
}
