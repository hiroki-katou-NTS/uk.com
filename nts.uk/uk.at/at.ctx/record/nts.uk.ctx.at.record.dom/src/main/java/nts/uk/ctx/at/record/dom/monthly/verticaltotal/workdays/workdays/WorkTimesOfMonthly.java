package nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays;

import java.util.List;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimesMonth;

/**
 * 月別実績の勤務回数
 * @author shuichu_ishida
 */
@Getter
public class WorkTimesOfMonthly {

	/** 回数 */
	private AttendanceTimesMonth times;
	
	/**
	 * コンストラクタ
	 */
	public WorkTimesOfMonthly(){
		
		this.times = new AttendanceTimesMonth(0);
	}
	
	/**
	 * ファクトリー
	 * @param times 回数
	 * @return 月別実績の勤務回数
	 */
	public static WorkTimesOfMonthly of(AttendanceTimesMonth times){
		
		val domain = new WorkTimesOfMonthly();
		domain.times = times;
		return domain;
	}
	
	/**
	 * 集計
	 * @param attendanceTimeOfDailys 日別実績の勤怠時間リスト
	 */
	public void aggregate(List<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailys){
		
		this.times = new AttendanceTimesMonth(0);
		for (val attendanceTimeOfDaily : attendanceTimeOfDailys){
			val actualWorkingTime = attendanceTimeOfDaily.getActualWorkingTimeOfDaily();
			val totalWorkingTime = actualWorkingTime.getTotalWorkingTime();
			
			// 日別実績の「勤務回数」を集計する
			this.times = this.times.addTimes(totalWorkingTime.getWorkTimes().v());
		}
	}
}
