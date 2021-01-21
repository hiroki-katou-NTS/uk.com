package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workdays;

import java.io.Serializable;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.context.AppContexts;

/**
 * 月別実績の時間消化休暇日数
 */
@Getter
public class TimeConsumpVacationDaysOfMonthly implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 日数 */
	private AttendanceDaysMonth days;
	/** 時間 */
	private AttendanceTimeMonth time;
	
	/**
	 * コンストラクタ
	 */
	public TimeConsumpVacationDaysOfMonthly(){
		
		this.days = new AttendanceDaysMonth(0.0);
		this.time = new AttendanceTimeMonth(0);
	}
	
	/**
	 * ファクトリー
	 * @param days 日数
	 * @param time　時間
	 * @return 月別実績の時間消化休暇日数
	 */
	public static TimeConsumpVacationDaysOfMonthly of(
			AttendanceDaysMonth days,
			AttendanceTimeMonth time){
		
		val domain = new TimeConsumpVacationDaysOfMonthly();
		domain.days = days;
		domain.time = time;
		return domain;
	}
	
	/**
	 * 集計
	 * @param workType 勤務種類
	 * @param attendanceTime 日別実績の勤怠時間
	 */
	public void aggregate(WorkType workType, AttendanceTimeOfDailyAttendance attendanceTime){
		
		/** 勤務種類を確認して時間消化休暇日数を取得する */
		double vacationDays = workType.calcTimeConsumpVacationDays();  
		
		if (vacationDays > 0) {
			
			/** 月別実績の時間消化休暇日数に加算する */
			if (attendanceTime != null) {
				this.time = this.time.addMinutes(attendanceTime.getActualWorkingTimeOfDaily()
						.getTotalWorkingTime().getHolidayOfDaily()
						.getTimeDigest().getUseTime().valueAsMinutes());
			}
			
			this.days = this.days.addDays(vacationDays);
		}
	}
	
	/**
	 * 集計
	 * @param workInfo 日別勤怠の勤務情報
	 * @param attendanceTime 日別実績の勤怠時間
	 */
	public void aggregate(RequireM1 require, 
			WorkInfoOfDailyAttendance workInfo, AttendanceTimeOfDailyAttendance attendanceTime){
		
		val workType = require.workType(AppContexts.user().companyId(), workInfo.getRecordInfo().getWorkTypeCode().v());

		/** 集計 */
		workType.ifPresent(wt -> aggregate(wt, attendanceTime));
	}
	
	public static interface RequireM1 {
		
		Optional<WorkType> workType(String cid, String workTypeCode);
	}

	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(TimeConsumpVacationDaysOfMonthly target){
		
		this.days = this.days.addDays(target.days.v());
		this.time = this.time.addMinutes(target.time.valueAsMinutes());
	}
}
