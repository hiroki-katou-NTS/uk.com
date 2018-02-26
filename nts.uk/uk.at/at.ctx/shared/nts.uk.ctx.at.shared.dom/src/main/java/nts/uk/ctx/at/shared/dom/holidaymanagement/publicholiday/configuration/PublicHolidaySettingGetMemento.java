/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration;

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
	 * Gets the checks if is weekly hd check.
	 *
	 * @return the checks if is weekly hd check
	 */
	boolean getIsWeeklyHdCheck();
	
	/**
	 * Gets the public holiday management start date.
	 *
	 * @return the public holiday management start date
	 */
	PublicHolidayManagementStartDate getPublicHolidayManagementStartDate(Integer publicHdManageAtr);
}
