package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletime;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * The Interface WorkScheduleTimeGetMemento.
 */
public interface WorkScheduleTimeGetMemento {
	
	/**
	 * Gets the person fee time.
	 *
	 * @return the person fee time
	 */
	public List<PersonFeeTime> getPersonFeeTime();

    /**
     * Gets the break time.
     *
     * @return the break time
     */
    public AttendanceTime getBreakTime();

    /**
     * Gets the working time.
     *
     * @return the working time
     */
    public AttendanceTime getWorkingTime();

    /**
     * Gets the weekday time.
     *
     * @return the weekday time
     */
    public AttendanceTime getWeekdayTime();

    /**
     * Gets the predetermine time.
     *
     * @return the predetermine time
     */
    public AttendanceTime getPredetermineTime();

    /**
     * Gets the total labor time.
     *
     * @return the total labor time
     */
    public AttendanceTime getTotalLaborTime();

    /**
     * Gets the child care time.
     *
     * @return the child care time
     */
    public AttendanceTime getChildCareTime();
}
