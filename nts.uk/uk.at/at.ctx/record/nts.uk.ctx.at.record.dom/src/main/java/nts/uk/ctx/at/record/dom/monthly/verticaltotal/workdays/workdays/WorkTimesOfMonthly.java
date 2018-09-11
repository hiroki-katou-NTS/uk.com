package nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.common.times.AttendanceTimesMonth;

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
	 * @param attendanceTimeOfDaily 日別実績の勤怠時間
	 */
	public void aggregate(AttendanceTimeOfDailyPerformance attendanceTimeOfDaily){

		if (attendanceTimeOfDaily == null) return;
		
		val actualWorkingTime = attendanceTimeOfDaily.getActualWorkingTimeOfDaily();
		val totalWorkingTime = actualWorkingTime.getTotalWorkingTime();
		
		// 日別実績の「勤務回数」を集計する
		this.times = this.times.addTimes(totalWorkingTime.getWorkTimes().v());
	}

	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(WorkTimesOfMonthly target){
		
		this.times = this.times.addTimes(target.times.v());
	}
}
