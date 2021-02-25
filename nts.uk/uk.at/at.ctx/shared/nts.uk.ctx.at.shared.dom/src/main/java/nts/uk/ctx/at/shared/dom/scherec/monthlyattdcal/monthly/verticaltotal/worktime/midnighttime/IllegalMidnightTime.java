package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.midnighttime;

import java.io.Serializable;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.TimeMonthWithCalculation;

/**
 * 法定外深夜時間
 * @author shuichi_ishida
 */
@Getter
public class IllegalMidnightTime implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

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
