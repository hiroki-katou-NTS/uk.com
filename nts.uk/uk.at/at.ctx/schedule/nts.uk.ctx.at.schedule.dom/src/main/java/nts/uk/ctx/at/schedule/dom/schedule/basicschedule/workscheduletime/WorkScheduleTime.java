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
	
	public static WorkScheduleTime createFromJavaType(List<PersonFeeTime> listPersonFeeTime, Integer breakTime,
			Integer workingTime, Integer weekdayTime, Integer predetermineTime, Integer totalLaborTime,
			Integer childCareTime) {
		return new WorkScheduleTime(listPersonFeeTime, new AttendanceTime(breakTime), new AttendanceTime(workingTime),
				new AttendanceTime(weekdayTime), new AttendanceTime(predetermineTime),
				new AttendanceTime(totalLaborTime), new AttendanceTime(childCareTime));
	}
	
	public boolean diffBreakTime(AttendanceTime attendanceTime){
		return breakTime.v().intValue() != attendanceTime.v().intValue();
	}
	
	public boolean diffWorkingTime(AttendanceTime attendanceTime){
		return workingTime.v().intValue() != attendanceTime.v().intValue();
	}
	
	public boolean diffWeekdayTime(AttendanceTime attendanceTime){
		return weekdayTime.v().intValue() != attendanceTime.v().intValue();
	}
	
	public boolean diffPredetermineTime(AttendanceTime attendanceTime){
		return predetermineTime.v().intValue() != attendanceTime.v().intValue();
	}
	
	public boolean diffTotalLaborTime(AttendanceTime attendanceTime){
		return totalLaborTime.v().intValue() != attendanceTime.v().intValue();
	}
	
	public boolean diffChildCareTime(AttendanceTime attendanceTime){
		return childCareTime.v().intValue() != attendanceTime.v().intValue();
	}
	
}
