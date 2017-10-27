/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.personallaborcondition;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class PersonalWorkCategory.
 */
// 個人勤務日区分別勤務
@Getter
public class PersonalWorkCategory extends DomainObject{
	
	/** The holiday work. */
	// 休日出勤時
	private SingleDaySchedule holidayWork;
	
	/** The holiday time. */
	// 休日時
	private SingleDaySchedule holidayTime;
	
	/** The weekday time. */
	// 平日時
	private SingleDaySchedule weekdayTime;
	
	/** The public holiday work. */
	// 公休出勤時
	private Optional<SingleDaySchedule> publicHolidayWork;
	
	/** The in law break time. */
	// 法内休出時
	private Optional<SingleDaySchedule> inLawBreakTime;
	
	/** The outside law break time. */
	// 法外休出時
	private Optional<SingleDaySchedule> outsideLawBreakTime;
	
	/** The holiday attendance time. */
	// 祝日出勤時
	private Optional<SingleDaySchedule> holidayAttendanceTime;

}
