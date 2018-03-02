/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration;

/**
 * The Interface ForwardSettingOfPublicHolidaySetMemento.
 */
public interface ForwardSettingOfPublicHolidaySetMemento {
	
	/**
	 * Sets the company ID.
	 *
	 * @param companyID the new company ID
	 */
	void setCompanyID(String companyID);
	
	
	/**
	 * Sets the checks if is transfer when public hd is minus.
	 *
	 * @param isTransferWhenPublicHdIsMinus the new checks if is transfer when public hd is minus
	 */
	void setIsTransferWhenPublicHdIsMinus(boolean isTransferWhenPublicHdIsMinus);
	
	/**
	 * Sets the carry over deadline.
	 *
	 * @param carryOverDeadline the new carry over deadline
	 */
	void setCarryOverDeadline(PublicHolidayCarryOverDeadline carryOverDeadline);
}
