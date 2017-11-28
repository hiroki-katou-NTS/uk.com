/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset;

/**
 * The Interface PredetemineTimeSetMemento.
 */
public interface PredetemineTimeSetMemento {

	/**
	 * Sets the company ID.
	 *
	 * @param companyID the new company ID
	 */
	public void setCompanyID(String companyID);

	/**
	 * Sets the range time day.
	 *
	 * @param rangeTimeDay the new range time day
	 */
	public void setRangeTimeDay(int rangeTimeDay);

	/**
	 * Sets the sift CD.
	 *
	 * @param siftCD the new sift CD
	 */
	public void setSiftCD(String siftCD);

	/**
	 * Sets the addition set ID.
	 *
	 * @param additionSetID the new addition set ID
	 */
	public void setAdditionSetID(String additionSetID);

	/**
	 * Sets the night shift.
	 *
	 * @param nightShift the new night shift
	 */
	public void setNightShift(boolean nightShift);

	/**
	 * Sets the prescribed timezone setting.
	 *
	 * @param prescribedTimezoneSetting the new prescribed timezone setting
	 */
	public void setPrescribedTimezoneSetting(PrescribedTimezoneSetting prescribedTimezoneSetting);

	/**
	 * Sets the start date clock.
	 *
	 * @param startDateClock the new start date clock
	 */
	public void setStartDateClock(int startDateClock);

	/**
	 * Sets the predetermine.
	 *
	 * @param predetermine the new predetermine
	 */
	public void setPredetermine(boolean predetermine);
}
