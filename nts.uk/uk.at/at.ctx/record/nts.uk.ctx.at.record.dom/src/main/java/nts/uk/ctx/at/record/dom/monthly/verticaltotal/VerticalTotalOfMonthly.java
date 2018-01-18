package nts.uk.ctx.at.record.dom.monthly.verticaltotal;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.WorkDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.WorkTimesOfMonthly;

/**
 * 月別実績の縦計
 * @author shuichu_ishida
 */
@Getter
public class VerticalTotalOfMonthly {
	
	/** 勤務時間 */
	private WorkTimesOfMonthly workTime;
	/** 勤務日数 */
	private WorkDaysOfMonthly workDays;
	
	/**
	 * コンストラクタ
	 */
	public VerticalTotalOfMonthly(){
		
		this.workTime = new WorkTimesOfMonthly();
		this.workDays = new WorkDaysOfMonthly();
	}
	
	/**
	 * ファクトリー
	 * @param workTime 勤務時間
	 * @param workDays 勤務日数
	 * @return 月別実績の縦計
	 */
	public static VerticalTotalOfMonthly of(
			WorkTimesOfMonthly workTime,
			WorkDaysOfMonthly workDays){
		
		val domain = new VerticalTotalOfMonthly();
		domain.workTime = workTime;
		domain.workDays = workDays;
		return domain;
	}
}
