package nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.WorkTypeDaysCountTable;

/**
 * 月別実績の所定日数
 * @author shuichu_ishida
 */
@Getter
public class PredeterminedDaysOfMonthly {

	/** 所定日数 */
	private AttendanceDaysMonth predeterminedDays;
	
	/**
	 * コンストラクタ
	 */
	public PredeterminedDaysOfMonthly(){
		
		this.predeterminedDays = new AttendanceDaysMonth(0.0);
	}

	/**
	 * ファクトリー
	 * @param predeterminedDays 所定日数
	 * @return 月別実績の所定日数
	 */
	public static PredeterminedDaysOfMonthly of(
			AttendanceDaysMonth predeterminedDays){
		
		val domain = new PredeterminedDaysOfMonthly();
		domain.predeterminedDays = predeterminedDays;
		return domain;
	}
	
	/**
	 * 集計
	 * @param workTypeDaysCountTable 勤務種類の日数カウント表
	 */
	public void aggregate(WorkTypeDaysCountTable workTypeDaysCountTable){
		
		if (workTypeDaysCountTable == null) return;
			
		// 所定日数に加算
		this.predeterminedDays =
				this.predeterminedDays.addDays(workTypeDaysCountTable.getPredetermineDays().v());
	}

	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(PredeterminedDaysOfMonthly target){
		
		this.predeterminedDays = this.predeterminedDays.addDays(target.predeterminedDays.v());
	}
}
