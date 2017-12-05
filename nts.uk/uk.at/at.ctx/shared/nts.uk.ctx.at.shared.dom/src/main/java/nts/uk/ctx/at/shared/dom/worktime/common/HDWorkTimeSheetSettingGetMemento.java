/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Interface HDWorkTimeSheetSettingGetMemento.
 */
public interface HDWorkTimeSheetSettingGetMemento {

	/**
	 * Gets the work time no.
	 *
	 * @return the work time no
	 */
	Integer getWorkTimeNo();
	
	
	/**
	 * Gets the timezone.
	 *
	 * @return the timezone
	 */
	TimeZoneRounding getTimezone();
	
	
	/**
	 * Gets the checks if is legal holiday constraint time.
	 *
	 * @return the checks if is legal holiday constraint time
	 */
	boolean getIsLegalHolidayConstraintTime();
	
	
	/**
	 * Gets the in legal break frame no.
	 *
	 * @return the in legal break frame no
	 */
	BreakFrameNo getInLegalBreakFrameNo();
	
	/**
	 * Gets the checks if is non statutory dayoff constraint time.
	 *
	 * @return the checks if is non statutory dayoff constraint time
	 */
	boolean getIsNonStatutoryDayoffConstraintTime();
	
	
	/**
	 * Gets the out legal break frame no.
	 *
	 * @return the out legal break frame no
	 */
	BreakFrameNo getOutLegalBreakFrameNo();
	
	
	/**
	 * Gets the checks if is non statutory holiday constraint time.
	 *
	 * @return the checks if is non statutory holiday constraint time
	 */
	boolean getIsNonStatutoryHolidayConstraintTime();
	
	
	/**
	 * Gets the out legal pub HD frame no.
	 *
	 * @return the out legal pub HD frame no
	 */
	BreakFrameNo getOutLegalPubHDFrameNo();
}
