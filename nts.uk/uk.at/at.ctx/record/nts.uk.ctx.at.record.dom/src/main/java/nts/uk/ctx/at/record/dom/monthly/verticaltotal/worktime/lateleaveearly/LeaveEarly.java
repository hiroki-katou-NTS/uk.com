package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.lateleaveearly;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimesMonth;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;

/**
 * 早退
 * @author shuichu_ishida
 */
@Getter
public class LeaveEarly {

	/** 回数 */
	private AttendanceTimesMonth times;
	/** 時間 */
	private TimeMonthWithCalculation time;
	
	/**
	 * コンストラクタ
	 */
	public LeaveEarly(){
		
		this.times = new AttendanceTimesMonth(0);
		this.time = TimeMonthWithCalculation.ofSameTime(0);
	}
	
	/**
	 * ファクトリー
	 * @param times 回数
	 * @param time 時間
	 * @return 早退
	 */
	public static LeaveEarly of(AttendanceTimesMonth times, TimeMonthWithCalculation time){
		
		val domain = new LeaveEarly();
		domain.times = times;
		domain.time = time;
		return domain;
	}
}
