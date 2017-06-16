/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment;

/**
 * The Interface CompanySettingRepository.
 */
public interface EmploymentWtSettingRepository {

	/**
	 * Creates the.
	 *
	 * @param setting the setting
	 */
	void create(EmploymentWtSetting setting);

	/**
	 * Update.
	 *
	 * @param setting the setting
	 */
	void update(EmploymentWtSetting setting);

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
	EmploymentWtSetting find(String companyId);
}
