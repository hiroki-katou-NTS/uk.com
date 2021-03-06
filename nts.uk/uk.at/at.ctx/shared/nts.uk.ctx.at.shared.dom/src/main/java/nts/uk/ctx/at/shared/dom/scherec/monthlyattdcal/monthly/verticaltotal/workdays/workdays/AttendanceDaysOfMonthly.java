package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workdays.workdays;

import java.io.Serializable;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.WorkDaysNumberOnLeaveCount;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.WorkTypeDaysCountTable;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 月別実績の出勤日数
 * @author shuichi_ishida
 */
@Getter
public class AttendanceDaysOfMonthly implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 日数 */
	private AttendanceDaysMonth days;
	
	/**
	 * コンストラクタ
	 */
	public AttendanceDaysOfMonthly(){
		
		this.days = new AttendanceDaysMonth(0.0);
	}
	
	/**
	 * ファクトリー
	 * @param days 日数
	 * @return 月別実績の出勤日数
	 */
	public static AttendanceDaysOfMonthly of(AttendanceDaysMonth days){
		
		val domain = new AttendanceDaysOfMonthly();
		domain.days = days;
		return domain;
	}
	
	/**
	 * 集計
	 * @param workingSystem 労働制
	 * @param workTypeDaysCountTable 勤務種類の日数カウント表
	 * @param isAttendanceDay 出勤しているかどうか
	 */
	public void aggregate(Require require, String cid, WorkType workType, 
			WorkingSystem workingSystem, WorkTypeDaysCountTable workTypeDaysCountTable,
			boolean isAttendanceDay){
		
		if (workTypeDaysCountTable == null) return;
		
		/** ○勤務種類を判断しカウント数を取得する */
		val attendanceDays = workTypeDaysCountTable.getAttendanceDays().v();
		
		/** 休暇時の日数カウントを計算する */
		val workDaysNumberOnLeaveCount = require.workDaysNumberOnLeaveCount(cid);
		val holidayTimeDays = workDaysNumberOnLeaveCount.countDaysOnHoliday(workType).v();
		
		val totalAttendanceDays = attendanceDays + holidayTimeDays;
		
		// 労働制を取得
		if (workingSystem == WorkingSystem.EXCLUDED_WORKING_CALCULATE){

			// 計算対象外の時、無条件で、出勤日数に加算する
			this.days = this.days.addDays(totalAttendanceDays);
		} else { // その他労働制の時
			
			// 勤務種類が連続勤務かどうかを判断する
			if (workTypeDaysCountTable.isContinuousWorkDay()) {
				
				// 1日連続勤務の時、無条件で、出勤日数に加算する
				this.days = this.days.addDays(totalAttendanceDays);
			} else {
				
				// その他勤務の時、出勤している日なら、出勤日数に加算する
				if (isAttendanceDay) {
					this.days = this.days.addDays(attendanceDays); 
				}
				
				this.days = this.days.addDays(holidayTimeDays); 
			}
		}
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(AttendanceDaysOfMonthly target){
		
		this.days = this.days.addDays(target.days.v());
	}
	
	public static interface Require {
	
		WorkDaysNumberOnLeaveCount workDaysNumberOnLeaveCount(String cid);
	}
}
