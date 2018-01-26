package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.timevarience;

import java.util.List;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 月別実績の予実差異時間
 * @author shuichu_ishida
 */
@Getter
public class BudgetTimeVarienceOfMonthly {

	/** 時間 */
	private AttendanceTimeMonth time;
	
	/**
	 * コンストラクタ
	 */
	public BudgetTimeVarienceOfMonthly(){
		
		this.time = new AttendanceTimeMonth(0);
	}
	
	/**
	 * ファクトリー
	 * @param time 時間
	 * @return 月別実績の予実差異時間
	 */
	public static BudgetTimeVarienceOfMonthly of(AttendanceTimeMonth time){
		
		val domain = new BudgetTimeVarienceOfMonthly();
		domain.time = time;
		return domain;
	}
	
	/**
	 * 集計
	 * @param attendanceTimeOfDailys 日別実績の勤怠時間リスト
	 */
	public void aggregate(List<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailys){
		
		this.time = new AttendanceTimeMonth(0);
		
		// 日別実績の「予実差異時間」を集計する
		for (val attendanceTimeOfDaily : attendanceTimeOfDailys){
			this.time = this.time.addMinutes(attendanceTimeOfDaily.getBudgetTimeVariance().v());
		}
	}
}
