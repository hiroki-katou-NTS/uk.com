/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletime;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * The Class WorkScheduleTime.
 */
//勤務予定時間
@Getter
@AllArgsConstructor
public class WorkScheduleTime extends DomainObject{

	/** The person fee time. */
	//人件費時間
	private List<PersonFeeTime> personFeeTime;
	
	/** The break time. */
	//休憩時間
	private AttendanceTime breakTime;
	
	/** The working time. */
	//実働時間
	private AttendanceTime workingTime;
	
	/** The weekday time. */
	//平日時間
	private AttendanceTime weekdayTime;
	
	/** The predetermine time. */
	//所定時間
	private AttendanceTime predetermineTime;
	
	/** The total labor time. */
	//総労働時間
	private AttendanceTime totalLaborTime;
	
	/** The child care time. */
	//育児介護時間
	private AttendanceTime childCareTime;
	
	public static WorkScheduleTime createFromJavaType(List<PersonFeeTime> listPersonFeeTime, int breakTime, int workingTime,
			int weekdayTime, int predetermineTime, int totalLaborTime, int childCareTime) {
		return new WorkScheduleTime(listPersonFeeTime, 
				new AttendanceTime(Integer.valueOf(breakTime)),
				new AttendanceTime(Integer.valueOf(workingTime)), new AttendanceTime(Integer.valueOf(weekdayTime)),
				new AttendanceTime(Integer.valueOf(predetermineTime)),
				new AttendanceTime(Integer.valueOf(totalLaborTime)),
				new AttendanceTime(Integer.valueOf(childCareTime)));
	}
}
