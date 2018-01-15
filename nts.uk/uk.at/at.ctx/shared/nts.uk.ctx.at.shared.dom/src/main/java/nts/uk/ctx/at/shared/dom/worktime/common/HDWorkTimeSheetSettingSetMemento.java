/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Interface HDWorkTimeSheetSettingSetMemento.
 */
public interface HDWorkTimeSheetSettingSetMemento {

	/**
	 * Sets the work time no.
	 *
	 * @param workTimeNo the new work time no
	 */
	void setWorkTimeNo(Integer workTimeNo);

	/**
	 * Sets the timezone.
	 *
	 * @param timezone the new timezone
	 */
	void setTimezone(TimeZoneRounding timezone);
	
	
	/**
	 * Sets the checks if is legal holiday constraint time.
	 *
	 * @param isLegalHolidayConstraintTime the new checks if is legal holiday constraint time
	 */
	void setIsLegalHolidayConstraintTime(boolean isLegalHolidayConstraintTime);
	
	
	/**
	 * Sets the in legal break frame no.
	 *
	 * @param inLegalBreakFrameNo the new in legal break frame no
	 */
	void setInLegalBreakFrameNo(BreakFrameNo inLegalBreakFrameNo);
	
	
	/**
	 * Sets the checks if is non statutory dayoff constraint time.
	 *
	 * @param isNonStatutoryDayoffConstraintTime the new checks if is non statutory dayoff constraint time
	 */
	void setIsNonStatutoryDayoffConstraintTime(boolean isNonStatutoryDayoffConstraintTime);
	
	
	/**
	 * Sets the out legal break frame no.
	 *
	 * @param outLegalBreakFrameNo the new out legal break frame no
	 */
	void setOutLegalBreakFrameNo(BreakFrameNo outLegalBreakFrameNo);
	
	
	/**
	 * Sets the checks if is non statutory holiday constraint time.
	 *
	 * @param isNonStatutoryHolidayConstraintTime the new checks if is non statutory holiday constraint time
	 */
	void setIsNonStatutoryHolidayConstraintTime(boolean isNonStatutoryHolidayConstraintTime);
	
	
	/**
	 * Sets the out legal pub HD frame no.
	 *
	 * @param outLegalPubHDFrameNo the new out legal pub HD frame no
	 */
	void setOutLegalPubHDFrameNo(BreakFrameNo outLegalPubHDFrameNo);
	
}
