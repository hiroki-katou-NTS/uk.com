/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Interface PredetemineTimeGetMemento.
 */
public interface PredetemineTimeSettingGetMemento {
	
	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	public String getCompanyId();

	/**
	 * Gets the range time day.
	 *
	 * @return the range time day
	 */
	public AttendanceTime getRangeTimeDay();

    /**
     * Gets the work time code.
     *
     * @return the work time code
     */
    public WorkTimeCode getWorkTimeCode();

    /**
     * Gets the pred time.
     *
     * @return the pred time
     */
    public PredetermineTime getPredTime();

    /**
     * Checks if is night shift.
     *
     * @return true, if is night shift
     */
//    public boolean isNightShift();

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
	public TimeWithDayAttr getStartDateClock();

    /**
     * Checks if is predetermine.
     *
     * @return true, if is predetermine
     */
    public boolean isPredetermine();
       
}
