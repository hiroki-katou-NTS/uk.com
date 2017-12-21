/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.holidaysetting.configuration;

/**
 * The Interface FourWeekFourHolidayNumberSettingSetMemento.
 */
public interface FourWeekFourHolidayNumberSettingSetMemento {
	
	/**
	 * Sets the checks if is one week holiday.
	 *
	 * @param isOneWeekHoliday the new checks if is one week holiday
	 */
	void setIsOneWeekHoliday(boolean isOneWeekHoliday);
	
	/**
	 * Sets the one week.
	 *
	 * @param oneWeek the new one week
	 */
	void setOneWeek(OneWeekPublicHoliday oneWeek);
	
	/**
	 * Sets the checks if is four week holiday.
	 *
	 * @param isFourWeekHoliday the new checks if is four week holiday
	 */
	void setIsFourWeekHoliday(boolean isFourWeekHoliday);
	
	/**
	 * Sets the four week.
	 *
	 * @param fourWeek the new four week
	 */
	void setFourWeek(FourWeekPublicHoliday fourWeek);
	
	/**
	 * Sets the cid.
	 *
	 * @param CID the new cid
	 */
	void setCID(String CID);
}
