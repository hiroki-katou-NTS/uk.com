/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.employment.statutory.worktime;

/**
 * The Interface CompanySettingRepository.
 */
public interface CompanySettingRepository {

	/**
	 * Creates the.
	 *
	 * @param setting the setting
	 */
	void create(CompanySetting setting);

	/**
	 * Update.
	 *
	 * @param setting the setting
	 */
	void update(CompanySetting setting);

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
	 * @return the company setting
	 */
	CompanySetting find(String companyId);
}
