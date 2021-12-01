package nts.uk.ctx.at.shared.app.find.workingcondition;

import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
//import nts.uk.ctx.at.shared.dom.workingcondition.PersonalDayOfWeek;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategory;
import nts.uk.ctx.at.shared.dom.workingcondition.ScheduleMethod;
import nts.uk.ctx.at.shared.dom.workingcondition.TimeZone;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkTypeByIndividualWorkDay;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Setter
public class WorkingConditionDto extends PeregDomainDto {
	/**
	 * 期間
	 */
	@PeregItem("IS00118")
	private String period;

	/**
	 * 開始日
	 */
	@Getter
	@PeregItem("IS00119")
	private GeneralDate startDate;

	/**
	 * 終了日
	 */
	@PeregItem("IS00120")
	private GeneralDate endDate;

	/**
	 * スケ管理区分 予定管理区分
	 */
	@PeregItem("IS00121")
	private int scheduleManagementAtr;

	/**
	 * 勤務予定作成方法
	 */
	// @PeregItem("IS00122")

	/**
	 * 基本作成方法 予定作成方法.基本作成方法
	 */
	@PeregItem("IS00123")
	private int basicCreateMethod;

	/**
	 * 就業時間帯の参照先 予定作成方法.月間パターンによる勤務予定作成.勤務種類と就業時間帯の参照先
	 */
	@PeregItem("IS00126")
	private Integer referenceType;

	/**
	 * 月間パターンCD
	 */
	@PeregItem("IS00127")
	private String monthlyPattern;

	/**
	 * 休日勤種CD 区分別勤務.休日時.勤務種類コード
	 */
	@PeregItem("IS00128")
	private String holidayWorkTypeCode;

	/**
	 * 平日時勤務設定
	 */
	// @PeregItem("IS00129")

	/**
	 * 平日勤種CD 区分別勤務.平日時.勤務種類コード
	 *
	 */
	@PeregItem("IS00130")
	private String weekdayWorkTypeCode;

	/**
	 * 平日就時CD 区分別勤務.平日時.就業時間帯コード
	 */
	@PeregItem("IS00131")
	private String weekdayWorkTimeCode;

//	/**
//	 * 平日時勤務時間1
//	 * 
//	 */
//	@PeregItem("IS00132")
//	private String weekDay1;
//
//	/**
//	 * 平日開始1 区分別勤務.平日時.勤務時間帯.開始 ※回数=1
//	 */
//	@PeregItem("IS00133")
//	private Integer weekDayStartTime1;
//
//	/**
//	 * 平日終了1 区分別勤務.平日時.勤務時間帯.終了 ※回数=1
//	 */
//	@PeregItem("IS00134")
//	private Integer weekDayEndTime1;
//
//	/**
//	 * 平日時勤務時間2
//	 */
//	@PeregItem("IS00135")
//	private String weekDay2;
//
//	/**
//	 * 平日開始2 区分別勤務.平日時.勤務時間帯.開始 ※回数=2
//	 */
//	@PeregItem("IS00136")
//	private Integer weekDayStartTime2;
//
//	/**
//	 * 平日終了2 区分別勤務.平日時.勤務時間帯.終了 ※回数=2
//	 */
//	@PeregItem("IS00137")
//	private Integer weekDayEndTime2;

	/**
	 * 休出時勤務設定
	 */
	// @PeregItem("IS00138")

	/**
	 * 休出勤種CD 区分別勤務.休日出勤時.勤務種類コード
	 */
	@PeregItem("IS00139")
	private String workInHolidayWorkTypeCode;

	/**
	 * 休出就時CD 区分別勤務.休日出勤時.就業時間帯コード
	 */
	@PeregItem("IS00140")
	private String workInHolidayWorkTimeCode;

	/**
	 * 法定休出時勤務設定
	 */
	// @PeregItem("IS00156")

	/**
	 * 法内休出勤種CD 区分別勤務.法内休出時.勤務種類コード
	 */
	@PeregItem("IS00157")
	private String inLawBreakTimeWorkTypeCode;

	/**
	 * 法定外休出時勤務設定
	 */
	// @PeregItem("IS00165")

	/**
	 * 法外休出勤種CD 区分別勤務.法外休出時.勤務種類コード
	 */
	@PeregItem("IS00166")
	private String outsideLawBreakTimeWorkTypeCode;

	/**
	 * 祝日時勤務設定
	 */
	// @PeregItem("IS00174")

	/**
	 * 法外祝日勤種CD 区分別勤務.祝日出勤時.勤務種類コード
	 */
	@PeregItem("IS00175")
	private String holidayAttendanceTimeWorkTypeCode;
	
	/**
	 * 加給CD
	 */
	// @PeregItem("IS00246")

	/**
	 * 加算時間利用区分 休暇加算時間利用区分
	 */
	@PeregItem("IS00248")
	private Integer vacationAddedTimeAtr;

	/**
	 * 加算時間１日 休暇加算時間設定.１日
	 */
	@PeregItem("IS00249")
	private Integer oneDay;

	/**
	 * 加算時間ＡＭ 休暇加算時間設定.午前
	 */
	@PeregItem("IS00250")
	private Integer morning;

	/**
	 * 加算時間ＰＭ 休暇加算時間設定.午後
	 */
	@PeregItem("IS00251")
	private Integer afternoon;

	/**
	 * 就業区分 労働制
	 */
	@PeregItem("IS00252")
	private Integer laborSystem;

	/**
	 * 契約時間 契約時間
	 */
	@PeregItem("IS00253")
	private Integer contractTime;

	/** The auto stamp set atr. */
	// 自動打刻セット区分
	@PeregItem("IS00258")
	private int autoStampSetAtr;

	/* The hourly ppayment atr. */
	// 時給者区分
	@PeregItem("IS00259")
	private Integer hourlyPaymentAtr;

	/* The time apply. */
	// 加給時間帯
	@PeregItem("IS00246")
	private String timeApply;

	public WorkingConditionDto(String recordId) {
		super(recordId);
	}

	public static WorkingConditionDto createWorkingConditionDto(DateHistoryItem dateHistoryItem,
			WorkingConditionItem workingConditionItem) {
		WorkingConditionDto dto = new WorkingConditionDto(dateHistoryItem.identifier());

		dto.setRecordId(dateHistoryItem.identifier());
		dto.setStartDate(dateHistoryItem.start());
		dto.setEndDate(dateHistoryItem.end());

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

		if (workingConditionItem.getScheduleMethod().isPresent()) {
			setScheduleMethod(dto, workingConditionItem.getScheduleMethod().get());
		}

		PersonalWorkCategory workTime = workingConditionItem.getWorkCategory().getWorkTime();
		WorkTypeByIndividualWorkDay workType = workingConditionItem.getWorkCategory().getWorkType();
		
		// 休日出勤時
		setHolidayTime(dto, workType.getHolidayTimeWTypeCode());

		// 平日時
		setWeekDay(dto, workType.getWeekdayTimeWTypeCode(), workTime);

		// 休日出勤時
		setWorkInHoliday(dto, workType.getHolidayWorkWTypeCode(), workTime);

		if(workType != null){
			// 法内休出時
			workType.getInLawBreakTimeWTypeCode().ifPresent(ilbt -> {
				setInLawBreakTime(dto, ilbt, workTime);
			});

			// 法外休出時
			workType.getOutsideLawBreakTimeWTypeCode().ifPresent(olbt -> {
				setOutsideLawBreakTime(dto, olbt, workTime);
			});

			// 祝日出勤時
			workType.getHolidayAttendanceTimeWTypeCode().ifPresent(at -> {
				setHolidayAttendanceTime(dto, at, workTime);
			});
		} 

//		PersonalDayOfWeek workDayOfWeek = workingConditionItem.getWorkDayOfWeek();

		dto.setVacationAddedTimeAtr(workingConditionItem.getVacationAddedTimeAtr().value);

		workingConditionItem.getHolidayAddTimeSet().ifPresent(hat -> {
			Optional.ofNullable(hat.getOneDay()).ifPresent(od -> dto.setOneDay(od.v()));

			Optional.ofNullable(hat.getMorning()).ifPresent(od -> dto.setMorning(od.v()));

			Optional.ofNullable(hat.getAfternoon()).ifPresent(od -> dto.setAfternoon(od.v()));
		});

		dto.setLaborSystem(workingConditionItem.getLaborSystem().value);
		dto.setContractTime(workingConditionItem.getContractTime().v());
		dto.setAutoStampSetAtr(workingConditionItem.getAutoStampSetAtr().value);

		return dto;
	}
	
	public static WorkingConditionDto createWorkingConditionDtoEnum(DateHistoryItem dateHistoryItem,
			WorkingConditionItem workingConditionItem, Map<String, Object> enums) {
		WorkingConditionDto dto = new WorkingConditionDto(dateHistoryItem.identifier());
		
		dto.setRecordId(dateHistoryItem.identifier());
		dto.setStartDate(dateHistoryItem.start());
		dto.setEndDate(dateHistoryItem.end());

		Integer hourlyPaymentAtr = (Integer) enums.get("IS00259");
		dto.setHourlyPaymentAtr(hourlyPaymentAtr == null? null: hourlyPaymentAtr.intValue());

		workingConditionItem.getTimeApply().ifPresent(wci -> {
			dto.setTimeApply(wci.v());
		});
		
		Integer scheduleManagementAtr = (Integer)enums.get("IS00121");
		dto.setScheduleManagementAtr(scheduleManagementAtr == null? null: scheduleManagementAtr.intValue());

		// 予定作成方法
		workingConditionItem.getMonthlyPattern().ifPresent(mp -> {
			dto.setMonthlyPattern(mp.v());
		});

		if (workingConditionItem.getScheduleMethod().isPresent()) {
			setScheduleMethodCPS013(dto, workingConditionItem.getScheduleMethod().get(), enums);
		}

		PersonalWorkCategory workTime = workingConditionItem.getWorkCategory().getWorkTime();
		WorkTypeByIndividualWorkDay workType = workingConditionItem.getWorkCategory().getWorkType();
		
		// 休日出勤時
		setHolidayTime(dto, workType.getHolidayTimeWTypeCode());

		// 平日時
		setWeekDay(dto, workType.getWeekdayTimeWTypeCode(), workTime);

		// 休日出勤時
		setWorkInHoliday(dto, workType.getHolidayWorkWTypeCode(), workTime);

		if (workType != null) {
			// 法内休出時
			workType.getInLawBreakTimeWTypeCode().ifPresent(ilbt -> {
				setInLawBreakTime(dto, ilbt, workTime);
			});

			// 法外休出時
			workType.getOutsideLawBreakTimeWTypeCode().ifPresent(olbt -> {
				setOutsideLawBreakTime(dto, olbt, workTime);
			});

			// 祝日出勤時
			workType.getHolidayAttendanceTimeWTypeCode().ifPresent(at -> {
				setHolidayAttendanceTime(dto, at, workTime);
			});
		}

		//PersonalDayOfWeek workDayOfWeek = workingConditionItem.getWorkDayOfWeek();
		Integer vacationAddedTimeAtr = (Integer)enums.get("IS00248");
		dto.setVacationAddedTimeAtr(vacationAddedTimeAtr == null? workingConditionItem.getVacationAddedTimeAtr().value: vacationAddedTimeAtr.intValue());

		workingConditionItem.getHolidayAddTimeSet().ifPresent(hat -> {
			Optional.ofNullable(hat.getOneDay()).ifPresent(od -> dto.setOneDay(od.v()));

			Optional.ofNullable(hat.getMorning()).ifPresent(od -> dto.setMorning(od.v()));

			Optional.ofNullable(hat.getAfternoon()).ifPresent(od -> dto.setAfternoon(od.v()));
		});

		Integer laborSystem = (Integer)enums.get("IS00252");
		Integer autoStampSetAtr = (Integer)enums.get("IS00258");
		dto.setLaborSystem(laborSystem == null? workingConditionItem.getLaborSystem().value: laborSystem.intValue());
		dto.setContractTime(workingConditionItem.getContractTime().v());
		dto.setAutoStampSetAtr(autoStampSetAtr == null? workingConditionItem.getAutoStampSetAtr().value: autoStampSetAtr.intValue());
		return dto;
	}

	private static void setScheduleMethod(WorkingConditionDto dto, ScheduleMethod scheduleMethod) {
		dto.setBasicCreateMethod(0);
		switch (scheduleMethod.getBasicCreateMethod()) {
		case BUSINESS_DAY_CALENDAR:
			scheduleMethod.getWorkScheduleBusCal().ifPresent(wsb -> {
				switch (wsb.getReferenceBusinessDayCalendar()) {
				case WORK_PLACE:
					dto.setBasicCreateMethod(1);
					break;
				case CLASSIFICATION:
					dto.setBasicCreateMethod(2);
					break;
				case COMPANY:
				default:
					dto.setBasicCreateMethod(0);
					break;
				}
				dto.setReferenceType(wsb.getReferenceWorkingHours().value);
			});
			break;
		case MONTHLY_PATTERN:
			dto.setBasicCreateMethod(3);
			scheduleMethod.getMonthlyPatternWorkScheduleCre().ifPresent(mps -> {
				dto.setReferenceType(mps.getReferenceType().value);
			});
			break;
		default:
			dto.setBasicCreateMethod(4);
			if (scheduleMethod.getWorkScheduleBusCal().isPresent()) {
				dto.setReferenceType(scheduleMethod.getWorkScheduleBusCal().get().getReferenceWorkingHours().value);
			} else if (scheduleMethod.getMonthlyPatternWorkScheduleCre().isPresent()) {
				dto.setReferenceType(scheduleMethod.getMonthlyPatternWorkScheduleCre().get().getReferenceType().value);
			} else {
				dto.setReferenceType(0);
			}
			break;
		}
	}
	
	private static void setScheduleMethodCPS013(WorkingConditionDto dto, ScheduleMethod scheduleMethod, Map<String, Object> enums) {
		Integer basicCreateMethod = (Integer) enums.get("IS00123");
		dto.setBasicCreateMethod(basicCreateMethod == null? null: basicCreateMethod.intValue());

		switch (basicCreateMethod) {
			
		case 0: // BUSINESS_DAY_CALENDAR
			scheduleMethod.getWorkScheduleBusCal().ifPresent(wsb -> {
				Integer referenceType = (Integer) enums.get("IS00126");
				dto.setReferenceType(referenceType == null? 0: referenceType.intValue());
			});
			break;
		case 1: // MONTHLY_PATTERN
			scheduleMethod.getMonthlyPatternWorkScheduleCre().ifPresent(mps -> {
				Integer referenceType = (Integer) enums.get("IS00126");
				dto.setReferenceType(referenceType == null? 0: referenceType.intValue());
			});
			break;
		default:
			if (scheduleMethod.getWorkScheduleBusCal().isPresent()) {
				Integer referenceType = (Integer) enums.get("IS00126");
				dto.setReferenceType(referenceType == null? 0: referenceType.intValue());
			} else if (scheduleMethod.getMonthlyPatternWorkScheduleCre().isPresent()) {
				Integer referenceType = (Integer) enums.get("IS00126");
				dto.setReferenceType(referenceType == null? 0: referenceType.intValue());
			} else {
				dto.setReferenceType(0);
			}
			break;
		}
	}

	private static void setHolidayTime(WorkingConditionDto dto, WorkTypeCode holidayTimeWTypeCode) {
		dto.setHolidayWorkTypeCode(holidayTimeWTypeCode == null ? null : holidayTimeWTypeCode.v());
	}

	private static void setWeekDay(WorkingConditionDto dto, WorkTypeCode weekdayTimeWTypeCode, PersonalWorkCategory workTime) {
			dto.setWeekdayWorkTypeCode(weekdayTimeWTypeCode == null ? null : weekdayTimeWTypeCode.v());

			workTime.getWeekdayTime().getWorkTimeCode().ifPresent(wt -> dto.setWeekdayWorkTimeCode(wt.v()));

//			Optional<TimeZone> timeZone1 = workTime.getHolidayWork().getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 1)
//					.findFirst();
//			Optional<TimeZone> timeZone2 = workTime.getHolidayWork().getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 2)
//					.findFirst();
//
//			timeZone1.ifPresent(tz -> {
//				dto.setWeekDayStartTime1(tz.getStart().v());
//				dto.setWeekDayEndTime1(tz.getEnd().v());
//			});
//
//			timeZone2.ifPresent(tz -> {
//				dto.setWeekDayStartTime2(tz.getStart().v());
//				dto.setWeekDayEndTime2(tz.getEnd().v());
//			});
	}

	private static void setWorkInHoliday(WorkingConditionDto dto, WorkTypeCode workInHolidayWTypeCode, PersonalWorkCategory workTime) {
		dto.setWorkInHolidayWorkTypeCode(workInHolidayWTypeCode == null ? null : workInHolidayWTypeCode.v());

		workTime.getHolidayWork().getWorkTimeCode().ifPresent(wtc -> dto.setWorkInHolidayWorkTimeCode(wtc.v()));

//		Optional<TimeZone> timeZone1 = workTime.getHolidayWork().getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 1)
//				.findFirst();
//
//		Optional<TimeZone> timeZone2 = workTime.getHolidayWork().getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 2)
//				.findFirst();
//
//		timeZone1.ifPresent(tz -> {
//			dto.setWorkInHolidayStartTime1(tz.getStart().v());
//			dto.setWorkInHolidayEndTime1(tz.getEnd().v());
//		});
//
//		timeZone2.ifPresent(tz -> {
//			dto.setWorkInHolidayStartTime2(tz.getStart().v());
//			dto.setWorkInHolidayEndTime2(tz.getEnd().v());
//		});
	}

	private static void setInLawBreakTime(WorkingConditionDto dto, WorkTypeCode inLawBreakTimeWTypeCode, PersonalWorkCategory workTime) {
		dto.setInLawBreakTimeWorkTypeCode(inLawBreakTimeWTypeCode == null ? null : inLawBreakTimeWTypeCode.v());
	}

	private static void setOutsideLawBreakTime(WorkingConditionDto dto, WorkTypeCode outsideLawBreakTimeWTypeCode, PersonalWorkCategory workTime) {
		dto.setOutsideLawBreakTimeWorkTypeCode(outsideLawBreakTimeWTypeCode == null ?  null : outsideLawBreakTimeWTypeCode.v());
	}

	private static void setHolidayAttendanceTime(WorkingConditionDto dto, WorkTypeCode holidayAttendanceTimeWTypeCode, PersonalWorkCategory workTime) {
		dto.setHolidayAttendanceTimeWorkTypeCode(holidayAttendanceTimeWTypeCode == null ? null : holidayAttendanceTimeWTypeCode.v());
	}

}
