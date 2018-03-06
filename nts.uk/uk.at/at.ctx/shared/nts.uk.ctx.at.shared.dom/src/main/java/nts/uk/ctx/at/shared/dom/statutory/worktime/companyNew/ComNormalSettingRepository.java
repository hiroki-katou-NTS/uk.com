/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.Year;

/**
 * The Interface ComNormalSettingRepository.
 */
public interface ComNormalSettingRepository {

	/**
	 * Find com normal setting by cid and year.
	 *
	 * @param companyId the company id
	 * @param year the year
	 * @return the optional
	 */
	Optional<ComNormalSetting> findComNormalSettingByCidAndYear(String companyId, Year year);

	/**
	 * Adds the.
	 *
	 * @param comNormalSetting the com normal setting
	 */
	void add(ComNormalSetting comNormalSetting);

	/**
	 * Update.
	 *
	 * @param comNormalSetting the com normal setting
	 */
	void update(ComNormalSetting comNormalSetting);

}
