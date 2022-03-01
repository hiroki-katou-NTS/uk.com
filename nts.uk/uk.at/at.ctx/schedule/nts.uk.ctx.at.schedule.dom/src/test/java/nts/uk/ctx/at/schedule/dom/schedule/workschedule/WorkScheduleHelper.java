package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.val;
import mockit.Injectable;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.support.supportschedule.SupportSchedule;
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
			Optional.empty(),
			Optional.empty());
	
	@Injectable
	private static TaskSchedule taskSchedule;
	
	@Injectable
	private static BreakTimeOfDailyAttd lstBreakTime;
	
	
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
				SupportSchedule.createWithEmptyList(),
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
		
		WorkSchedule workSchedule = createDefaultWorkSchedule();
		workSchedule.setLstBreakTime(breakTime);
		workSchedule.setTaskSchedule(taskSchedule);
		
		return workSchedule;
	}
	
	/**
	 * @param breakTime
	 * @param shortTime
	 * @return
	 */
	public static WorkSchedule createWithBreakTimeAndShortTime(BreakTimeOfDailyAttd breakTime, Optional<ShortTimeOfDailyAttd> shortTime) {
		
		WorkSchedule workSchedule = createDefaultWorkSchedule();
		workSchedule.setLstBreakTime(breakTime);
		workSchedule.setOptSortTimeWork(shortTime);
		
		return workSchedule;
	}
	
	/**
	 * @param workInfo
	 * @return
	 */
	public static WorkSchedule createWithWorkInfo(WorkInfoOfDailyAttendance workInfo) {
		
		WorkSchedule workSchedule = createDefaultWorkSchedule();
		workSchedule.setWorkInfo(workInfo);
		
		return workSchedule;
	}
	
	/**
	 * @param workInfo
	 * @param timeLeaving
	 * @return
	 */
	public static WorkSchedule createWithWorkInfoAndTimeLeaving(
			WorkInfoOfDailyAttendance workInfo,
			TimeLeavingOfDailyAttd timeLeaving) {
		
		WorkSchedule workSchedule = createDefaultWorkSchedule();
		workSchedule.setWorkInfo(workInfo);
		workSchedule.setOptTimeLeaving(Optional.of(timeLeaving));
		
		return workSchedule;
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
		
		WorkSchedule workSchedule = createDefaultWorkSchedule();
		workSchedule.setWorkInfo(workInfo);
		workSchedule.setLstBreakTime(breakTime);
		workSchedule.setOptTimeLeaving(Optional.of(timeLeaving));
		
		return workSchedule;
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
		
		WorkSchedule workSchedule = createDefaultWorkSchedule();
		workSchedule.setWorkInfo(workInfo);
		workSchedule.setLstBreakTime(breakTime);
		workSchedule.setOptTimeLeaving(Optional.of(timeLeaving));
		workSchedule.setOptSortTimeWork(Optional.of(shortTimeWork));
		
		return workSchedule;
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
		
		WorkSchedule workSchedule = createDefaultWorkSchedule();
		workSchedule.setOptTimeLeaving(optTimeLeaving);
		workSchedule.setOptAttendanceTime(optAttendanceTime);
		workSchedule.setOutingTime(outingTime);
		
		return workSchedule;
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
		
		WorkSchedule workSchedule = createDefaultWorkSchedule();
		workSchedule.setOptTimeLeaving(Optional.of( timeLeaving ));
		workSchedule.setLstEditState(editStateList);
		
		return workSchedule;
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
		
		WorkSchedule workSchedule = createDefaultWorkSchedule();
		workSchedule.setWorkInfo(workInfo);
		workSchedule.setOptTimeLeaving(optTimeLeaving);
		
		return workSchedule;
	}
	
	public static WorkSchedule createWithParams(
			BreakTimeOfDailyAttd breakTime,
			List<EditStateOfDailyAttd> editStateList
			) {
		
		WorkSchedule workSchedule = createDefaultWorkSchedule();
		workSchedule.setLstBreakTime(breakTime);
		workSchedule.setLstEditState(editStateList);
		
		return workSchedule;
	}
	
	public static WorkSchedule createWithConfirmAtr(ConfirmedATR confirmAtr) {
		
		WorkSchedule workSchedule = createDefaultWorkSchedule();
		workSchedule.setConfirmedATR(confirmAtr);
		return workSchedule;
	}
	
	public static WorkSchedule createWithEditStateList(List<EditStateOfDailyAttd> editStateList) {
		
		WorkSchedule workSchedule = createDefaultWorkSchedule();
		workSchedule.setLstEditState(editStateList);
		
		return workSchedule;
	}
	
	public static WorkSchedule createWithParams(String employeeId, GeneralDate date,
			ConfirmedATR confirmedAtr, TaskSchedule taskSchedule, SupportSchedule supportSchedule) {
		
		return new WorkSchedule(employeeId, date, confirmedAtr, 
				defaultWorkInfo, defaultAffInfo, 
				new BreakTimeOfDailyAttd(), Collections.emptyList(), 
				taskSchedule, supportSchedule, 
				Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
	}
	
	/**
	 * 日別勤怠の所属情報を作る
	 * @param wplID 職場ID
	 * @param workplaceGroupId 職場グループID
	 * @return
	 */
	public static AffiliationInforOfDailyAttd createAffiliationInforOfDailyAttd( String wplID, Optional<String> workplaceGroupId ) {
		
		val domain = new AffiliationInforOfDailyAttd();
		
		domain.setWplID(wplID);
		domain.setWorkplaceGroupId(workplaceGroupId);
		
		return domain;
	}
	
	/**
	 * 勤務予定を作る
	 * @param sid 社員ID
	 * @param ymd 年月日
	 * @param affInfo 確定区分
	 * @param supportSchedule 応援予定
	 * @return
	 */
	public static WorkSchedule createWorkSchedule(
			String sid
		,	GeneralDate ymd
		,	AffiliationInforOfDailyAttd affInfo
		,	SupportSchedule supportSchedule
		) {
		
		return new WorkSchedule(
					sid
				,	ymd
				,	ConfirmedATR.CONFIRMED
				,	defaultWorkInfo
				,	affInfo
				,	lstBreakTime
				,	Collections.emptyList()
				,	taskSchedule
				,	supportSchedule
				,	Optional.empty()
				,	Optional.empty()
				,	Optional.empty()
				,	Optional.empty()
					);
	}

}
