package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.timevarience;

import java.io.Serializable;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;

/**
 * 月別実績の予実差異時間
 * @author shuichi_ishida
 */
@Getter
public class BudgetTimeVarienceOfMonthly implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 時間 */
	private AttendanceTimeMonthWithMinus time;
	
	/**
	 * コンストラクタ
	 */
	public BudgetTimeVarienceOfMonthly(){
		
		this.time = new AttendanceTimeMonthWithMinus(0);
	}
	
	/**
	 * ファクトリー
	 * @param time 時間
	 * @return 月別実績の予実差異時間
	 */
	public static BudgetTimeVarienceOfMonthly of(AttendanceTimeMonthWithMinus time){
		
		val domain = new BudgetTimeVarienceOfMonthly();
		domain.time = time;
		return domain;
	}
	
	/**
	 * 集計
	 * @param attendanceTimeOfDaily 日別実績の勤怠時間
	 */
	public void aggregate(AttendanceTimeOfDailyAttendance attendanceTimeOfDaily){

		if (attendanceTimeOfDaily == null) return;
		
		// 日別実績の「予実差異時間」を集計する
		this.time = this.time.addMinutes(attendanceTimeOfDaily.getBudgetTimeVariance().v());
	}

	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(BudgetTimeVarienceOfMonthly target){
		
		this.time = this.time.addMinutes(target.time.v());
	}
}
