/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.predset;

/**
 * The Interface PredetemineTimeGetMemento.
 */
public interface PredetemineTimeGetMemento {
	
	/**
	 * Gets the company ID.
	 *
	 * @return the company ID
	 */
	public String getCompanyID();

	/**
	 * Gets the range time day.
	 *
	 * @return the range time day
	 */
	public int getRangeTimeDay();

	/**
	 * Gets the sift CD.
	 *
	 * @return the sift CD
	 */
	public String getSiftCD();

	/**
	 * Gets the addition set ID.
	 *
	 * @return the addition set ID
	 */
	public String getAdditionSetID();

	/**
	 * Checks if is night shift.
	 *
	 * @return true, if is night shift
	 */
	public boolean isNightShift();

	/**
	 * Gets the prescribed timezone setting.
	 *
	 * @return the prescribed timezone setting
	 */
	public PrescribedTimezoneSetting getPrescribedTimezoneSetting();

	/**
	 * Gets the start date clock.
	 *
	 * @return the start date clock
	 */
	public int getStartDateClock();

	/**
	 * Checks if is predetermine.
	 *
	 * @return true, if is predetermine
	 */
	public boolean isPredetermine();
}
