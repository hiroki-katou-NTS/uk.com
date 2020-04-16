/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workingcondition;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class PersonalWorkCategory.
 */
// 個人勤務日区分別勤務
@Getter
public class PersonalWorkCategory extends DomainObject {

	/** The weekday time. */
	// 平日時
	private SingleDaySchedule weekdayTime;

	/** The holiday work. */
	// 休日出勤時
	private SingleDaySchedule holidayWork;

	/** The holiday time. */
	// 休日時
	private SingleDaySchedule holidayTime;

	/** The in law break time. */
	// 法内休出時
	private Optional<SingleDaySchedule> inLawBreakTime;

	/** The outside law break time. */
	// 法外休出時
	private Optional<SingleDaySchedule> outsideLawBreakTime;

	/** The holiday attendance time. */
	// 祝日出勤時
	private Optional<SingleDaySchedule> holidayAttendanceTime;

	/** The public holiday work. */
	// 公休出勤時
	private Optional<SingleDaySchedule> publicHolidayWork;

	/**
	 * Instantiates a new personal work category.
	 *
	 * @param memento
	 *            the memento
	 */
	public PersonalWorkCategory(PersonalWorkCategoryGetMemento memento) {
		this.holidayWork = memento.getHolidayWork();
		this.holidayTime = memento.getHolidayTime();
		this.weekdayTime = memento.getWeekdayTime();
		this.publicHolidayWork = memento.getPublicHolidayWork();
		this.inLawBreakTime = memento.getInLawBreakTime();
		this.outsideLawBreakTime = memento.getOutsideLawBreakTime();
		this.holidayAttendanceTime = memento.getHolidayAttendanceTime();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(PersonalWorkCategorySetMemento memento) {
		memento.setHolidayWork(this.holidayWork);
		memento.setHolidayTime(this.holidayTime);
		memento.setWeekdayTime(this.weekdayTime);
		memento.setPublicHolidayWork(this.publicHolidayWork);
		memento.setInLawBreakTime(this.inLawBreakTime);
		memento.setOutsideLawBreakTime(this.outsideLawBreakTime);
		memento.setHolidayAttendanceTime(this.holidayAttendanceTime);
	}

	public PersonalWorkCategory(SingleDaySchedule weekdayTime, SingleDaySchedule holidayWork,
			SingleDaySchedule holidayTime, Optional<SingleDaySchedule> inLawBreakTime,
			Optional<SingleDaySchedule> outsideLawBreakTime, Optional<SingleDaySchedule> holidayAttendanceTime,
			Optional<SingleDaySchedule> publicHolidayWork) {
		super();
		this.weekdayTime = weekdayTime;
		this.holidayWork = holidayWork;
		this.holidayTime = holidayTime;
		this.inLawBreakTime = inLawBreakTime;
		this.outsideLawBreakTime = outsideLawBreakTime;
		this.holidayAttendanceTime = holidayAttendanceTime;
		this.publicHolidayWork = publicHolidayWork;
	}

	public PersonalWorkCategory(SingleDaySchedule weekdayTime) {
		this.weekdayTime = weekdayTime;
	}
	
	

}
