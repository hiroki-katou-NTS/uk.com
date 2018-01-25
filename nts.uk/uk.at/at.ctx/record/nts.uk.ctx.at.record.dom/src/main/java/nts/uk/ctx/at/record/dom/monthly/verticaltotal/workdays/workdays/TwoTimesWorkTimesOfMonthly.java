package nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimesMonth;

/**
 * 月別実績の二回勤務回数
 * @author shuichu_ishida
 */
@Getter
public class TwoTimesWorkTimesOfMonthly {

	/** 回数 */
	private AttendanceTimesMonth times;
	
	/**
	 * コンストラクタ
	 */
	public TwoTimesWorkTimesOfMonthly(){
		
		this.times = new AttendanceTimesMonth(0);
	}
	
	/**
	 * ファクトリー
	 * @param times 回数
	 * @return 月別実績の二回勤務回数
	 */
	public static TwoTimesWorkTimesOfMonthly of(AttendanceTimesMonth times){
		
		val domain = new TwoTimesWorkTimesOfMonthly();
		domain.times = times;
		return domain;
	}
	
	/**
	 * 集計
	 */
	public void aggregate(){
		
		this.times = new AttendanceTimesMonth(0);
	}
}
