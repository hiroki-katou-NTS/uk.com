package nts.uk.ctx.at.shared.app.command.workingcondition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.workingcondition.AttendanceTime;
import nts.uk.ctx.at.shared.dom.workingcondition.BreakdownTimeDay;
import nts.uk.ctx.at.shared.dom.workingcondition.MonthlyPatternWorkScheduleCre;
import nts.uk.ctx.at.shared.dom.workingcondition.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalDayOfWeek;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategory;
import nts.uk.ctx.at.shared.dom.workingcondition.ScheduleMethod;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDaySchedule;
import nts.uk.ctx.at.shared.dom.workingcondition.TimeZone;
import nts.uk.ctx.at.shared.dom.workingcondition.TimeZoneScheduledMasterAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleBasicCreMethod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleBusCal;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleMasterReferenceAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

@Stateless
public class AddWorkingConditionCommandAssembler {
	public WorkingConditionItem fromDTO(String histId, AddWorkingConditionCommand command){
		List<TimeZone> listTimeZone = new ArrayList<>();
		// ---------------------- PersonalDayOfWeek
		/** The monday. */
		// 月曜日
		listTimeZone = getTimeZone(command.getMondayStartTime1(),command.getMondayEndTime1(),command.getMondayStartTime2(),command.getMondayEndTime2());
		SingleDaySchedule mondaySchedule = new SingleDaySchedule(command.getMondayWorkTypeCode(),listTimeZone, getOptionalWorkTime(command.getMondayWorkTimeCode()));
		
		/** The tuesday. */
		// 火曜日
		listTimeZone = getTimeZone(command.getTuesdayStartTime1(),command.getTuesdayEndTime1(),command.getTuesdayStartTime2(),command.getTuesdayEndTime2());
		SingleDaySchedule tuesdaySchedule = new SingleDaySchedule(command.getTuesdayWorkTypeCode(),listTimeZone, getOptionalWorkTime(command.getTuesdayWorkTimeCode()));
		
		/** The wednesday. */
		// 水曜日
		listTimeZone = getTimeZone(command.getWednesdayStartTime1(),command.getWednesdayEndTime1(),command.getWednesdayStartTime2(),command.getWednesdayEndTime2());
		SingleDaySchedule wedSchedule = new SingleDaySchedule(command.getWednesdayWorkTypeCode(),listTimeZone, getOptionalWorkTime(command.getWednesdayWorkTimeCode()));
		
		/** The thursday. */
		// 木曜日
		listTimeZone =  getTimeZone(command.getThursdayStartTime1(),command.getThursdayEndTime1(),command.getThursdayStartTime2(),command.getThursdayEndTime2());
		SingleDaySchedule thurSchedule = new SingleDaySchedule(command.getThursdayWorkTypeCode(),listTimeZone, getOptionalWorkTime(command.getThursdayWorkTimeCode()));
		
		/** The friday. */
		// 金曜日
		listTimeZone = getTimeZone(command.getFridayStartTime1(),command.getFridayEndTime1(),command.getFridayStartTime2(),command.getFridayEndTime2());
		SingleDaySchedule friSchedule = new SingleDaySchedule(command.getFridayWorkTypeCode(),listTimeZone, getOptionalWorkTime(command.getFridayWorkTimeCode()));
		
		/** The saturday. */
		// 土曜日
		listTimeZone = getTimeZone(command.getSaturdayStartTime1(),command.getSaturdayEndTime1(),command.getSaturdayStartTime2(),command.getSaturdayEndTime2());
		SingleDaySchedule satSchedule = new SingleDaySchedule(command.getSaturdayWorkTypeCode(),listTimeZone, getOptionalWorkTime(command.getSaturdayWorkTimeCode()));
		
		/** The sunday. */
		// 日曜日
		listTimeZone = getTimeZone(command.getSundayStartTime1(),command.getSundayEndTime1(),command.getSundayStartTime2(),command.getSundayEndTime2());
		SingleDaySchedule sunSchedule = new SingleDaySchedule(command.getSundayWorkTypeCode(),listTimeZone, getOptionalWorkTime(command.getSundayWorkTimeCode()));
		
		PersonalDayOfWeek workDayOfWeek = new PersonalDayOfWeek(getOptionalSingleDay(mondaySchedule),getOptionalSingleDay(tuesdaySchedule),getOptionalSingleDay(wedSchedule),getOptionalSingleDay(thurSchedule),getOptionalSingleDay(friSchedule),getOptionalSingleDay(satSchedule),getOptionalSingleDay(sunSchedule));
		//-------------------------
				
				
		// ------------------ PersonalWorkCategory
		/** The weekday time. */
		// 平日時
		listTimeZone = getTimeZone(command.getWeekDayStartTime1(),command.getWeekDayEndTime1(),command.getWeekDayStartTime2(),command.getWeekDayEndTime2());
		SingleDaySchedule weekdaySchedule = new SingleDaySchedule(command.getWeekdayWorkTypeCode(),listTimeZone, getOptionalWorkTime(command.getWeekdayWorkTimeCode()));
		
		/** The holiday work. */
		// 休日出勤時
		listTimeZone = getTimeZone(command.getWorkInHolidayStartTime1(),command.getWorkInHolidayEndTime1(),command.getWorkInHolidayStartTime2(),command.getWorkInHolidayEndTime2());
		SingleDaySchedule wholidaySchedule = new SingleDaySchedule(command.getWorkInHolidayWorkTypeCode(),listTimeZone, getOptionalWorkTime(command.getWorkInHolidayWorkTimeCode()));
		
		/** The holiday time. */
		// 休日時
		// private SingleDaySchedule holidayTime;
		SingleDaySchedule holiday = new SingleDaySchedule(command.getHolidayWorkTypeCode(), new ArrayList<>(), Optional.empty());
		
		/** The in law break time. */
		// 法内休出時
		listTimeZone = getTimeZone(command.getInLawBreakTimeStartTime1(),command.getInLawBreakTimeEndTime1(),command.getInLawBreakTimeStartTime2(),command.getInLawBreakTimeEndTime2());
		SingleDaySchedule inLawBreakTime = new SingleDaySchedule(command.getInLawBreakTimeWorkTypeCode(),listTimeZone, getOptionalWorkTime(command.getInLawBreakTimeWorkTimeCode()));
		
		/** The outside law break time. */
		// 法外休出時
		listTimeZone = getTimeZone(command.getOutsideLawBreakTimeStartTime1(),command.getOutsideLawBreakTimeEndTime1(),command.getOutsideLawBreakTimeStartTime2(),command.getOutsideLawBreakTimeEndTime2());
		SingleDaySchedule outLawBreakTime = new SingleDaySchedule(command.getOutsideLawBreakTimeWorkTypeCode(),listTimeZone, getOptionalWorkTime(command.getOutsideLawBreakTimeWorkTimeCode()));
		
		/** The holiday attendance time. */
		// 祝日出勤時
		listTimeZone = getTimeZone(command.getHolidayAttendanceTimeStartTime1(),command.getHolidayAttendanceTimeEndTime1(),command.getHolidayAttendanceTimeStartTime2(),command.getHolidayAttendanceTimeEndTime2());
		SingleDaySchedule holidayAttendance = new SingleDaySchedule(command.getHolidayAttendanceTimeWorkTypeCode(),listTimeZone, getOptionalWorkTime(command.getHolidayAttendanceTimeWorkTimeCode()));
		
		/** The public holiday work. */
		// 公休出勤時
		listTimeZone = getTimeZone(command.getWorkInPublicHolidayStartTime1(),command.getWorkInPublicHolidayEndTime1(),command.getWorkInPublicHolidayStartTime2(),command.getWorkInPublicHolidayEndTime2());
		SingleDaySchedule wiPublicHoliday = new SingleDaySchedule(command.getWorkInPublicHolidayWorkTypeCode(),listTimeZone, getOptionalWorkTime(command.getWorkInPublicHolidayWorkTimeCode()));
		
		PersonalWorkCategory workCategory = new PersonalWorkCategory(weekdaySchedule,wholidaySchedule,holiday,getOptionalSingleDay(inLawBreakTime),getOptionalSingleDay(outLawBreakTime),getOptionalSingleDay(holidayAttendance),getOptionalSingleDay(wiPublicHoliday));
		// -------------------------------
		
		
		// ----------------------- BreakdownTimeDay
		BreakdownTimeDay holidayAddTimeSet = new BreakdownTimeDay(new AttendanceTime(command.getOneDay()!= null? command.getOneDay().intValue():0), new AttendanceTime(command.getMorning() != null? command.getMorning().intValue(): 0), new AttendanceTime(command.getAfternoon()!=null?command.getAfternoon().intValue():0));
		// ------------------------
		
		
		// ScheduleMethod
		// WorkScheduleBusCal - 営業日カレンダーによる勤務予定作成
		WorkScheduleBusCal busCal = new WorkScheduleBusCal(
				EnumAdaptor.valueOf(
						command.getReferenceBusinessDayCalendar() != null
								? command.getReferenceBusinessDayCalendar().intValue() : 0,
						WorkScheduleMasterReferenceAtr.class),
				EnumAdaptor.valueOf(
						command.getReferenceBasicWork() != null ? command.getReferenceBasicWork().intValue() : 0,
						WorkScheduleMasterReferenceAtr.class),
				EnumAdaptor.valueOf(command.getReferenceType() != null ? command.getReferenceType().intValue() : 0,
						TimeZoneScheduledMasterAtr.class));
		// MonthlyPatternWorkScheduleCre
		MonthlyPatternWorkScheduleCre monthlySchedule = new MonthlyPatternWorkScheduleCre(
				EnumAdaptor.valueOf(command.getReferenceType()!=null?command.getReferenceType().intValue():0, TimeZoneScheduledMasterAtr.class));
		ScheduleMethod scheduleMethod = new ScheduleMethod(
				EnumAdaptor.valueOf(command.getBasicCreateMethod()!=null?command.getBasicCreateMethod().intValue():0, WorkScheduleBasicCreMethod.class),
				busCal, monthlySchedule);
		WorkingConditionItem workingCond = new WorkingConditionItem(histId,
				EnumAdaptor.valueOf(command.getScheduleManagementAtr() != null? command.getScheduleManagementAtr().intValue() : 0, NotUseAtr.class), workDayOfWeek,
				workCategory, EnumAdaptor.valueOf(command.getAutoStampSetAtr() != null ? command.getAutoStampSetAtr().intValue(): 0, NotUseAtr.class),
				EnumAdaptor.valueOf(command.getAutoIntervalSetAtr() != null ? command.getAutoIntervalSetAtr().intValue(): 0, NotUseAtr.class),
				command.getEmployeeId(),
				EnumAdaptor.valueOf(command.getVacationAddedTimeAtr() != null? command.getVacationAddedTimeAtr().intValue(): 0, NotUseAtr.class),
				command.getContractTime() != null? command.getContractTime().intValue(): 0,
				EnumAdaptor.valueOf(command.getLaborSystem() != null? command.getLaborSystem().intValue(): 0, WorkingSystem.class), holidayAddTimeSet,
				scheduleMethod);
		return workingCond;
	}
	public WorkingConditionItem fromDTO(UpdateWorkingConditionCommand command){
		List<TimeZone> listTimeZone = new ArrayList<>();
		// ---------------------- PersonalDayOfWeek
				/** The monday. */
				// 月曜日
				listTimeZone = getTimeZone(command.getMondayStartTime1(),command.getMondayEndTime1(),command.getMondayStartTime2(),command.getMondayEndTime2());
				SingleDaySchedule mondaySchedule = new SingleDaySchedule(command.getMondayWorkTypeCode(),listTimeZone, getOptionalWorkTime(command.getMondayWorkTimeCode()));
				
				/** The tuesday. */
				// 火曜日
				listTimeZone = getTimeZone(command.getTuesdayStartTime1(),command.getTuesdayEndTime1(),command.getTuesdayStartTime2(),command.getTuesdayEndTime2());
				SingleDaySchedule tuesdaySchedule = new SingleDaySchedule(command.getTuesdayWorkTypeCode(),listTimeZone, getOptionalWorkTime(command.getTuesdayWorkTimeCode()));
				
				/** The wednesday. */
				// 水曜日
				listTimeZone = getTimeZone(command.getWednesdayStartTime1(),command.getWednesdayEndTime1(),command.getWednesdayStartTime2(),command.getWednesdayEndTime2());
				SingleDaySchedule wedSchedule = new SingleDaySchedule(command.getWednesdayWorkTypeCode(),listTimeZone, getOptionalWorkTime(command.getWednesdayWorkTimeCode()));
				
				/** The thursday. */
				// 木曜日
				listTimeZone =  getTimeZone(command.getThursdayStartTime1(),command.getThursdayEndTime1(),command.getThursdayStartTime2(),command.getThursdayEndTime2());
				SingleDaySchedule thurSchedule = new SingleDaySchedule(command.getThursdayWorkTypeCode(),listTimeZone, getOptionalWorkTime(command.getThursdayWorkTimeCode()));
				
				/** The friday. */
				// 金曜日
				listTimeZone = getTimeZone(command.getFridayStartTime1(),command.getFridayEndTime1(),command.getFridayStartTime2(),command.getFridayEndTime2());
				SingleDaySchedule friSchedule = new SingleDaySchedule(command.getFridayWorkTypeCode(),listTimeZone, getOptionalWorkTime(command.getFridayWorkTimeCode()));
				
				/** The saturday. */
				// 土曜日
				listTimeZone = getTimeZone(command.getSaturdayStartTime1(),command.getSaturdayEndTime1(),command.getSaturdayStartTime2(),command.getSaturdayEndTime2());
				SingleDaySchedule satSchedule = new SingleDaySchedule(command.getSaturdayWorkTypeCode(),listTimeZone, getOptionalWorkTime(command.getSaturdayWorkTimeCode()));
				
				/** The sunday. */
				// 日曜日
				listTimeZone = getTimeZone(command.getSundayStartTime1(),command.getSundayEndTime1(),command.getSundayStartTime2(),command.getSundayEndTime2());
				SingleDaySchedule sunSchedule = new SingleDaySchedule(command.getSundayWorkTypeCode(),listTimeZone, getOptionalWorkTime(command.getSundayWorkTimeCode()));
				
				PersonalDayOfWeek workDayOfWeek = new PersonalDayOfWeek(getOptionalSingleDay(mondaySchedule),getOptionalSingleDay(tuesdaySchedule),getOptionalSingleDay(wedSchedule),getOptionalSingleDay(thurSchedule),getOptionalSingleDay(friSchedule),getOptionalSingleDay(satSchedule),getOptionalSingleDay(sunSchedule));
				//-------------------------
						
						
				// ------------------ PersonalWorkCategory
				/** The weekday time. */
				// 平日時
				listTimeZone = getTimeZone(command.getWeekDayStartTime1(),command.getWeekDayEndTime1(),command.getWeekDayStartTime2(),command.getWeekDayEndTime2());
				SingleDaySchedule weekdaySchedule = new SingleDaySchedule(command.getWeekdayWorkTypeCode(),listTimeZone, getOptionalWorkTime(command.getWeekdayWorkTimeCode()));
				
				/** The holiday work. */
				// 休日出勤時
				listTimeZone = getTimeZone(command.getWorkInHolidayStartTime1(),command.getWorkInHolidayEndTime1(),command.getWorkInHolidayStartTime2(),command.getWorkInHolidayEndTime2());
				SingleDaySchedule wholidaySchedule = new SingleDaySchedule(command.getWorkInHolidayWorkTypeCode(),listTimeZone, getOptionalWorkTime(command.getWorkInHolidayWorkTimeCode()));
				
				/** The holiday time. */
				// 休日時
				// private SingleDaySchedule holidayTime;
				SingleDaySchedule holiday = new SingleDaySchedule(command.getHolidayWorkTypeCode(), new ArrayList<>(), Optional.empty());
				
				/** The in law break time. */
				// 法内休出時
				listTimeZone = getTimeZone(command.getInLawBreakTimeStartTime1(),command.getInLawBreakTimeEndTime1(),command.getInLawBreakTimeStartTime2(),command.getInLawBreakTimeEndTime2());
				SingleDaySchedule inLawBreakTime = new SingleDaySchedule(command.getInLawBreakTimeWorkTypeCode(),listTimeZone, getOptionalWorkTime(command.getInLawBreakTimeWorkTimeCode()));
				
				/** The outside law break time. */
				// 法外休出時
				listTimeZone = getTimeZone(command.getOutsideLawBreakTimeStartTime1(),command.getOutsideLawBreakTimeEndTime1(),command.getOutsideLawBreakTimeStartTime2(),command.getOutsideLawBreakTimeEndTime2());
				SingleDaySchedule outLawBreakTime = new SingleDaySchedule(command.getOutsideLawBreakTimeWorkTypeCode(),listTimeZone, getOptionalWorkTime(command.getOutsideLawBreakTimeWorkTimeCode()));
				
				/** The holiday attendance time. */
				// 祝日出勤時
				listTimeZone = getTimeZone(command.getHolidayAttendanceTimeStartTime1(),command.getHolidayAttendanceTimeEndTime1(),command.getHolidayAttendanceTimeStartTime2(),command.getHolidayAttendanceTimeEndTime2());
				SingleDaySchedule holidayAttendance = new SingleDaySchedule(command.getHolidayAttendanceTimeWorkTypeCode(),listTimeZone, getOptionalWorkTime(command.getHolidayAttendanceTimeWorkTimeCode()));
				
				/** The public holiday work. */
				// 公休出勤時
				listTimeZone = getTimeZone(command.getWorkInPublicHolidayStartTime1(),command.getWorkInPublicHolidayEndTime1(),command.getWorkInPublicHolidayStartTime2(),command.getWorkInPublicHolidayEndTime2());
				SingleDaySchedule wiPublicHoliday = new SingleDaySchedule(command.getWorkInPublicHolidayWorkTypeCode(),listTimeZone, getOptionalWorkTime(command.getWorkInPublicHolidayWorkTimeCode()));
				
				PersonalWorkCategory workCategory = new PersonalWorkCategory(weekdaySchedule,wholidaySchedule,holiday,getOptionalSingleDay(inLawBreakTime),getOptionalSingleDay(outLawBreakTime),getOptionalSingleDay(holidayAttendance),getOptionalSingleDay(wiPublicHoliday));
				// -------------------------------
		
		
		// ----------------------- BreakdownTimeDay
		BreakdownTimeDay holidayAddTimeSet = new BreakdownTimeDay(new AttendanceTime(command.getOneDay()!= null? command.getOneDay().intValue():0), new AttendanceTime(command.getMorning() != null? command.getMorning().intValue(): 0), new AttendanceTime(command.getAfternoon()!=null?command.getAfternoon().intValue():0));
		// ------------------------
		
		
		// ScheduleMethod
		// WorkScheduleBusCal - 営業日カレンダーによる勤務予定作成
		WorkScheduleBusCal busCal = new WorkScheduleBusCal(
				EnumAdaptor.valueOf(
						command.getReferenceBusinessDayCalendar() != null
								? command.getReferenceBusinessDayCalendar().intValue() : 0,
						WorkScheduleMasterReferenceAtr.class),
				EnumAdaptor.valueOf(
						command.getReferenceBasicWork() != null ? command.getReferenceBasicWork().intValue() : 0,
						WorkScheduleMasterReferenceAtr.class),
				EnumAdaptor.valueOf(command.getReferenceType() != null ? command.getReferenceType().intValue() : 0,
						TimeZoneScheduledMasterAtr.class));
		// MonthlyPatternWorkScheduleCre
		MonthlyPatternWorkScheduleCre monthlySchedule = new MonthlyPatternWorkScheduleCre(
				EnumAdaptor.valueOf(command.getReferenceType()!=null?command.getReferenceType().intValue():0, TimeZoneScheduledMasterAtr.class));
		ScheduleMethod scheduleMethod = new ScheduleMethod(
				EnumAdaptor.valueOf(command.getBasicCreateMethod()!=null?command.getBasicCreateMethod().intValue():0, WorkScheduleBasicCreMethod.class),
				busCal, monthlySchedule);
		WorkingConditionItem workingCond = new WorkingConditionItem(command.getHistId(),
				EnumAdaptor.valueOf(command.getScheduleManagementAtr() != null? command.getScheduleManagementAtr().intValue() : 0, NotUseAtr.class), workDayOfWeek,
				workCategory, EnumAdaptor.valueOf(command.getAutoStampSetAtr() != null ? command.getAutoStampSetAtr().intValue(): 0, NotUseAtr.class),
				EnumAdaptor.valueOf(command.getAutoIntervalSetAtr() != null ? command.getAutoIntervalSetAtr().intValue(): 0, NotUseAtr.class),
				command.getEmployeeId(),
				EnumAdaptor.valueOf(command.getVacationAddedTimeAtr() != null? command.getVacationAddedTimeAtr().intValue(): 0, NotUseAtr.class),
				command.getContractTime() != null? command.getContractTime().intValue(): 0,
				EnumAdaptor.valueOf(command.getLaborSystem() != null? command.getLaborSystem().intValue(): 0, WorkingSystem.class), holidayAddTimeSet,
				scheduleMethod);
		return workingCond;
	}
	
	
	private List<TimeZone> getTimeZone(BigDecimal startTime1, BigDecimal endTime1, BigDecimal startTime2, BigDecimal endTime2){
		List<TimeZone> listTimeZone = new ArrayList<>();
		if (startTime1 != null && endTime1 != null){
			TimeZone item = new TimeZone(EnumAdaptor.valueOf(1,NotUseAtr.class), 1, startTime1.intValue(), endTime1.intValue());
			listTimeZone.add(item);
		}
		if (startTime2 != null && endTime2 != null){
			TimeZone item = new TimeZone(EnumAdaptor.valueOf(1,NotUseAtr.class), 2, startTime2.intValue(), endTime2.intValue());
			listTimeZone.add(item);
		}
		
		return listTimeZone;
	}
	
	private Optional<String> getOptionalWorkTime(String value){
		if (StringUtils.isNotEmpty(value)){
			return Optional.of(value);
		}
		return Optional.empty();
	}
	
	private Optional<SingleDaySchedule> getOptionalSingleDay(SingleDaySchedule value){
		if (value.getWorkTypeCode() != null){
			return Optional.of(value);
		}
		
		return Optional.empty();
	}
}
