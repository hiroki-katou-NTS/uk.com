/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.holidaysetting.configuration;

/**
 * The Interface PublicHolidaySettingGetMemento.
 */
public interface PublicHolidaySettingGetMemento {
	
	/**
	 * Gets the company ID.
	 *
	 * @return the company ID
	 */
	String getCompanyID();

	/**
	 * Gets the checks if is manage com public hd.
	 *
	 * @return the checks if is manage com public hd
	 */
	boolean getIsManageComPublicHd();

	/**
	 * Gets the public hd management classification.
	 *
	 * @return the public hd management classification
	 */
	PublicHolidayManagementClassification getPublicHdManagementClassification();
	
	/**
	 * Gets the public hd management usage unit.
	 *
	 * @return the public hd management usage unit
	 */
	PublicHolidayManagementUsageUnit getPublicHdManagementUsageUnit();
	
	boolean getIsWeeklyHdCheck();
}
