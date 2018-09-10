package nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.times.AttendanceTimesMonth;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;

/**
 * 月別実績の臨時勤務回数
 * @author shuichu_ishida
 */
@Getter
public class TemporaryWorkTimesOfMonthly {

	/** 回数 */
	private AttendanceTimesMonth times;
	
	/**
	 * コンストラクタ
	 */
	public TemporaryWorkTimesOfMonthly(){
		
		this.times = new AttendanceTimesMonth(0);
	}
	
	/**
	 * ファクトリー
	 * @param times 回数
	 * @return 月別実績の臨時勤務回数
	 */
	public static TemporaryWorkTimesOfMonthly of(AttendanceTimesMonth times){
		
		val domain = new TemporaryWorkTimesOfMonthly();
		domain.times = times;
		return domain;
	}
	
	/**
	 * 集計
	 * @param temporaryTimeOfDaily 日別実績の臨時出退勤
	 */
	public void aggregate(TemporaryTimeOfDailyPerformance temporaryTimeOfDaily){

		if (temporaryTimeOfDaily == null) return;
		
		// 勤務回数を計算
		this.times = this.times.addTimes(temporaryTimeOfDaily.getWorkTimes().v());
	}

	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(TemporaryWorkTimesOfMonthly target){
		
		this.times = this.times.addTimes(target.times.v());
	}
}
