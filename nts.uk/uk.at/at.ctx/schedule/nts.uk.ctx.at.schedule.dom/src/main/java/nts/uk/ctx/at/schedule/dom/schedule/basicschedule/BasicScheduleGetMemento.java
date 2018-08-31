/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.schedule.basicschedule;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.personalfee.WorkSchedulePersonFee;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workschedulebreak.WorkScheduleBreak;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletime.WorkScheduleTime;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.WorkScheduleTimeZone;

/**
 * The Interface BasicScheduleGetMemento.
 */
public interface BasicScheduleGetMemento {
	
	/**
	 * Gets the employee id.
	 *
	 * @return the employee id
	 */
	public String getEmployeeId();
	
	
	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public GeneralDate getDate();
	
	
	/**
	 * Gets the work type code.
	 *
	 * @return the work type code
	 */
	public String getWorkTypeCode();
	
	
	/**
	 * Gets the work time code.
	 *
	 * @return the work time code
	 */
	public String getWorkTimeCode();
	
	
	/**
	 * Gets the confirmed atr.
	 *
	 * @return the confirmed atr
	 */
	public ConfirmedAtr getConfirmedAtr();
	

	/**
	 * Gets the work schedule time zones.
	 *
	 * @return the work schedule time zones
	 */
	public List<WorkScheduleTimeZone> getWorkScheduleTimeZones();
	
	
	/**
	 * Gets the work schedule breaks.
	 *
	 * @return the work schedule breaks
	 */
	public List<WorkScheduleBreak> getWorkScheduleBreaks();
	
	
	/**
	 * Gets the work schedule time.
	 *
	 * @return the work schedule time
	 */
	public Optional<WorkScheduleTime> getWorkScheduleTime();
	
	
	/**
	 * Gets the work schedule person fees.
	 *
	 * @return the work schedule person fees
	 */
	public List<WorkSchedulePersonFee> getWorkSchedulePersonFees();
	
	
	/**
	 * Gets the child care schedules.
	 *
	 * @return the child care schedules
	 */
	public List<ChildCareSchedule> getChildCareSchedules();

}
