/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Interface PredetemineTimeSetMemento.
 */
public interface PredetemineTimeSettingSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	public void setCompanyId(String companyId);

	/**
	 * Sets the range time day.
	 *
	 * @param rangeTimeDay the new range time day
	 */
	public void setRangeTimeDay(AttendanceTime rangeTimeDay);

	/**
	 * Sets the work time code.
	 *
	 * @param workTimeCode the new work time code
	 */
	public void setWorkTimeCode(WorkTimeCode workTimeCode);

	/**
	 * Sets the pred time.
	 *
	 * @param predTime the new pred time
	 */
	public void setPredTime(PredetermineTime predTime);

	/**
	 * Sets the night shift.
	 *
	 * @param nightShift the new night shift
	 */
//	public void setNightShift(boolean nightShift);

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
	public void setStartDateClock(TimeWithDayAttr startDateClock);

	/**
	 * Sets the predetermine.
	 *
	 * @param predetermine the new predetermine
	 */
	public void setPredetermine(boolean predetermine);
}
