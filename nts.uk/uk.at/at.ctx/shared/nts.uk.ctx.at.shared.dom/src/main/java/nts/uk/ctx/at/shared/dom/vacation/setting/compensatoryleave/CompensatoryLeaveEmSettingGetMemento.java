/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

/**
 * The Interface CompensatoryLeaveEmSettingGetMemento.
 */
public interface CompensatoryLeaveEmSettingGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	String getCompanyId();

	/**
	 * Gets the employment code.
	 *
	 * @return the employment code
	 */
	EmploymentCode getEmploymentCode();

	/**
	 * Gets the employment manage setting.
	 *
	 * @return the employment manage setting
	 */
	EmploymentCompensatoryManageSetting getEmploymentManageSetting();

	/**
	 * Gets the employment time manage setting.
	 *
	 * @return the employment time manage setting
	 */
	EmploymentCompensatoryTimeManageSetting getEmploymentTimeManageSetting();

}
