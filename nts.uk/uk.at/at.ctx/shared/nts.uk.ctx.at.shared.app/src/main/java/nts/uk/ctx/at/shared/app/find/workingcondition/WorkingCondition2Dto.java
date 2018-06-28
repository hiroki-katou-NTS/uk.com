package nts.uk.ctx.at.shared.app.find.workingcondition;

import java.util.Optional;

import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalDayOfWeek;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategory;
import nts.uk.ctx.at.shared.dom.workingcondition.ScheduleMethod;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDaySchedule;
import nts.uk.ctx.at.shared.dom.workingcondition.TimeZone;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.pereg.app.PeregItem;

@Setter
public class WorkingCondition2Dto extends WorkingConditionDto {	
	
	/**
	 * 期間 - 
	 */
	@PeregItem("IS00780")
	private String period2;

	/**
	 * 開始日
	 */
	@PeregItem("IS00781")
	private GeneralDate startDate2;

	/**
	 * 終了日
	 */
	@PeregItem("IS00782")
	private GeneralDate endDate2;
	
	public WorkingCondition2Dto(String recordId) {
		super(recordId);
		// TODO Auto-generated constructor stub
	}
	
	public static WorkingCondition2Dto createWorkingConditionDto(DateHistoryItem dateHistoryItem,
			WorkingConditionItem workingConditionItem) {
		WorkingCondition2Dto dto = new WorkingCondition2Dto(dateHistoryItem.identifier());

		dto.setRecordId(dateHistoryItem.identifier());
		dto.setStartDate2(dateHistoryItem.start());
		dto.setEndDate2(dateHistoryItem.end());

		if (workingConditionItem.getHourlyPaymentAtr() != null) {
			dto.setHourlyPaymentAtr(workingConditionItem.getHourlyPaymentAtr().value);
		}

		workingConditionItem.getTimeApply().ifPresent(wci -> {
			dto.setTimeApply(wci.v());
		});

		dto.setScheduleManagementAtr(workingConditionItem.getScheduleManagementAtr().value);

		// 予定作成方法
		workingConditionItem.getMonthlyPattern().ifPresent(mp -> {
			dto.setMonthlyPattern(mp.v());
		});

		setScheduleMethod(dto, workingConditionItem.getScheduleMethod().get());

		PersonalWorkCategory workCategory = workingConditionItem.getWorkCategory();

		// 休日出勤時
		setHolidayTime(dto, workCategory.getHolidayTime());

		// 平日時
		setWeekDay(dto, workCategory.getWeekdayTime());

		// 休日出勤時
		setWorkInHoliday(dto, workCategory.getHolidayWork());

		// 公休出勤時
		workCategory.getPublicHolidayWork().ifPresent(phw -> {
			setWorkInPublicHoliday(dto, phw);
		});

		// 法内休出時
		workCategory.getInLawBreakTime().ifPresent(ilbt -> {
			setInLawBreakTime(dto, ilbt);
		});

		// 法外休出時
		workCategory.getOutsideLawBreakTime().ifPresent(olbt -> {
			setOutsideLawBreakTime(dto, olbt);
		});

		// 祝日出勤時
		workCategory.getHolidayAttendanceTime().ifPresent(at -> {
			setHolidayAttendanceTime(dto, at);
		});

		PersonalDayOfWeek workDayOfWeek = workingConditionItem.getWorkDayOfWeek();

		// 日曜日
		workDayOfWeek.getSunday().ifPresent(sund -> setSunday(dto, sund));

		// 月曜日
		workDayOfWeek.getMonday().ifPresent(mond -> setMonday(dto, mond));

		// 火曜日
		workDayOfWeek.getTuesday().ifPresent(tue -> setTuesday(dto, tue));

		// 水曜日
		workDayOfWeek.getWednesday().ifPresent(wed -> setWednesday(dto, wed));

		// 木曜日
		workDayOfWeek.getThursday().ifPresent(thu -> setThursday(dto, thu));

		// 金曜日
		workDayOfWeek.getFriday().ifPresent(fri -> setFriday(dto, fri));

		// 土曜日
		workDayOfWeek.getSaturday().ifPresent(sat -> setSaturday(dto, sat));

		dto.setAutoIntervalSetAtr(workingConditionItem.getAutoIntervalSetAtr().value);
		dto.setVacationAddedTimeAtr(workingConditionItem.getVacationAddedTimeAtr().value);

		workingConditionItem.getHolidayAddTimeSet().ifPresent(hat -> {
			Optional.of(hat.getOneDay()).ifPresent(od -> dto.setOneDay(od.v()));

			Optional.of(hat.getMorning()).ifPresent(od -> dto.setMorning(od.v()));

			Optional.of(hat.getAfternoon()).ifPresent(od -> dto.setAfternoon(od.v()));
		});

		dto.setLaborSystem(workingConditionItem.getLaborSystem().value);
		dto.setContractTime(workingConditionItem.getContractTime().v());
		dto.setAutoStampSetAtr(workingConditionItem.getAutoStampSetAtr().value);

		return dto;
	}

	private static void setScheduleMethod(WorkingCondition2Dto dto, ScheduleMethod scheduleMethod) {
		dto.setBasicCreateMethod(scheduleMethod.getBasicCreateMethod().value);

		scheduleMethod.getWorkScheduleBusCal().ifPresent(wsb -> {
			dto.setReferenceBasicWork(wsb.getReferenceBasicWork().value);
			dto.setReferenceBusinessDayCalendar(wsb.getReferenceBusinessDayCalendar().value);
		});

		// cần xem lại thuật toán thực thi đoạn mã này
		switch (scheduleMethod.getBasicCreateMethod()) {
		case BUSINESS_DAY_CALENDAR:
			scheduleMethod.getWorkScheduleBusCal().ifPresent(wsb -> {
				dto.setReferenceType(wsb.getReferenceWorkingHours().value);
			});
			break;
		case MONTHLY_PATTERN:
			scheduleMethod.getMonthlyPatternWorkScheduleCre().ifPresent(mps -> {
				dto.setReferenceType(mps.getReferenceType().value);
			});
			break;
		default:
		case PERSONAL_DAY_OF_WEEK:
			break;
		}
	}

	private static void setHolidayTime(WorkingCondition2Dto dto, SingleDaySchedule holidayTime) {
		Optional.ofNullable(holidayTime).ifPresent(ht -> {
			dto.setHolidayWorkTypeCode(ht.getWorkTypeCode().v());
		});
	}

	private static void setWeekDay(WorkingCondition2Dto dto, SingleDaySchedule weekDay) {
		Optional.of(weekDay).ifPresent(wd -> {
			dto.setWeekdayWorkTypeCode(wd.getWorkTypeCode().v());

			wd.getWorkTimeCode().ifPresent(wt -> dto.setWeekdayWorkTimeCode(wt.v()));

			Optional<TimeZone> timeZone1 = wd.getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 1)
					.findFirst();
			Optional<TimeZone> timeZone2 = wd.getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 2)
					.findFirst();

			timeZone1.ifPresent(tz -> {
				dto.setWeekDayStartTime1(tz.getStart().v());
				dto.setWeekDayEndTime1(tz.getEnd().v());
			});

			timeZone2.ifPresent(tz -> {
				dto.setWeekDayStartTime2(tz.getStart().v());
				dto.setWeekDayEndTime2(tz.getEnd().v());
			});
		});
	}

	private static void setWorkInHoliday(WorkingCondition2Dto dto, SingleDaySchedule workInHoliday) {
		dto.setWorkInHolidayWorkTypeCode(workInHoliday.getWorkTypeCode().v());

		workInHoliday.getWorkTimeCode().ifPresent(wtc -> dto.setWorkInHolidayWorkTimeCode(wtc.v()));

		Optional<TimeZone> timeZone1 = workInHoliday.getWorkingHours().stream()
				.filter(timeZone -> timeZone.getCnt() == 1).findFirst();

		Optional<TimeZone> timeZone2 = workInHoliday.getWorkingHours().stream()
				.filter(timeZone -> timeZone.getCnt() == 2).findFirst();

		timeZone1.ifPresent(tz -> {
			dto.setWorkInHolidayStartTime1(tz.getStart().v());
			dto.setWorkInHolidayEndTime1(tz.getEnd().v());
		});

		timeZone2.ifPresent(tz -> {
			dto.setWorkInHolidayStartTime2(tz.getStart().v());
			dto.setWorkInHolidayEndTime2(tz.getEnd().v());
		});
	}

	private static void setWorkInPublicHoliday(WorkingCondition2Dto dto, SingleDaySchedule workInPublicHoliday) {
		dto.setWorkInPublicHolidayWorkTypeCode(workInPublicHoliday.getWorkTypeCode().v());

		workInPublicHoliday.getWorkTimeCode().ifPresent(wtc -> dto.setWorkInPublicHolidayWorkTimeCode(wtc.v()));

		Optional<TimeZone> timeZone1 = workInPublicHoliday.getWorkingHours().stream()
				.filter(timeZone -> timeZone.getCnt() == 1).findFirst();
		Optional<TimeZone> timeZone2 = workInPublicHoliday.getWorkingHours().stream()
				.filter(timeZone -> timeZone.getCnt() == 2).findFirst();

		timeZone1.ifPresent(tz -> {
			dto.setWorkInPublicHolidayStartTime1(tz.getStart().v());
			dto.setWorkInPublicHolidayEndTime1(tz.getEnd().v());
		});

		timeZone2.ifPresent(tz -> {
			dto.setWorkInPublicHolidayStartTime2(tz.getStart().v());
			dto.setWorkInPublicHolidayEndTime2(tz.getEnd().v());
		});
	}

	private static void setInLawBreakTime(WorkingCondition2Dto dto, SingleDaySchedule inLawBreakTime) {
		dto.setInLawBreakTimeWorkTypeCode(inLawBreakTime.getWorkTypeCode().v());

		inLawBreakTime.getWorkTimeCode().ifPresent(wtc -> dto.setInLawBreakTimeWorkTimeCode(wtc.v()));

		Optional<TimeZone> timeZone1 = inLawBreakTime.getWorkingHours().stream()
				.filter(timeZone -> timeZone.getCnt() == 1).findFirst();
		Optional<TimeZone> timeZone2 = inLawBreakTime.getWorkingHours().stream()
				.filter(timeZone -> timeZone.getCnt() == 2).findFirst();

		timeZone1.ifPresent(tz -> {
			dto.setInLawBreakTimeStartTime1(tz.getStart().v());
			dto.setInLawBreakTimeEndTime1(tz.getEnd().v());
		});

		timeZone2.ifPresent(tz -> {
			dto.setInLawBreakTimeStartTime2(tz.getStart().v());
			dto.setInLawBreakTimeEndTime2(tz.getEnd().v());
		});
	}

	private static void setOutsideLawBreakTime(WorkingCondition2Dto dto, SingleDaySchedule outsideLawBreakTime) {
		dto.setOutsideLawBreakTimeWorkTypeCode(outsideLawBreakTime.getWorkTypeCode().v());

		outsideLawBreakTime.getWorkTimeCode().ifPresent(wtc -> dto.setOutsideLawBreakTimeWorkTimeCode(wtc.v()));

		Optional<TimeZone> timeZone1 = outsideLawBreakTime.getWorkingHours().stream()
				.filter(timeZone -> timeZone.getCnt() == 1).findFirst();
		Optional<TimeZone> timeZone2 = outsideLawBreakTime.getWorkingHours().stream()
				.filter(timeZone -> timeZone.getCnt() == 2).findFirst();

		timeZone1.ifPresent(tz -> {
			dto.setOutsideLawBreakTimeStartTime1(tz.getStart().v());
			dto.setOutsideLawBreakTimeEndTime1(tz.getEnd().v());
		});

		timeZone2.ifPresent(tz -> {
			dto.setOutsideLawBreakTimeStartTime2(tz.getStart().v());
			dto.setOutsideLawBreakTimeEndTime2(tz.getEnd().v());
		});
	}

	private static void setHolidayAttendanceTime(WorkingCondition2Dto dto, SingleDaySchedule holidayAttendanceTime) {
		dto.setHolidayAttendanceTimeWorkTypeCode(holidayAttendanceTime.getWorkTypeCode().v());

		holidayAttendanceTime.getWorkTimeCode().ifPresent(wtc -> dto.setHolidayAttendanceTimeWorkTimeCode(wtc.v()));

		Optional<TimeZone> timeZone1 = holidayAttendanceTime.getWorkingHours().stream()
				.filter(timeZone -> timeZone.getCnt() == 1).findFirst();
		Optional<TimeZone> timeZone2 = holidayAttendanceTime.getWorkingHours().stream()
				.filter(timeZone -> timeZone.getCnt() == 2).findFirst();

		timeZone1.ifPresent(tz -> {
			dto.setHolidayAttendanceTimeStartTime1(tz.getStart().v());
			dto.setHolidayAttendanceTimeEndTime1(tz.getEnd().v());
		});

		timeZone2.ifPresent(tz -> {
			dto.setHolidayAttendanceTimeStartTime2(tz.getStart().v());
			dto.setHolidayAttendanceTimeEndTime2(tz.getEnd().v());
		});
	}

	private static void setSunday(WorkingCondition2Dto dto, SingleDaySchedule sunday) {
		dto.setSundayWorkTypeCode(sunday.getWorkTypeCode().v());

		sunday.getWorkTimeCode().ifPresent(wtc -> dto.setSundayWorkTimeCode(wtc.v()));

		Optional<TimeZone> timeZone1 = sunday.getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 1)
				.findFirst();
		Optional<TimeZone> timeZone2 = sunday.getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 2)
				.findFirst();

		timeZone1.ifPresent(tz -> {
			dto.setSundayStartTime1(tz.getStart().v());
			dto.setSundayEndTime1(tz.getEnd().v());
		});

		timeZone2.ifPresent(tz -> {
			dto.setSundayStartTime2(tz.getStart().v());
			dto.setSundayEndTime2(tz.getEnd().v());
		});
	}

	private static void setMonday(WorkingCondition2Dto dto, SingleDaySchedule monday) {
		dto.setMondayWorkTypeCode(monday.getWorkTypeCode().v());

		monday.getWorkTimeCode().ifPresent(wtc -> dto.setMondayWorkTimeCode(wtc.v()));

		Optional<TimeZone> timeZone1 = monday.getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 1)
				.findFirst();
		Optional<TimeZone> timeZone2 = monday.getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 2)
				.findFirst();

		timeZone1.ifPresent(tz -> {
			dto.setMondayStartTime1(tz.getStart().v());
			dto.setMondayEndTime1(tz.getEnd().v());
		});

		timeZone2.ifPresent(tz -> {
			dto.setMondayStartTime2(tz.getStart().v());
			dto.setMondayEndTime2(tz.getEnd().v());
		});
	}

	private static void setTuesday(WorkingCondition2Dto dto, SingleDaySchedule tuesday) {
		dto.setTuesdayWorkTypeCode(tuesday.getWorkTypeCode().v());

		tuesday.getWorkTimeCode().ifPresent(wtc -> dto.setTuesdayWorkTimeCode(wtc.v()));

		Optional<TimeZone> timeZone1 = tuesday.getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 1)
				.findFirst();
		Optional<TimeZone> timeZone2 = tuesday.getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 2)
				.findFirst();

		timeZone1.ifPresent(tz -> {
			dto.setTuesdayStartTime1(tz.getStart().v());
			dto.setTuesdayEndTime1(tz.getEnd().v());
		});

		timeZone2.ifPresent(tz -> {
			dto.setTuesdayStartTime2(tz.getStart().v());
			dto.setTuesdayEndTime2(tz.getEnd().v());
		});
	}

	private static void setWednesday(WorkingCondition2Dto dto, SingleDaySchedule wednesday) {
		dto.setWednesdayWorkTypeCode(wednesday.getWorkTypeCode().v());

		wednesday.getWorkTimeCode().ifPresent(wtc -> dto.setWednesdayWorkTimeCode(wtc.v()));

		Optional<TimeZone> timeZone1 = wednesday.getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 1)
				.findFirst();
		Optional<TimeZone> timeZone2 = wednesday.getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 2)
				.findFirst();

		timeZone1.ifPresent(tz -> {
			dto.setWednesdayStartTime1(tz.getStart().v());
			dto.setWednesdayEndTime1(tz.getEnd().v());
		});

		timeZone2.ifPresent(tz -> {
			dto.setWednesdayStartTime2(tz.getStart().v());
			dto.setWednesdayEndTime2(tz.getEnd().v());
		});
	}

	private static void setThursday(WorkingCondition2Dto dto, SingleDaySchedule thursday) {
		dto.setThursdayWorkTypeCode(thursday.getWorkTypeCode().v());

		thursday.getWorkTimeCode().ifPresent(wtc -> {
			dto.setThursdayWorkTimeCode(wtc.v());
		});

		Optional<TimeZone> timeZone1 = thursday.getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 1)
				.findFirst();
		Optional<TimeZone> timeZone2 = thursday.getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 2)
				.findFirst();

		timeZone1.ifPresent(tz -> {
			dto.setThursdayStartTime1(tz.getStart().v());
			dto.setThursdayEndTime1(tz.getEnd().v());
		});

		timeZone2.ifPresent(tz -> {
			dto.setThursdayStartTime2(tz.getStart().v());
			dto.setThursdayEndTime2(tz.getEnd().v());
		});
	}

	private static void setFriday(WorkingCondition2Dto dto, SingleDaySchedule friday) {
		dto.setFridayWorkTypeCode(friday.getWorkTypeCode().v());

		friday.getWorkTimeCode().ifPresent(wtc -> {
			dto.setFridayWorkTimeCode(wtc.v());
		});

		Optional<TimeZone> timeZone1 = friday.getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 1)
				.findFirst();
		Optional<TimeZone> timeZone2 = friday.getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 2)
				.findFirst();

		timeZone1.ifPresent(tz -> {
			dto.setFridayStartTime1(tz.getStart().v());
			dto.setFridayEndTime1(tz.getEnd().v());
		});

		timeZone2.ifPresent(tz -> {
			dto.setFridayStartTime2(tz.getStart().v());
			dto.setFridayEndTime2(tz.getEnd().v());
		});
	}

	private static void setSaturday(WorkingCondition2Dto dto, SingleDaySchedule saturday) {
		dto.setSaturdayWorkTypeCode(saturday.getWorkTypeCode().v());

		saturday.getWorkTimeCode().ifPresent(wtc -> {
			dto.setSaturdayWorkTimeCode(wtc.v());
		});

		Optional<TimeZone> timeZone1 = saturday.getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 1)
				.findFirst();
		Optional<TimeZone> timeZone2 = saturday.getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 2)
				.findFirst();

		timeZone1.ifPresent(tz -> {
			dto.setSaturdayStartTime1(tz.getStart().v());
			dto.setSaturdayEndTime1(tz.getEnd().v());
		});

		timeZone2.ifPresent(tz -> {
			dto.setSaturdayStartTime2(tz.getStart().v());
			dto.setSaturdayEndTime2(tz.getEnd().v());
		});
	}
}
