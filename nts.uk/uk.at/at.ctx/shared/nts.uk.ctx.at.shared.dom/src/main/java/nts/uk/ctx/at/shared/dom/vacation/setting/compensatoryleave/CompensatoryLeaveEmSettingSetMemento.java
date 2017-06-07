/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

/**
 * The Interface CompensatoryLeaveEmSettingSetMemento.
 */
public interface CompensatoryLeaveEmSettingSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	public void setCompanyId(String companyId);

	/**
     * Sets the employment code.
     *
     * @param employmentCode the new employment code
     */
    public void setEmploymentCode(EmploymentCode employmentCode);

	/**
     * Sets the employment manage setting.
     *
     * @param employmentManageSetting the new employment manage setting
     */
    public void setEmploymentManageSetting(EmploymentCompensatoryManageSetting employmentManageSetting);

	/**
     * Sets the employment time manage setting.
     *
     * @param employmentTimeManageSetting the new employment time manage setting
     */
    public void setEmploymentTimeManageSetting(EmploymentCompensatoryTimeManageSetting employmentTimeManageSetting);
}
