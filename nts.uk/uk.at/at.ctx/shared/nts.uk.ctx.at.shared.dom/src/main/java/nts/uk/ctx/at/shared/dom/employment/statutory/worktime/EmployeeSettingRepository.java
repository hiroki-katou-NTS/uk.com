/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.employment.statutory.worktime;

/**
 * The Interface EmployeeSettingRepository.
 */
public interface EmployeeSettingRepository {

	/**
	 * Creates the.
	 *
	 * @param setting the setting
	 */
	void create(EmployeeSetting setting);

	/**
	 * Update.
	 *
	 * @param setting the setting
	 */
	void update(EmployeeSetting setting);

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
	EmployeeSetting find(String companyId);
}
