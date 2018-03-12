/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration;

/**
 * The Interface ForwardSettingOfPublicHolidayGetMemento.
 */
public interface ForwardSettingOfPublicHolidayGetMemento {
	
	/**
	 * Gets the company ID.
	 *
	 * @return the company ID
	 */
	String getCompanyID();
	
	/**
	 * Gets the checks if is transfer when public hd is minus.
	 *
	 * @return the checks if is transfer when public hd is minus
	 */
	boolean getIsTransferWhenPublicHdIsMinus();
	
	/**
	 * Gets the carry over deadline.
	 *
	 * @return the carry over deadline
	 */
	PublicHolidayCarryOverDeadline getCarryOverDeadline();
}
