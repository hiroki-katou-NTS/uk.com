/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration;

/**
 * The Interface FourWeekFourHolidayNumberSettingGetMemento.
 */
public interface FourWeekFourHolidayNumberSettingGetMemento {
		
	/**
	 * Gets the checks if is one week holiday.
	 *
	 * @return the checks if is one week holiday
	 */
	boolean getIsOneWeekHoliday();
	
	/**
	 * Gets the one week.
	 *
	 * @return the one week
	 */
	OneWeekPublicHoliday getOneWeek();
	
	/**
	 * Gets the checks if is four week holiday.
	 *
	 * @return the checks if is four week holiday
	 */
	boolean getIsFourWeekHoliday();
	
	/**
	 * Gets the four week.
	 *
	 * @return the four week
	 */
	FourWeekPublicHoliday getFourWeek();
	
	/**
	 * Gets the cid.
	 *
	 * @return the cid
	 */
	String getCID(); 
}
