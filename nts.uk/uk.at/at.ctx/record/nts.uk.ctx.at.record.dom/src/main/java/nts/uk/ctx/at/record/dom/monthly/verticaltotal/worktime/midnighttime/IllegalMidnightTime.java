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
}
