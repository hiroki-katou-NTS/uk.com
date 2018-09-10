package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.lateleaveearly;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.common.times.AttendanceTimesMonth;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;

/**
 * 遅刻
 * @author shuichu_ishida
 */
@Getter
public class Late {

	/** 回数 */
	private AttendanceTimesMonth times;
	/** 時間 */
	private TimeMonthWithCalculation time;
	
	/**
	 * コンストラクタ
	 */
	public Late(){
		
		this.times = new AttendanceTimesMonth(0);
		this.time = TimeMonthWithCalculation.ofSameTime(0);
	}
	
	/**
	 * ファクトリー
	 * @param times 回数
	 * @param time 時間
	 * @return 早退
	 */
	public static Late of(AttendanceTimesMonth times, TimeMonthWithCalculation time){
		
		val domain = new Late();
		domain.times = times;
		domain.time = time;
		return domain;
	}
	
	/**
	 * 集計
	 * @param attendanceTimeOfDaily 日別実績の勤怠時間
	 */
	public void aggregate(AttendanceTimeOfDailyPerformance attendanceTimeOfDaily){
		
		if (attendanceTimeOfDaily == null) return;
		
		val totalWorkingTime = attendanceTimeOfDaily.getActualWorkingTimeOfDaily().getTotalWorkingTime();
		val lateTimeOfDailys = totalWorkingTime.getLateTimeOfDaily();
		for (val lateTimeOfDaily : lateTimeOfDailys){
			val lateTime = lateTimeOfDaily.getLateTime();
			
			// 回数をインクリメント
			if (lateTime.getTime().greaterThan(0)){
				this.times = this.times.addTimes(1);
			}
			
			// 時間を計算
			this.time = this.time.addMinutes(
					lateTime.getTime().v(),
					lateTime.getCalcTime().v());
		}
	}

	/**
	 * 回数に加算する
	 * @param times 回数
	 */
	public void addTimes(int times){
		this.times = this.times.addTimes(times);
	}

	/**
	 * 分を時間に加算する
	 * @param mitutes 分
	 * @param calcMinutes 計算分
	 */
	public void addMinutesToTime(int mitutes, int calcMinutes){
		this.time.addMinutes(mitutes, calcMinutes);
	}
}
