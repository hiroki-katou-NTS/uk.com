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
//import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;

/**
 * The Interface BasicScheduleSetMemento.
 */
public interface BasicScheduleSetMemento {
	
	/**
	 * Sets the employee id.
	 *
	 * @param employeeId the new employee id
	 */
	public void setEmployeeId(String employeeId);
	
	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(GeneralDate date);
	
	/**
	 * Sets the work type code.
	 *
	 * @param workTypeCode the new work type code
	 */
	public void setWorkTypeCode(String workTypeCode);
	
	
	/**
	 * Sets the work time code.
	 *
	 * @param workTimeCode the new work time code
	 */
	public void setWorkTimeCode(String workTimeCode);
	
	
	/**
	 * Sets the confirmed atr.
	 *
	 * @param confirmedAtr the new confirmed atr
	 */
	public void setConfirmedAtr(ConfirmedAtr confirmedAtr);
	
	/**
	 * Sets the work schedule time zones.
	 *
	 * @param workScheduleTimeZones the new work schedule time zones
	 */
	public void setWorkScheduleTimeZones(List<WorkScheduleTimeZone> workScheduleTimeZones);
	
	/**
	 * Sets the work schedule breaks.
	 *
	 * @param workScheduleBreaks the new work schedule breaks
	 */
	public void setWorkScheduleBreaks(List<WorkScheduleBreak> workScheduleBreaks);
	
	
	/**
	 * Sets the work schedule time.
	 *
	 * @param workScheduleTime the new work schedule time
	 */
	public void setWorkScheduleTime(Optional<WorkScheduleTime> workScheduleTime);
	
	
	/**
	 * Sets the work schedule person fees.
	 *
	 * @param workSchedulePersonFees the new work schedule person fees
	 */
	public void setWorkSchedulePersonFees(List<WorkSchedulePersonFee> workSchedulePersonFees);
	
	
	/**
	 * Sets the child care schedules.
	 *
	 * @param childCareSchedules the new child care schedules
	 */
	public void setChildCareSchedules(List<ChildCareSchedule> childCareSchedules);

}
