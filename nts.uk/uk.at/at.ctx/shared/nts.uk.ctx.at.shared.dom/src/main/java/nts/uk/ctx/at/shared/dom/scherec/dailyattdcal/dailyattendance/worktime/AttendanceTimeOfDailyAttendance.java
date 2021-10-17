package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainObject;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.OutingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.earlyleavetime.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.SystemFixedErrorAlarm;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.latetime.LateTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.secondorder.medical.MedicalCareTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workschedule.WorkScheduleTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.CheckExcessAtr;
/**
 * 日別勤怠の勤怠時間 (new)
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.実働時間.日別勤怠の勤怠時間
 * @author tutk
 *
 */
@Getter
public class AttendanceTimeOfDailyAttendance implements DomainObject {
	
	//勤務予定時間 - 日別実績の勤務予定時間
	private WorkScheduleTimeOfDaily workScheduleTimeOfDaily;
	
	//実働時間/実績時間  - 日別実績の勤務実績時間 - 勤務時間
	private ActualWorkingTimeOfDaily actualWorkingTimeOfDaily;
	
	//滞在時間 - 日別実績の滞在時間 change tyle
	private StayingTimeOfDaily stayingTime;
	
	//不就労時間 - 勤怠時間
	private AttendanceTimeOfExistMinus unEmployedTime;
	
	//予実差異時間 - 勤怠時間
	private AttendanceTimeOfExistMinus budgetTimeVariance;
	
	//医療時間 - 日別実績の医療時間
	private MedicalCareTimeOfDaily medicalCareTime;

	/**
	 * @param schedule 	予定時間
	 * @param actual 勤務時間
	 * @param stay 滞在時間
	 * @param budget 予実差異時間
	 * @param unEmploy 不就労時間
	 */
	public AttendanceTimeOfDailyAttendance (
			 WorkScheduleTimeOfDaily schedule,
			 ActualWorkingTimeOfDaily actual,
			 StayingTimeOfDaily stay,
			 AttendanceTimeOfExistMinus budget,
			 AttendanceTimeOfExistMinus unEmploy) {
		
		this.workScheduleTimeOfDaily = schedule;
		this.actualWorkingTimeOfDaily = actual;
		this.stayingTime = stay;
		this.budgetTimeVariance = budget;
		this.unEmployedTime = unEmploy;
	}
				
	/**
	 * @param workScheduleTimeOfDaily 予定時間
	 * @param actualWorkingTimeOfDaily 勤務時間
	 * @param stayingTime 滞在時間
	 * @param unEmployedTime 不就労時間
	 * @param budgetTimeVariance 予実差異時間
	 * @param medicalCareTime 医療時間
	 */
	public AttendanceTimeOfDailyAttendance(
			WorkScheduleTimeOfDaily workScheduleTimeOfDaily,
			ActualWorkingTimeOfDaily actualWorkingTimeOfDaily, 
			StayingTimeOfDaily stayingTime,
			AttendanceTimeOfExistMinus unEmployedTime, 
			AttendanceTimeOfExistMinus budgetTimeVariance,
			MedicalCareTimeOfDaily medicalCareTime) {
		super();
		this.workScheduleTimeOfDaily = workScheduleTimeOfDaily;
		this.actualWorkingTimeOfDaily = actualWorkingTimeOfDaily;
		this.stayingTime = stayingTime;
		this.unEmployedTime = unEmployedTime;
		this.budgetTimeVariance = budgetTimeVariance;
		this.medicalCareTime = medicalCareTime;
	}
	/**
	 * エラーチェックの指示メソッド 
	 * @param attendanceItemConverter 
	 * @return 社員のエラーチェック一覧
	 */
	public List<EmployeeDailyPerError> getErrorList(String employeeId,GeneralDate targetDate,
			   										SystemFixedErrorAlarm fixedErrorAlarmCode, CheckExcessAtr checkAtr) {
		List<EmployeeDailyPerError> returnErrorItem = new ArrayList<>();
		if(this.getActualWorkingTimeOfDaily() != null) {
			return this.getActualWorkingTimeOfDaily().requestCheckError(employeeId, targetDate, fixedErrorAlarmCode, checkAtr);
		}
		return returnErrorItem;
	}
	
	public AttendanceTimeOfDailyAttendance inssertActualWorkingTimeOfDaily(ActualWorkingTimeOfDaily time) {
		return new AttendanceTimeOfDailyAttendance (
						this.workScheduleTimeOfDaily, time, this.stayingTime, this.budgetTimeVariance,
						this.unEmployedTime
				); 
	}
	
	/**
	 * 遅刻時間を取得する
	 * @return
	 */
	public List<LateTimeOfDaily> getLateTimeOfDaily(){
		//@勤務時間.総労働時間.遅刻時間
		return this.actualWorkingTimeOfDaily.getTotalWorkingTime().getLateTimeOfDaily();
	}
	
	/**
	 * 早退時間を取得する
	 * @return
	 */
	public List<LeaveEarlyTimeOfDaily> getLeaveEarlyTimeOfDaily(){
		//@勤務時間.総労働時間.早退時間
		return this.actualWorkingTimeOfDaily.getTotalWorkingTime().getLeaveEarlyTimeOfDaily();
	}
	
	/**
	 * 外出時間を取得する
	 * @return
	 */
	public List<OutingTimeOfDaily> getOutingTimeOfDaily() {
		//@勤務時間.総労働時間.外出時間
		return this.actualWorkingTimeOfDaily.getTotalWorkingTime().getOutingTimeOfDailyPerformance();
	}
	
	public static AttendanceTimeOfDailyAttendance createDefault() {
		return new AttendanceTimeOfDailyAttendance(WorkScheduleTimeOfDaily.defaultValue(),
				ActualWorkingTimeOfDaily.defaultValue(), StayingTimeOfDaily.defaultValue(),
				AttendanceTimeOfExistMinus.ZERO, AttendanceTimeOfExistMinus.ZERO,
				MedicalCareTimeOfDaily.defaultValue());
	}
	
	/**
	 * 合計相殺代休時間の取得
	 * @return 合計相殺代休時間
	 */
	public AttendanceTime getTotalOffsetCompLeaveTime() {
		
		// 遅刻相殺時間を取得する
		int lateOffsetMinutes = this.getLateTimeOfDaily().stream()
				.mapToInt(l -> l.getOffsetCompensatoryTime().valueAsMinutes())
				.sum(); 
		// 早退相殺時間を取得する
		int earlyOffsetMinutes = this.getLeaveEarlyTimeOfDaily().stream()
				.mapToInt(l -> l.getOffsetCompensatoryTime().valueAsMinutes())
				.sum(); 
		// 外出相殺時間を取得する
		int outingOffsetMinutes = this.getOutingTimeOfDaily().stream()
				.mapToInt(l -> l.getOffsetCompensatoryTime().valueAsMinutes())
				.sum(); 
		// 合計相殺代休時間
		return new AttendanceTime(lateOffsetMinutes + earlyOffsetMinutes + outingOffsetMinutes);
	}
	
	/**
	 * 合計時間代休使用時間の取得
	 * @return 合計時間代休使用時間
	 */
	public AttendanceTime getTotalTimeCompLeaveUseTime() {
		
		// 遅刻.時間代休使用時間を取得する
		int lateUseMinutes = this.getLateTimeOfDaily().stream()
				.mapToInt(l -> l.getTimePaidUseTime().getTimeCompensatoryLeaveUseTime().valueAsMinutes())
				.sum(); 
		// 早退.時間代休使用時間を取得する
		int earlyUseMinutes = this.getLeaveEarlyTimeOfDaily().stream()
				.mapToInt(l -> l.getTimePaidUseTime().getTimeCompensatoryLeaveUseTime().valueAsMinutes())
				.sum(); 
		// 外出.時間代休使用時間を取得する
		int outingUseMinutes = this.getOutingTimeOfDaily().stream()
				.mapToInt(l -> l.getTimeVacationUseOfDaily().getTimeCompensatoryLeaveUseTime().valueAsMinutes())
				.sum(); 
		// 合計時間代休使用時間
		return new AttendanceTime(lateUseMinutes + earlyUseMinutes + outingUseMinutes);
	}
	
	/**
	 * 合計超過有給使用時間の取得
	 * @return 合計超過有給使用時間
	 */
	public AttendanceTime getTotalExcessHolidayUseTime() {
		
		// 遅刻.超過有給使用時間を取得する
		int lateUseMinutes = this.getLateTimeOfDaily().stream()
				.mapToInt(l -> l.getTimePaidUseTime().getSixtyHourExcessHolidayUseTime().valueAsMinutes())
				.sum(); 
		// 早退.超過有給使用時間を取得する
		int earlyUseMinutes = this.getLeaveEarlyTimeOfDaily().stream()
				.mapToInt(l -> l.getTimePaidUseTime().getSixtyHourExcessHolidayUseTime().valueAsMinutes())
				.sum(); 
		// 外出.超過有給使用時間を取得する
		int outingUseMinutes = this.getOutingTimeOfDaily().stream()
				.mapToInt(l -> l.getTimeVacationUseOfDaily().getSixtyHourExcessHolidayUseTime().valueAsMinutes())
				.sum(); 
		// 合計超過有給使用時間
		return new AttendanceTime(lateUseMinutes + earlyUseMinutes + outingUseMinutes);
	}
	
	/**
	 * 合計特別休暇使用時間の取得
	 * @return 合計特別休暇使用時間
	 */
	public AttendanceTime getTotalSpecialHolidayUseTime() {
		
		// 遅刻.特別休暇使用時間を取得する
		int lateUseMinutes = this.getLateTimeOfDaily().stream()
				.mapToInt(l -> l.getTimePaidUseTime().getTimeSpecialHolidayUseTime().valueAsMinutes())
				.sum(); 
		// 早退.特別休暇使用時間を取得する
		int earlyUseMinutes = this.getLeaveEarlyTimeOfDaily().stream()
				.mapToInt(l -> l.getTimePaidUseTime().getTimeSpecialHolidayUseTime().valueAsMinutes())
				.sum(); 
		// 外出.特別休暇使用時間を取得する
		int outingUseMinutes = this.getOutingTimeOfDaily().stream()
				.mapToInt(l -> l.getTimeVacationUseOfDaily().getTimeSpecialHolidayUseTime().valueAsMinutes())
				.sum(); 
		// 合計特別休暇使用時間
		return new AttendanceTime(lateUseMinutes + earlyUseMinutes + outingUseMinutes);
	}
	
	/**
	 * 合計時間年休使用時間の取得
	 * @return 合計時間年休使用時間
	 */
	public AttendanceTime getTotalTimeAnnualUseTime() {
		
		// 遅刻.時間年休使用時間を取得する
		int lateUseMinutes = this.getLateTimeOfDaily().stream()
				.mapToInt(l -> l.getTimePaidUseTime().getTimeAnnualLeaveUseTime().valueAsMinutes())
				.sum(); 
		// 早退.時間年休使用時間を取得する
		int earlyUseMinutes = this.getLeaveEarlyTimeOfDaily().stream()
				.mapToInt(l -> l.getTimePaidUseTime().getTimeAnnualLeaveUseTime().valueAsMinutes())
				.sum(); 
		// 外出.時間年休使用時間を取得する
		int outingUseMinutes = this.getOutingTimeOfDaily().stream()
				.mapToInt(l -> l.getTimeVacationUseOfDaily().getTimeAnnualLeaveUseTime().valueAsMinutes())
				.sum(); 
		// 合計時間年休使用時間
		return new AttendanceTime(lateUseMinutes + earlyUseMinutes + outingUseMinutes);
	}
}
