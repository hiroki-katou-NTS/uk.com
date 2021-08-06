package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.task.taskschedule.TaskSchedule;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfWeek;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.ClassificationCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.CalculationState;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

public class WorkScheduleHelper {
	
	private static WorkInfoOfDailyAttendance defaultWorkInfo = new WorkInfoOfDailyAttendance(
			new WorkInformation(new WorkTypeCode("001"), new WorkTimeCode("001")), 
			CalculationState.No_Calculated, 
			NotUseAttribute.Not_use, 
			NotUseAttribute.Not_use, 
			DayOfWeek.MONDAY, 
			Collections.emptyList(), 
			Optional.empty());
	
	private static AffiliationInforOfDailyAttd defaultAffInfo = new AffiliationInforOfDailyAttd(
			new EmploymentCode("EmpCode-001"),
			"JobTitle-Id-001", 
			"Wpl-Id-001", 
			new ClassificationCode("class-001"), 
			Optional.empty(), 
			Optional.empty(),
			Optional.empty(),
			Optional.empty());
	
	
	public static WorkSchedule createDefaultWorkSchedule() { 
		
		return new WorkSchedule(
				"employeeID",
				GeneralDate.today(),
				ConfirmedATR.UNSETTLED,
				defaultWorkInfo,
				defaultAffInfo, 
				new BreakTimeOfDailyAttd(), // breakTime
				Collections.emptyList(), // editState
				TaskSchedule.createWithEmptyList(), // taskSchedule
				Optional.empty(), // timeLeaving
				Optional.empty(), // attendanceTime
				Optional.empty(), // shortTime
				Optional.empty()); // outingTime
	}
	
	/**
	 * @param breakTime
	 * @param taskSchedule
	 * @return
	 */
	public static WorkSchedule createWithBreakTimeAndTaskSchedule(BreakTimeOfDailyAttd breakTime, TaskSchedule taskSchedule) {
		
		return new WorkSchedule(
				"employeeID",
				GeneralDate.today(),
				ConfirmedATR.UNSETTLED,
				defaultWorkInfo,
				defaultAffInfo, 
				breakTime,
				Collections.emptyList(), // editState
				taskSchedule,
				Optional.empty(), // timeLeaving
				Optional.empty(), // attendanceTime
				Optional.empty(), // shortTime
				Optional.empty()); // outingTime
	}
	
	/**
	 * @param breakTime
	 * @param shortTime
	 * @return
	 */
	public static WorkSchedule createWithBreakTimeAndShortTime(BreakTimeOfDailyAttd breakTime, Optional<ShortTimeOfDailyAttd> shortTime) {
		
		return new WorkSchedule(
				"employeeID",
				GeneralDate.today(),
				ConfirmedATR.UNSETTLED,
				defaultWorkInfo,
				defaultAffInfo, 
				breakTime,
				Collections.emptyList(), // editState
				TaskSchedule.createWithEmptyList(),
				Optional.empty(), // timeLeaving
				Optional.empty(), // attendanceTime
				shortTime, // shortTime
				Optional.empty()); // outingTime
	}
	
	/**
	 * @param workInfo
	 * @return
	 */
	public static WorkSchedule createWithWorkInfo(WorkInfoOfDailyAttendance workInfo) {
		
		return new WorkSchedule(
				"employeeID",
				GeneralDate.today(),
				ConfirmedATR.UNSETTLED,
				workInfo,
				defaultAffInfo, 
				new BreakTimeOfDailyAttd(),
				Collections.emptyList(), // editState
				TaskSchedule.createWithEmptyList(),
				Optional.empty(), // timeLeaving
				Optional.empty(), // attendanceTime
				Optional.empty(), // shortTime
				Optional.empty()); // outingTime
	}
	
	/**
	 * @param workInfo
	 * @param timeLeaving
	 * @return
	 */
	public static WorkSchedule createWithWorkInfoAndTimeLeaving(
			WorkInfoOfDailyAttendance workInfo,
			TimeLeavingOfDailyAttd timeLeaving) {
		
		return new WorkSchedule(
				"employeeID",
				GeneralDate.today(),
				ConfirmedATR.UNSETTLED,
				workInfo,
				defaultAffInfo, 
				new BreakTimeOfDailyAttd(),
				Collections.emptyList(), // editState
				TaskSchedule.createWithEmptyList(),
				Optional.of(timeLeaving), // timeLeaving
				Optional.empty(), // attendanceTime
				Optional.empty(), // shortTime
				Optional.empty()); // outingTime
	}
	
	/**
	 * @param workInfo
	 * @param breakTime
	 * @param timeLeaving
	 * @return
	 */
	public static WorkSchedule createWithParams(
			WorkInfoOfDailyAttendance workInfo,
			BreakTimeOfDailyAttd breakTime,
			TimeLeavingOfDailyAttd timeLeaving) {
		
		return new WorkSchedule(
				"employeeID",
				GeneralDate.today(),
				ConfirmedATR.UNSETTLED,
				workInfo,
				defaultAffInfo, 
				breakTime,
				Collections.emptyList(), // editState
				TaskSchedule.createWithEmptyList(),
				Optional.of(timeLeaving), // timeLeaving
				Optional.empty(), // attendanceTime
				Optional.empty(), // shortTime
				Optional.empty()); // outingTime
	}
	
	/**
	 * @param workInfo
	 * @param breakTime
	 * @param timeLeaving
	 * @param shortTimeWork
	 * @return
	 */
	public static WorkSchedule createWithParams(
			WorkInfoOfDailyAttendance workInfo,
			BreakTimeOfDailyAttd breakTime,
			TimeLeavingOfDailyAttd timeLeaving,
			ShortTimeOfDailyAttd shortTimeWork) {
		
		return new WorkSchedule(
				"employeeID",
				GeneralDate.today(),
				ConfirmedATR.UNSETTLED,
				workInfo,
				defaultAffInfo, 
				breakTime,
				Collections.emptyList(), // editState
				TaskSchedule.createWithEmptyList(),
				Optional.of(timeLeaving), // timeLeaving
				Optional.empty(), // attendanceTime
				Optional.of(shortTimeWork), // shortTime
				Optional.empty()); // outingTime
	}
	
	/**
	 * @param optTimeLeaving 出退勤
	 * @param optAttendanceTime 勤怠時間
	 * @param outingTime 外出時間帯
	 * @return
	 */
	public static WorkSchedule createWithParams(
			Optional<TimeLeavingOfDailyAttd> optTimeLeaving,
			Optional<AttendanceTimeOfDailyAttendance> optAttendanceTime,
			Optional<OutingTimeOfDailyAttd> outingTime
			) {
		
		return new WorkSchedule(
				"employeeID",
				GeneralDate.today(),
				ConfirmedATR.UNSETTLED,
				defaultWorkInfo,
				defaultAffInfo, 
				new BreakTimeOfDailyAttd(),
				Collections.emptyList(),
				TaskSchedule.createWithEmptyList(),
				optTimeLeaving, // parameter
				optAttendanceTime, // parameter
				Optional.empty(),
				outingTime); // parameter
	}
	
	/**
	 * @param timeLeaving 出退勤
	 * @param editStateList 編修状態
	 * @return
	 */
	public static WorkSchedule createWithParams(
			TimeLeavingOfDailyAttd timeLeaving,
			List<EditStateOfDailyAttd> editStateList
			) {
		
		return new WorkSchedule(
				"employeeID",
				GeneralDate.today(),
				ConfirmedATR.UNSETTLED,
				defaultWorkInfo,
				defaultAffInfo, 
				new BreakTimeOfDailyAttd(),
				editStateList, 
				TaskSchedule.createWithEmptyList(),
				Optional.of( timeLeaving ),
				Optional.empty(), 
				Optional.empty(),
				Optional.empty()); 
	}
	
	/**
	 * @param optTimeLeaving 出退勤
	 * @param goStraight 直行区分
	 * @param backStraight 直帰区分 
	 * @return
	 */
	public static WorkSchedule createWithParams(
			Optional<TimeLeavingOfDailyAttd> optTimeLeaving,
			NotUseAttribute goStraight,
			NotUseAttribute backStraight
			) {
		
		WorkInfoOfDailyAttendance workInfo = new WorkInfoOfDailyAttendance(
				new WorkInformation(new WorkTypeCode("001"), new WorkTimeCode("002")),
				CalculationState.No_Calculated, 
				goStraight, 
				backStraight, 
				DayOfWeek.MONDAY, 
				new ArrayList<>(), Optional.empty()); 
		
		return new WorkSchedule(
				"employeeID",
				GeneralDate.today(),
				ConfirmedATR.UNSETTLED,
				workInfo,
				defaultAffInfo, 
				new BreakTimeOfDailyAttd(),
				Collections.emptyList(), 
				TaskSchedule.createWithEmptyList(),
				optTimeLeaving,
				Optional.empty(), 
				Optional.empty(),
				Optional.empty()); 
	}
	
	public static WorkSchedule createWithParams(
			BreakTimeOfDailyAttd breakTime,
			List<EditStateOfDailyAttd> editStateList
			) {
		
		return new WorkSchedule(
				"employeeID",
				GeneralDate.today(),
				ConfirmedATR.UNSETTLED,
				defaultWorkInfo,
				defaultAffInfo, 
				breakTime,
				editStateList, 
				TaskSchedule.createWithEmptyList(),
				Optional.empty(),
				Optional.empty(), 
				Optional.empty(),
				Optional.empty()); 
	}
	
	public static WorkSchedule createWithConfirmAtr(ConfirmedATR confirmAtr) {
		return new WorkSchedule(
				"employeeID",
				GeneralDate.today(),
				confirmAtr,
				defaultWorkInfo,
				defaultAffInfo, 
				new BreakTimeOfDailyAttd(),
				Collections.emptyList(),
				TaskSchedule.createWithEmptyList(),
				Optional.empty(),
				Optional.empty(),
				Optional.empty(),
				Optional.empty());
	}
	
	public static WorkSchedule createWithEditStateList(List<EditStateOfDailyAttd> editStateList) {
		return new WorkSchedule(
				"employeeID",
				GeneralDate.today(),
				ConfirmedATR.UNSETTLED,
				defaultWorkInfo,
				defaultAffInfo, 
				new BreakTimeOfDailyAttd(),
				editStateList,
				TaskSchedule.createWithEmptyList(),
				Optional.empty(),
				Optional.empty(),
				Optional.empty(),
				Optional.empty());
	}

}
