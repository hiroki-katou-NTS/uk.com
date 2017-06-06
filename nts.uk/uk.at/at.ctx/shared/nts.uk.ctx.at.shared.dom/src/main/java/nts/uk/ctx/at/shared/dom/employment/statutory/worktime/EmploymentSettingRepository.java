/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.employment.statutory.worktime;

/**
 * The Interface CompanySettingRepository.
 */
public interface EmploymentSettingRepository {

	/**
	 * Creates the.
	 *
	 * @param setting the setting
	 */
	void create(EmploymentSetting setting);

	/**
	 * Update.
	 *
	 * @param setting the setting
	 */
	void update(EmploymentSetting setting);

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
	 * @return the employment setting
	 */
	EmploymentSetting find(String companyId);
}
