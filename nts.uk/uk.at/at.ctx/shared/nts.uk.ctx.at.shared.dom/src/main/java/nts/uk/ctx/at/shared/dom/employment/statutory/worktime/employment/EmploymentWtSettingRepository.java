/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment;

import java.util.Optional;

/**
 * The Interface EmploymentWtSettingRepository.
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
	Optional<EmploymentWtSetting> find(String companyId, int year);
}
