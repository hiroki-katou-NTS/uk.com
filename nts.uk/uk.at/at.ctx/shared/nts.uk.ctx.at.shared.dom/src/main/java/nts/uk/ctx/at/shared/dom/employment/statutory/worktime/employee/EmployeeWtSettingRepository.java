/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employee;

/**
 * The Interface EmployeeSettingRepository.
 */
public interface EmployeeWtSettingRepository {

	/**
	 * Creates the.
	 *
	 * @param setting the setting
	 */
	void create(EmployeeWtSetting setting);

	/**
	 * Update.
	 *
	 * @param setting the setting
	 */
	void update(EmployeeWtSetting setting);

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
	 * @return the employee setting
	 */
	EmployeeWtSetting find(String companyId);
}
