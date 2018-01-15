package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletime;

import java.util.List;

/**
 * The Interface WorkScheduleTimeSetMemento.
 */
public interface WorkScheduleTimeSetMemento {
	
	/**
	 * Sets the person fee time.
	 *
	 * @param personFeeTime the new person fee time
	 */
	public void setPersonFeeTime(List<PersonFeeTime> personFeeTime);

    /**
     * Sets the break time.
     *
     * @param breakTime the new break time
     */
    public void setBreakTime(AttendanceTime breakTime);

    /**
     * Sets the working time.
     *
     * @param workingTime the new working time
     */
    public void setWorkingTime(AttendanceTime workingTime);

    /**
     * Sets the weekday time.
     *
     * @param weekdayTime the new weekday time
     */
    public void setWeekdayTime(AttendanceTime weekdayTime);

    /**
     * Sets the predetermine time.
     *
     * @param predetermineTime the new predetermine time
     */
    public void setPredetermineTime(AttendanceTime predetermineTime);

    /**
     * Sets the total labor time.
     *
     * @param totalLaborTime the new total labor time
     */
    public void setTotalLaborTime(AttendanceTime totalLaborTime);

    /**
     * Sets the child care time.
     *
     * @param childCareTime the new child care time
     */
    public void setChildCareTime(AttendanceTime childCareTime);
}
