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
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;

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
	
	//育児時間
	private AttendanceTime childTime;
	
	//介護時間
	private AttendanceTime careTime;
	
	//フレックス時間
	private AttendanceTimeOfExistMinus flexTime;
	
	public static WorkScheduleTime createFromJavaType(List<PersonFeeTime> listPersonFeeTime, Integer breakTime,
			Integer workingTime, Integer weekdayTime, Integer predetermineTime, Integer totalLaborTime,
			Integer childTime, Integer careTime, Integer flexTime) {
		return new WorkScheduleTime(listPersonFeeTime, new AttendanceTime(breakTime), new AttendanceTime(workingTime),
				new AttendanceTime(weekdayTime), new AttendanceTime(predetermineTime),
				new AttendanceTime(totalLaborTime), new AttendanceTime(childTime), new AttendanceTime(careTime), new AttendanceTimeOfExistMinus(flexTime));
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
	
	public boolean diffChildTime(AttendanceTime attendanceTime){
		return childTime.v().intValue() != attendanceTime.v().intValue();
	}
	
	public boolean diffCareTime(AttendanceTime attendanceTime){
		return careTime.v().intValue() != attendanceTime.v().intValue();
	}
	
	public boolean diffFlexTime(AttendanceTimeOfExistMinus attendanceTime){
		return flexTime.v().intValue() != attendanceTime.v().intValue();
	}
	
}
