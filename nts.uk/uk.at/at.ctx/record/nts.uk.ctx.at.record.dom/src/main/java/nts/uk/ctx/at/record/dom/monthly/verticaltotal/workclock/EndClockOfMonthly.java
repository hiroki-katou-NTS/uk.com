package nts.uk.ctx.at.record.dom.monthly.verticaltotal.workclock;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimesMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 月別実績の終業時刻
 * @author shuichu_ishida
 */
@Getter
public class EndClockOfMonthly {

	/** 回数 */
	private AttendanceTimesMonth times;
	/** 合計時刻 */
	private AttendanceTimeMonth totalClock;
	/** 平均時刻 */
	private AttendanceTimeMonth averageClock;
	
	/**
	 * コンストラクタ
	 */
	public EndClockOfMonthly(){
		
		this.times = new AttendanceTimesMonth(0);
		this.totalClock = new AttendanceTimeMonth(0);
		this.averageClock = new AttendanceTimeMonth(0);
	}
	
	/**
	 * ファクトリー
	 * @param times 回数
	 * @param totalClock 合計時刻
	 * @param averageClock 平均時刻
	 * @return 月別実績の終業時刻
	 */
	public static EndClockOfMonthly of(
			AttendanceTimesMonth times,
			AttendanceTimeMonth totalClock,
			AttendanceTimeMonth averageClock){
		
		EndClockOfMonthly domain = new EndClockOfMonthly();
		domain.times = times;
		domain.totalClock = totalClock;
		domain.averageClock = averageClock;
		return domain;
	}
	
	/**
	 * 集計
	 */
	public void aggregate(){
		
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(EndClockOfMonthly target){
		
		this.times = this.times.addTimes(target.times.v());
		this.totalClock = this.totalClock.addMinutes(target.totalClock.v());
		
		this.averageClock = new AttendanceTimeMonth(0);
		if (this.times.v() != 0){
			this.averageClock = new AttendanceTimeMonth(this.totalClock.v() / this.times.v());
		}
	}
}
