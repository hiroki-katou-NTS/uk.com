package nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.times.AttendanceTimesMonth;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

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
	 * @param predetermineTimeSet 所定時間設定
	 * @param isTwoTimesStampExists 2回目の打刻が存在するか
	 */
	public void aggregate(PredetemineTimeSetting predetermineTimeSet, boolean isTwoTimesStampExists){

		if (predetermineTimeSet == null) return;
		
		// 2回勤務かどうかの判断処理　（2回勤務でなければ、無視）
		if (!predetermineTimeSet.getPrescribedTimezoneSetting().isUseShiftTwo()) return;
		
		// 2回目の打刻が存在する時、1回加算
		if (isTwoTimesStampExists) this.times = this.times.addTimes(1);
	}

	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(TwoTimesWorkTimesOfMonthly target){
		
		this.times = this.times.addTimes(target.times.v());
	}
}
