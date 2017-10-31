/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.employment;

import java.util.List;
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
	 * @param employmentCode the employment code
	 */
	void remove(String companyId, int year, String employmentCode);

	/**
	 * Find.
	 *
	 * @param companyId the company id
	 * @param year the year
	 * @param employmentCode the employment code
	 * @return the optional
	 */
	Optional<EmploymentWtSetting> find(String companyId, int year, String employmentCode);

	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @param year the year
	 * @return the list
	 */
	List<String> findAll(String companyId, int year);
}
