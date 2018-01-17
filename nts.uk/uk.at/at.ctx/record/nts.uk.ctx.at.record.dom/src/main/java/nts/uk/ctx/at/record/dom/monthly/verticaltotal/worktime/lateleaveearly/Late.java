package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.lateleaveearly;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimesMonth;
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
}
