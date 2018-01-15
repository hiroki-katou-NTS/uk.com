/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.company;

import java.util.Optional;

/**
 * The Interface CompanySettingRepository.
 */
public interface CompanyWtSettingRepository {

	/**
	 * Creates the.
	 *
	 * @param setting the setting
	 */
	void create(CompanyWtSetting setting);

	/**
	 * Update.
	 *
	 * @param setting the setting
	 */
	void update(CompanyWtSetting setting);

	/**
	 * Removes the.
	 *
	 * @param companyId the company id
	 */
	void remove(String companyId, int year);

	/**
	 * Find.
	 *
	 * @param companyId the company id
	 * @param year the year
	 * @return the optional
	 */
	Optional<CompanyWtSetting> find(String companyId, int year);
}
