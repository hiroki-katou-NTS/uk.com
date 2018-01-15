/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.holidaysetting.configuration;

/**
 * The Interface PublicHolidaySettingSetMemento.
 */
public interface PublicHolidaySettingSetMemento {
	
	/**
	 * Sets the company ID.
	 *
	 * @param CompanyID the new company ID
	 */
	void setCompanyID(String CompanyID);

	/**
	 * Sets the checks if is manage com public hd.
	 *
	 * @param isManageComPublicHd the new checks if is manage com public hd
	 */
	void setIsManageComPublicHd(boolean isManageComPublicHd);

	/**
	 * Sets the public hd management classification.
	 *
	 * @param publicHdManagementClassification the new public hd management classification
	 */
	void setPublicHdManagementClassification(PublicHolidayManagementClassification publicHdManagementClassification);
	
	void setPublicHdManagementUsageUnit(PublicHolidayManagementUsageUnit publicHdManagementUsageUnit);
	
	void setIsWeeklyHdCheck(boolean isWeeklyHdCheck);
}
