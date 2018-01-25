package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.midnighttime;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 法定外深夜時間
 * @author shuichu_ishida
 */
@Getter
public class IllegalMidnightTime {

	/** 時間 */
	private TimeMonthWithCalculation time;
	/** 事前時間 */
	private AttendanceTimeMonth beforeTime;
	
	/**
	 * コンストラクタ
	 */
	public IllegalMidnightTime(){
		
		this.time = TimeMonthWithCalculation.ofSameTime(0);
		this.beforeTime = new AttendanceTimeMonth(0);
	}
	
	/**
	 * ファクトリー
	 * @param time 時間
	 * @param beforeTime 事前時間
	 * @return 法定外深夜時間
	 */
	public static IllegalMidnightTime of(
			TimeMonthWithCalculation time,
			AttendanceTimeMonth beforeTime){
		
		val domain = new IllegalMidnightTime();
		domain.time = time;
		domain.beforeTime = beforeTime;
		return domain;
	}
	
	/**
	 * 時間に分を加算する
	 * @param minutes 分
	 * @param calcMinutes 分（計算用）
	 */
	public void addMinutesToTime(int minutes, int calcMinutes){
		this.time = this.time.addMinutes(minutes, calcMinutes);
	}
	
	/**
	 * 事前時間に分を加算する
	 * @param minutes 分
	 */
	public void addMinutesToBeforeTime(int minutes){
		this.beforeTime = this.beforeTime.addMinutes(minutes);
	}
}
