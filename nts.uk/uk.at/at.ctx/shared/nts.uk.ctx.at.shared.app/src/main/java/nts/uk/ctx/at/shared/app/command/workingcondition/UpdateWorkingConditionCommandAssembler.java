package nts.uk.ctx.at.shared.app.command.workingcondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.workingcondition.BreakdownTimeDay;
import nts.uk.ctx.at.shared.dom.workingcondition.LaborContractTime;
import nts.uk.ctx.at.shared.dom.workingcondition.ManageAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.MonthlyPatternCode;
import nts.uk.ctx.at.shared.dom.workingcondition.MonthlyPatternWorkScheduleCre;
import nts.uk.ctx.at.shared.dom.workingcondition.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalDayOfWeek;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategory;
import nts.uk.ctx.at.shared.dom.workingcondition.ScheduleMethod;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDaySchedule;
import nts.uk.ctx.at.shared.dom.workingcondition.TimeZone;
import nts.uk.ctx.at.shared.dom.workingcondition.TimeZoneScheduledMasterAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleBusCal;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleMasterReferenceAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.shr.pereg.app.command.MyCustomizeException;

@Stateless
public class UpdateWorkingConditionCommandAssembler {
	public WorkingConditionItem fromDTO(UpdateWorkingConditionCommand command){
		List<TimeZone> listTimeZone = new ArrayList<>();

		// ------------------ PersonalWorkCategory
		/** The weekday time. */
		// 平日時
		listTimeZone = WorkingConditionCommandUtils.getTimeZone(command.getWeekDayStartTime1(),
				command.getWeekDayEndTime1(), command.getWeekDayStartTime2(), command.getWeekDayEndTime2());
		SingleDaySchedule weekdaySchedule = new SingleDaySchedule(command.getWeekdayWorkTypeCode(), listTimeZone,
				WorkingConditionCommandUtils.getOptionalWorkTime(command.getWeekdayWorkTimeCode()));

		/** The holiday work. */
		// 休日出勤時
		listTimeZone = WorkingConditionCommandUtils.getTimeZone(command.getWorkInHolidayStartTime1(),
				command.getWorkInHolidayEndTime1(), command.getWorkInHolidayStartTime2(),
				command.getWorkInHolidayEndTime2());
		SingleDaySchedule wholidaySchedule = new SingleDaySchedule(command.getWorkInHolidayWorkTypeCode(), listTimeZone,
				WorkingConditionCommandUtils.getOptionalWorkTime(command.getWorkInHolidayWorkTimeCode()));

		/** The holiday time. */
		// 休日時
		// private SingleDaySchedule holidayTime;
		SingleDaySchedule holiday = new SingleDaySchedule(command.getHolidayWorkTypeCode(), new ArrayList<>(),
				Optional.empty());

		/** The in law break time. */
		// 法内休出時
		listTimeZone = WorkingConditionCommandUtils.getTimeZone(command.getInLawBreakTimeStartTime1(),
				command.getInLawBreakTimeEndTime1(), command.getInLawBreakTimeStartTime2(),
				command.getInLawBreakTimeEndTime2());
		SingleDaySchedule inLawBreakTime = new SingleDaySchedule(command.getInLawBreakTimeWorkTypeCode(), listTimeZone,
				WorkingConditionCommandUtils.getOptionalWorkTime(command.getInLawBreakTimeWorkTimeCode()));

		/** The outside law break time. */
		// 法外休出時
		listTimeZone = WorkingConditionCommandUtils.getTimeZone(command.getOutsideLawBreakTimeStartTime1(),
				command.getOutsideLawBreakTimeEndTime1(), command.getOutsideLawBreakTimeStartTime2(),
				command.getOutsideLawBreakTimeEndTime2());
		SingleDaySchedule outLawBreakTime = new SingleDaySchedule(command.getOutsideLawBreakTimeWorkTypeCode(),
				listTimeZone,
				WorkingConditionCommandUtils.getOptionalWorkTime(command.getOutsideLawBreakTimeWorkTimeCode()));

		/** The holiday attendance time. */
		// 祝日出勤時
		listTimeZone = WorkingConditionCommandUtils.getTimeZone(command.getHolidayAttendanceTimeStartTime1(),
				command.getHolidayAttendanceTimeEndTime1(), command.getHolidayAttendanceTimeStartTime2(),
				command.getHolidayAttendanceTimeEndTime2());
		SingleDaySchedule holidayAttendance = new SingleDaySchedule(command.getHolidayAttendanceTimeWorkTypeCode(),
				listTimeZone,
				WorkingConditionCommandUtils.getOptionalWorkTime(command.getHolidayAttendanceTimeWorkTimeCode()));

		/** The public holiday work. */
		// 公休出勤時
		listTimeZone = WorkingConditionCommandUtils.getTimeZone(command.getWorkInPublicHolidayStartTime1(),
				command.getWorkInPublicHolidayEndTime1(), command.getWorkInPublicHolidayStartTime2(),
				command.getWorkInPublicHolidayEndTime2());
		SingleDaySchedule wiPublicHoliday = new SingleDaySchedule(command.getWorkInPublicHolidayWorkTypeCode(),
				listTimeZone,
				WorkingConditionCommandUtils.getOptionalWorkTime(command.getWorkInPublicHolidayWorkTimeCode()));
	
		PersonalWorkCategory workCategory = new PersonalWorkCategory(weekdaySchedule, wholidaySchedule, holiday,
				WorkingConditionCommandUtils.getOptionalSingleDay(inLawBreakTime),
				WorkingConditionCommandUtils.getOptionalSingleDay(outLawBreakTime),
				WorkingConditionCommandUtils.getOptionalSingleDay(holidayAttendance),
				WorkingConditionCommandUtils.getOptionalSingleDay(wiPublicHoliday));
				// -------------------------------
		
		
		// ----------------------- BreakdownTimeDay
		BreakdownTimeDay holidayAddTimeSet = new BreakdownTimeDay(command.getOneDay() != null?
				new AttendanceTime(command.getOneDay().intValue()):null,
				command.getMorning() != null? new AttendanceTime(command.getMorning().intValue()): null,
				command.getAfternoon() != null ? new AttendanceTime(command.getAfternoon().intValue()):null);
		// ------------------------
		
		// ScheduleMethod
		// WorkScheduleBusCal - 営業日カレンダーによる勤務予定作成
		WorkScheduleBusCal businessCal = new WorkScheduleBusCal(
				command.getReferenceBusinessDayCalendar() != null
						? EnumAdaptor.valueOf(command.getReferenceBusinessDayCalendar().intValue() ,
						WorkScheduleMasterReferenceAtr.class) : null,
						command.getReferenceBasicWork() != null ? EnumAdaptor.valueOf(command.getReferenceBasicWork().intValue(),
						WorkScheduleMasterReferenceAtr.class) : null,
						// 予定作成方法.営業日カレンダーによる勤務予定作成.就業時間帯の参照先（基本作成方法＝営業部カレンダーの場合）
						command.getReferenceType() != null ? EnumAdaptor.valueOf(command.getReferenceType().intValue(),
						TimeZoneScheduledMasterAtr.class) : null);
		// MonthlyPatternWorkScheduleCre 月間パターンの場合
		MonthlyPatternWorkScheduleCre monthlySchedule = new MonthlyPatternWorkScheduleCre(
				command.getReferenceType().intValue());
		ScheduleMethod scheduleMethod = new ScheduleMethod(command.getBasicCreateMethod().intValue(), businessCal,
				monthlySchedule);
		
		WorkingConditionItem workingCond = new WorkingConditionItem(command.getHistId(),
				command.getScheduleManagementAtr() != null ? EnumAdaptor.valueOf( command.getScheduleManagementAtr().intValue(),
						ManageAtr.class) : null,
				null, workCategory,
				command.getAutoStampSetAtr() != null ? EnumAdaptor.valueOf(command.getAutoStampSetAtr().intValue(), NotUseAtr.class) : null,
				command.getAutoIntervalSetAtr() != null ? EnumAdaptor.valueOf(command.getAutoIntervalSetAtr().intValue(),NotUseAtr.class) : null,
				command.getEmployeeId(),
				command.getVacationAddedTimeAtr() != null ? EnumAdaptor.valueOf(command.getVacationAddedTimeAtr().intValue(),NotUseAtr.class) : null,
				// Default vaule is 0
				command.getContractTime() != null ? new LaborContractTime(command.getContractTime().intValue()) : null,
						command.getLaborSystem() != null ? EnumAdaptor.valueOf(command.getLaborSystem().intValue(),
						WorkingSystem.class) : null,
				holidayAddTimeSet, scheduleMethod, command.getHourlyPaymentAtr() != null? command.getHourlyPaymentAtr().intValue() : null,
				command.getTimeApply() != null ? new BonusPaySettingCode(command.getTimeApply()) : null,
				command.getMonthlyPattern() != null ? new MonthlyPatternCode(command.getMonthlyPattern()) : null);
		return workingCond;
	}
	
	public WokingConditionCommandCustom fromDTOCustom(UpdateWorkingConditionCommand command){
		TimeZoneCustom  timezone = new TimeZoneCustom();
		List<MyCustomizeException> exLst = new ArrayList<>();

		// ------------------ PersonalWorkCategory
		/** The weekday time. */
		// 平日時
		timezone = WorkingConditionCommandUtils.getCustomTimeZone(command.getEmployeeId(), "平日の終了時刻1","平日の勤務時間1", 
				command.getWeekDayStartTime1(),
				command.getWeekDayEndTime1(), command.getWeekDayStartTime2(), command.getWeekDayEndTime2());
		SingleDaySchedule weekdaySchedule = new SingleDaySchedule(command.getWeekdayWorkTypeCode(), timezone.getTimezoneLst(),
				WorkingConditionCommandUtils.getOptionalWorkTime(command.getWeekdayWorkTimeCode()));
		if(!CollectionUtil.isEmpty(timezone.getErrors())) {
			exLst.addAll(timezone.getErrors());
		}
		
		/** The holiday work. */
		// 休日出勤時
		timezone = WorkingConditionCommandUtils.getCustomTimeZone(command.getEmployeeId(), "休出の終了時刻1","休出の勤務時間1",
				command.getWorkInHolidayStartTime1(),
				command.getWorkInHolidayEndTime1(), command.getWorkInHolidayStartTime2(),
				command.getWorkInHolidayEndTime2());
		SingleDaySchedule wholidaySchedule = new SingleDaySchedule(command.getWorkInHolidayWorkTypeCode(), timezone.getTimezoneLst(),
				WorkingConditionCommandUtils.getOptionalWorkTime(command.getWorkInHolidayWorkTimeCode()));
		if(!CollectionUtil.isEmpty(timezone.getErrors())) {
			exLst.addAll(timezone.getErrors());
		}
		
		/** The holiday time. */
		// 休日時
		// private SingleDaySchedule holidayTime;
		SingleDaySchedule holiday = new SingleDaySchedule(command.getHolidayWorkTypeCode(), new ArrayList<>(),
				Optional.empty());

		/** The in law break time. */
		// 法内休出時
		timezone = WorkingConditionCommandUtils.getCustomTimeZone(command.getEmployeeId(), "法定休出の終了時刻1", "法定休出の勤務時間1",
				command.getInLawBreakTimeStartTime1(),
				command.getInLawBreakTimeEndTime1(), command.getInLawBreakTimeStartTime2(),
				command.getInLawBreakTimeEndTime2());
		SingleDaySchedule inLawBreakTime = new SingleDaySchedule(command.getInLawBreakTimeWorkTypeCode(), timezone.getTimezoneLst(),
				WorkingConditionCommandUtils.getOptionalWorkTime(command.getInLawBreakTimeWorkTimeCode()));
		if(!CollectionUtil.isEmpty(timezone.getErrors())) {
			exLst.addAll(timezone.getErrors());
		}
		
		/** The outside law break time. */
		// 法外休出時
		timezone = WorkingConditionCommandUtils.getCustomTimeZone(command.getEmployeeId(), "法定外休出の終了時刻1","法定外休出の勤務時間1",
				command.getOutsideLawBreakTimeStartTime1(),
				command.getOutsideLawBreakTimeEndTime1(), command.getOutsideLawBreakTimeStartTime2(),
				command.getOutsideLawBreakTimeEndTime2());
		SingleDaySchedule outLawBreakTime = new SingleDaySchedule(command.getOutsideLawBreakTimeWorkTypeCode(),
				timezone.getTimezoneLst(),
				WorkingConditionCommandUtils.getOptionalWorkTime(command.getOutsideLawBreakTimeWorkTimeCode()));
		if(!CollectionUtil.isEmpty(timezone.getErrors())) {
			exLst.addAll(timezone.getErrors());
		}
		
		/** The holiday attendance time. */
		// 祝日出勤時
		timezone = WorkingConditionCommandUtils.getCustomTimeZone(command.getEmployeeId(), "法定外祝日の終了時刻1", "法定外祝日の勤務時間1", 
				command.getHolidayAttendanceTimeStartTime1(),
				command.getHolidayAttendanceTimeEndTime1(), command.getHolidayAttendanceTimeStartTime2(),
				command.getHolidayAttendanceTimeEndTime2());
		SingleDaySchedule holidayAttendance = new SingleDaySchedule(command.getHolidayAttendanceTimeWorkTypeCode(),
				timezone.getTimezoneLst(),
				WorkingConditionCommandUtils.getOptionalWorkTime(command.getHolidayAttendanceTimeWorkTimeCode()));
		if(!CollectionUtil.isEmpty(timezone.getErrors())) {
			exLst.addAll(timezone.getErrors());
		}
		
		/** The public holiday work. */
		// 公休出勤時
		timezone = WorkingConditionCommandUtils.getCustomTimeZone(command.getEmployeeId(), "公休休出の終了時刻1", "公休休出の勤務時間1",
				command.getWorkInPublicHolidayStartTime1(),
				command.getWorkInPublicHolidayEndTime1(), command.getWorkInPublicHolidayStartTime2(),
				command.getWorkInPublicHolidayEndTime2());
		SingleDaySchedule wiPublicHoliday = new SingleDaySchedule(command.getWorkInPublicHolidayWorkTypeCode(),
				timezone.getTimezoneLst(),
				WorkingConditionCommandUtils.getOptionalWorkTime(command.getWorkInPublicHolidayWorkTimeCode()));
		if(!CollectionUtil.isEmpty(timezone.getErrors())) {
			exLst.addAll(timezone.getErrors());
		}
		
		PersonalWorkCategory workCategory = new PersonalWorkCategory(weekdaySchedule, wholidaySchedule, holiday,
				WorkingConditionCommandUtils.getOptionalSingleDay(inLawBreakTime),
				WorkingConditionCommandUtils.getOptionalSingleDay(outLawBreakTime),
				WorkingConditionCommandUtils.getOptionalSingleDay(holidayAttendance),
				WorkingConditionCommandUtils.getOptionalSingleDay(wiPublicHoliday));
		// -------------------------------
		
		
		// ----------------------- BreakdownTimeDay
		BreakdownTimeDay holidayAddTimeSet = new BreakdownTimeDay(command.getOneDay() != null?
				new AttendanceTime(command.getOneDay().intValue()):null,
				command.getMorning() != null? new AttendanceTime(command.getMorning().intValue()): null,
				command.getAfternoon() != null ? new AttendanceTime(command.getAfternoon().intValue()):null);
		// ------------------------
		
		// ScheduleMethod
		// WorkScheduleBusCal - 営業日カレンダーによる勤務予定作成
		WorkScheduleBusCal businessCal = new WorkScheduleBusCal(
				command.getReferenceBusinessDayCalendar() != null
						? EnumAdaptor.valueOf(command.getReferenceBusinessDayCalendar().intValue() ,
						WorkScheduleMasterReferenceAtr.class) : null,
						command.getReferenceBasicWork() != null ? EnumAdaptor.valueOf(command.getReferenceBasicWork().intValue(),
						WorkScheduleMasterReferenceAtr.class) : null,
						// 予定作成方法.営業日カレンダーによる勤務予定作成.就業時間帯の参照先（基本作成方法＝営業部カレンダーの場合）
						command.getReferenceType() != null ? EnumAdaptor.valueOf(command.getReferenceType().intValue(),
						TimeZoneScheduledMasterAtr.class) : null);
		// MonthlyPatternWorkScheduleCre 月間パターンの場合
		MonthlyPatternWorkScheduleCre monthlySchedule = new MonthlyPatternWorkScheduleCre(
				command.getReferenceType().intValue());
		ScheduleMethod scheduleMethod = new ScheduleMethod(command.getBasicCreateMethod().intValue(), businessCal,
				monthlySchedule);
		
		WorkingConditionItem workingCond = new WorkingConditionItem(command.getHistId(),
				command.getScheduleManagementAtr() != null ? EnumAdaptor.valueOf( command.getScheduleManagementAtr().intValue(),
						ManageAtr.class) : null,
				null, workCategory,
				command.getAutoStampSetAtr() != null ? EnumAdaptor.valueOf(command.getAutoStampSetAtr().intValue(), NotUseAtr.class) : null,
				command.getAutoIntervalSetAtr() != null ? EnumAdaptor.valueOf(command.getAutoIntervalSetAtr().intValue(),NotUseAtr.class) : null,
				command.getEmployeeId(),
				command.getVacationAddedTimeAtr() != null ? EnumAdaptor.valueOf(command.getVacationAddedTimeAtr().intValue(),NotUseAtr.class) : null,
				// Default vaule is 0
				command.getContractTime() != null ? new LaborContractTime(command.getContractTime().intValue()) : null,
						command.getLaborSystem() != null ? EnumAdaptor.valueOf(command.getLaborSystem().intValue(),
						WorkingSystem.class) : null,
				holidayAddTimeSet, scheduleMethod, command.getHourlyPaymentAtr() != null? command.getHourlyPaymentAtr().intValue() : null,
				command.getTimeApply() != null ? new BonusPaySettingCode(command.getTimeApply()) : null,
				command.getMonthlyPattern() != null ? new MonthlyPatternCode(command.getMonthlyPattern()) : null);
		return new WokingConditionCommandCustom(workingCond, exLst);
	}
	
	
	public WokingConditionCommandCustom fromDTO2Custom(UpdateWorkingCondition2Command command){
		TimeZoneCustom  timezone = new TimeZoneCustom();
		List<MyCustomizeException> exLst = new ArrayList<>();
		// ---------------------- PersonalDayOfWeek
		/** The monday. */
		// 月曜日
		timezone = WorkingConditionCommandUtils.getCustomTimeZone(command.getEmployeeId(), "月曜の終了時刻1","月曜の勤務時間1",
				command.getMondayStartTime1(),
				command.getMondayEndTime1(), command.getMondayStartTime2(), command.getMondayEndTime2());
		SingleDaySchedule mondaySchedule = new SingleDaySchedule(command.getMondayWorkTypeCode(), timezone.getTimezoneLst(),
				WorkingConditionCommandUtils.getOptionalWorkTime(command.getMondayWorkTimeCode()));
		if (!CollectionUtil.isEmpty(timezone.getErrors())) {
			exLst.addAll(timezone.getErrors());
		}
		
		/** The tuesday. */
		// 火曜日
		timezone = WorkingConditionCommandUtils.getCustomTimeZone(command.getEmployeeId(), "火曜の終了時刻1","月曜の勤務時間1",
				command.getTuesdayStartTime1(),
				command.getTuesdayEndTime1(), command.getTuesdayStartTime2(), command.getTuesdayEndTime2());
		SingleDaySchedule tuesdaySchedule = new SingleDaySchedule(command.getTuesdayWorkTypeCode(), timezone.getTimezoneLst(),
				WorkingConditionCommandUtils.getOptionalWorkTime(command.getTuesdayWorkTimeCode()));
		if(!CollectionUtil.isEmpty(timezone.getErrors())) {
			exLst.addAll(timezone.getErrors());
		}
		
		/** The wednesday. */
		// 水曜日
		timezone = WorkingConditionCommandUtils.getCustomTimeZone(command.getEmployeeId(), "水曜の終了時刻1","水曜の勤務時間1",
				command.getWednesdayStartTime1(),
				command.getWednesdayEndTime1(), command.getWednesdayStartTime2(), command.getWednesdayEndTime2());
		SingleDaySchedule wedSchedule = new SingleDaySchedule(command.getWednesdayWorkTypeCode(), timezone.getTimezoneLst(),
				WorkingConditionCommandUtils.getOptionalWorkTime(command.getWednesdayWorkTimeCode()));
		if(!CollectionUtil.isEmpty(timezone.getErrors())) {
			exLst.addAll(timezone.getErrors());
		}
		
		/** The thursday. */
		// 木曜日
		timezone = WorkingConditionCommandUtils.getCustomTimeZone(command.getEmployeeId(), "木曜の終了時刻1","木曜の勤務時間1",
				command.getThursdayStartTime1(),
				command.getThursdayEndTime1(), command.getThursdayStartTime2(), command.getThursdayEndTime2());
		SingleDaySchedule thurSchedule = new SingleDaySchedule(command.getThursdayWorkTypeCode(), timezone.getTimezoneLst(),
				WorkingConditionCommandUtils.getOptionalWorkTime(command.getThursdayWorkTimeCode()));
		if(!CollectionUtil.isEmpty(timezone.getErrors())) {
			exLst.addAll(timezone.getErrors());
		}
		
		/** The friday. */
		// 金曜日
		timezone = WorkingConditionCommandUtils.getCustomTimeZone(command.getEmployeeId(), "金曜の終了時刻1","金曜の勤務時間1",
				command.getFridayStartTime1(),
				command.getFridayEndTime1(), command.getFridayStartTime2(), command.getFridayEndTime2());
		SingleDaySchedule friSchedule = new SingleDaySchedule(command.getFridayWorkTypeCode(), timezone.getTimezoneLst(),
				WorkingConditionCommandUtils.getOptionalWorkTime(command.getFridayWorkTimeCode()));
		if(!CollectionUtil.isEmpty(timezone.getErrors())) {
			exLst.addAll(timezone.getErrors());
		}
		
		/** The saturday. */
		// 土曜日
		timezone = WorkingConditionCommandUtils.getCustomTimeZone(command.getEmployeeId(), "土曜の終了時刻1","土曜の勤務時間1",
				command.getSaturdayStartTime1(),
				command.getSaturdayEndTime1(), command.getSaturdayStartTime2(), command.getSaturdayEndTime2());
		SingleDaySchedule satSchedule = new SingleDaySchedule(command.getSaturdayWorkTypeCode(), timezone.getTimezoneLst(),
				WorkingConditionCommandUtils.getOptionalWorkTime(command.getSaturdayWorkTimeCode()));
		if(!CollectionUtil.isEmpty(timezone.getErrors())) {
			exLst.addAll(timezone.getErrors());
		}
		
		/** The sunday. */
		// 日曜日
		timezone = WorkingConditionCommandUtils.getCustomTimeZone(command.getEmployeeId(), "日曜の終了時刻1","日曜の勤務時間1",
				command.getSundayStartTime1(),
				command.getSundayEndTime1(), command.getSundayStartTime2(), command.getSundayEndTime2());
		SingleDaySchedule sunSchedule = new SingleDaySchedule(command.getSundayWorkTypeCode(), timezone.getTimezoneLst(),
				WorkingConditionCommandUtils.getOptionalWorkTime(command.getSundayWorkTimeCode()));

		PersonalDayOfWeek workDayOfWeek = new PersonalDayOfWeek(
				WorkingConditionCommandUtils.getOptionalSingleDay(mondaySchedule),
				WorkingConditionCommandUtils.getOptionalSingleDay(tuesdaySchedule),
				WorkingConditionCommandUtils.getOptionalSingleDay(wedSchedule),
				WorkingConditionCommandUtils.getOptionalSingleDay(thurSchedule),
				WorkingConditionCommandUtils.getOptionalSingleDay(friSchedule),
				WorkingConditionCommandUtils.getOptionalSingleDay(satSchedule),
				WorkingConditionCommandUtils.getOptionalSingleDay(sunSchedule));
		// -------------------------

		
		
		WorkingConditionItem workingCond = new WorkingConditionItem(command.getHistId(), null,
				workDayOfWeek, null,
				null,
				null,
				command.getEmployeeId(),
				null,
				null,
				null,
				null, null, null,
				null,
				null);
		return new WokingConditionCommandCustom(workingCond, exLst);
	}
	
	public WorkingConditionItem fromDTO2(UpdateWorkingCondition2Command command){
		List<TimeZone> listTimeZone = new ArrayList<>();
		// ---------------------- PersonalDayOfWeek
		/** The monday. */
		// 月曜日
		listTimeZone = WorkingConditionCommandUtils.getTimeZone(command.getMondayStartTime1(),
				command.getMondayEndTime1(), command.getMondayStartTime2(), command.getMondayEndTime2());
		SingleDaySchedule mondaySchedule = new SingleDaySchedule(command.getMondayWorkTypeCode(), listTimeZone,
				WorkingConditionCommandUtils.getOptionalWorkTime(command.getMondayWorkTimeCode()));

		/** The tuesday. */
		// 火曜日
		listTimeZone = WorkingConditionCommandUtils.getTimeZone(command.getTuesdayStartTime1(),
				command.getTuesdayEndTime1(), command.getTuesdayStartTime2(), command.getTuesdayEndTime2());
		SingleDaySchedule tuesdaySchedule = new SingleDaySchedule(command.getTuesdayWorkTypeCode(), listTimeZone,
				WorkingConditionCommandUtils.getOptionalWorkTime(command.getTuesdayWorkTimeCode()));

		/** The wednesday. */
		// 水曜日
		listTimeZone = WorkingConditionCommandUtils.getTimeZone(command.getWednesdayStartTime1(),
				command.getWednesdayEndTime1(), command.getWednesdayStartTime2(), command.getWednesdayEndTime2());
		SingleDaySchedule wedSchedule = new SingleDaySchedule(command.getWednesdayWorkTypeCode(), listTimeZone,
				WorkingConditionCommandUtils.getOptionalWorkTime(command.getWednesdayWorkTimeCode()));

		/** The thursday. */
		// 木曜日
		listTimeZone = WorkingConditionCommandUtils.getTimeZone(command.getThursdayStartTime1(),
				command.getThursdayEndTime1(), command.getThursdayStartTime2(), command.getThursdayEndTime2());
		SingleDaySchedule thurSchedule = new SingleDaySchedule(command.getThursdayWorkTypeCode(), listTimeZone,
				WorkingConditionCommandUtils.getOptionalWorkTime(command.getThursdayWorkTimeCode()));

		/** The friday. */
		// 金曜日
		listTimeZone = WorkingConditionCommandUtils.getTimeZone(command.getFridayStartTime1(),
				command.getFridayEndTime1(), command.getFridayStartTime2(), command.getFridayEndTime2());
		SingleDaySchedule friSchedule = new SingleDaySchedule(command.getFridayWorkTypeCode(), listTimeZone,
				WorkingConditionCommandUtils.getOptionalWorkTime(command.getFridayWorkTimeCode()));

		/** The saturday. */
		// 土曜日
		listTimeZone = WorkingConditionCommandUtils.getTimeZone(command.getSaturdayStartTime1(),
				command.getSaturdayEndTime1(), command.getSaturdayStartTime2(), command.getSaturdayEndTime2());
		SingleDaySchedule satSchedule = new SingleDaySchedule(command.getSaturdayWorkTypeCode(), listTimeZone,
				WorkingConditionCommandUtils.getOptionalWorkTime(command.getSaturdayWorkTimeCode()));

		/** The sunday. */
		// 日曜日
		listTimeZone = WorkingConditionCommandUtils.getTimeZone(command.getSundayStartTime1(),
				command.getSundayEndTime1(), command.getSundayStartTime2(), command.getSundayEndTime2());
		SingleDaySchedule sunSchedule = new SingleDaySchedule(command.getSundayWorkTypeCode(), listTimeZone,
				WorkingConditionCommandUtils.getOptionalWorkTime(command.getSundayWorkTimeCode()));

		PersonalDayOfWeek workDayOfWeek = new PersonalDayOfWeek(
				WorkingConditionCommandUtils.getOptionalSingleDay(mondaySchedule),
				WorkingConditionCommandUtils.getOptionalSingleDay(tuesdaySchedule),
				WorkingConditionCommandUtils.getOptionalSingleDay(wedSchedule),
				WorkingConditionCommandUtils.getOptionalSingleDay(thurSchedule),
				WorkingConditionCommandUtils.getOptionalSingleDay(friSchedule),
				WorkingConditionCommandUtils.getOptionalSingleDay(satSchedule),
				WorkingConditionCommandUtils.getOptionalSingleDay(sunSchedule));
		// -------------------------

		
		
		WorkingConditionItem workingCond = new WorkingConditionItem(command.getHistId(), null,
				workDayOfWeek, null,
				null,
				null,
				command.getEmployeeId(),
				null,
				null,
				null,
				null, null, null,
				null,
				null);
		return workingCond;
	}
	
	

}
