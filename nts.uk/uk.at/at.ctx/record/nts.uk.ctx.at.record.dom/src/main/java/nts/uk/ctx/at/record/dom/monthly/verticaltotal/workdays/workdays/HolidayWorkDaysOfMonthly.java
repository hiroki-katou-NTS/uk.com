package nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.WorkTypeDaysCountTable;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

/**
 * 月別実績の休出日数
 * @author shuichu_ishida
 */
@Getter
public class HolidayWorkDaysOfMonthly {

	/** 日数 */
	private AttendanceDaysMonth days;
	
	/**
	 * コンストラクタ
	 */
	public HolidayWorkDaysOfMonthly(){
		
		this.days = new AttendanceDaysMonth(0.0);
	}
	
	/**
	 * ファクトリー
	 * @param days 日数
	 * @return 月別実績の休出日数
	 */
	public static HolidayWorkDaysOfMonthly of(AttendanceDaysMonth days){
		
		val domain = new HolidayWorkDaysOfMonthly();
		domain.days = days;
		return domain;
	}
	
	/**
	 * 集計
	 * @param workingSystem 労働制
	 * @param workTypeDaysCountTable 勤務種類の日数カウント表
	 * @param isAttendanceDay 出勤しているかどうか
	 */
	public void aggregate(
			WorkingSystem workingSystem,
			WorkTypeDaysCountTable workTypeDaysCountTable,
			boolean isAttendanceDay){
		
		if (workTypeDaysCountTable == null) return;
			
		// 労働制を取得
		if (workingSystem == WorkingSystem.EXCLUDED_WORKING_CALCULATE){

			// 計算対象外の時、無条件で、休出日数に加算する
			this.days = this.days.addDays(workTypeDaysCountTable.getHolidayWorkDays().v());
		}
		else {
			
			// その他労働制の時、出勤している日なら、休出日数に加算する
			if (isAttendanceDay){
				this.days = this.days.addDays(workTypeDaysCountTable.getHolidayWorkDays().v());
			}
		}
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(HolidayWorkDaysOfMonthly target){
		
		this.days = this.days.addDays(target.days.v());
	}
}
