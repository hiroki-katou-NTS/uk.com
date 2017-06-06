/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

/**
 * The Interface CompensatoryTransferGetMenento.
 */
public interface CompensatoryTransferGetMemento {
	
	/**
	 * Gets the certain time.
	 *
	 * @return the certain time
	 */
	OneDayTime getCertainTime();
	
	/**
	 * Gets the use division.
	 *
	 * @return the use division
	 */
	boolean getUseDivision();
	
	/**
	 * Gets the design time.
	 *
	 * @return the design time
	 */
	OneDayTime getOneDayTime();
	
	/**
	 * Gets the half day time.
	 *
	 * @return the half day time
	 */
	OneDayTime getHalfDayTime();
	
	/**
	 * Gets the transfer division.
	 *
	 * @return the transfer division
	 */
	TransferSettingDivision getTransferDivision(); 
	
	/**
	 * Gets the compensatory occurrence division.
	 *
	 * @return the compensatory occurrence division
	 */
	CompensatoryOccurrenceDivision getCompensatoryOccurrenceDivision();
}
