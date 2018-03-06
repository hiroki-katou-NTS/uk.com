/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.Year;

/**
 * The Interface ComFlexSettingRepository.
 */
public interface ComFlexSettingRepository {

	/**
	 * Find com flex setting by cid.
	 *
	 * @param companyId the company id
	 * @param year the year
	 * @return the optional
	 */
	Optional<ComFlexSetting> findComFlexSettingByCid(String companyId, Year year);

	/**
	 * Adds the.
	 *
	 * @param comFlexSetting the com flex setting
	 */
	void add(ComFlexSetting comFlexSetting);

	/**
	 * Update.
	 *
	 * @param comFlexSetting the com flex setting
	 */
	void update(ComFlexSetting comFlexSetting);
}
