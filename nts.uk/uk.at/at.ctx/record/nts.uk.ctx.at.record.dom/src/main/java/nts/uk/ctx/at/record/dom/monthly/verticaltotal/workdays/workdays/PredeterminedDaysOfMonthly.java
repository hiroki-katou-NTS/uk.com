package nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.WorkTypeDaysCountTable;

/**
 * 月別実績の所定日数
 * @author shuichu_ishida
 */
@Getter
public class PredeterminedDaysOfMonthly {

	/** 所定日数 */
	private AttendanceDaysMonth predeterminedDays;
	/** 所定日数付与前 */
	private AttendanceDaysMonth predeterminedDaysBeforeGrant;
	/** 所定日数付与後 */
	private AttendanceDaysMonth predeterminedDaysAfterGrant;
	
	/**
	 * コンストラクタ
	 */
	public PredeterminedDaysOfMonthly(){
		
		this.predeterminedDays = new AttendanceDaysMonth(0.0);
		this.predeterminedDaysBeforeGrant = new AttendanceDaysMonth(0.0);
		this.predeterminedDaysAfterGrant = new AttendanceDaysMonth(0.0);
	}

	/**
	 * ファクトリー
	 * @param predeterminedDays 所定日数
	 * @param predeterminedDaysBeforeGrant 所定日数付与前
	 * @param predeterminedDaysAfterGrant 所定日数付与後
	 * @return 月別実績の所定日数
	 */
	public static PredeterminedDaysOfMonthly of(
			AttendanceDaysMonth predeterminedDays,
			AttendanceDaysMonth predeterminedDaysBeforeGrant,
			AttendanceDaysMonth predeterminedDaysAfterGrant){
		
		val domain = new PredeterminedDaysOfMonthly();
		domain.predeterminedDays = predeterminedDays;
		domain.predeterminedDaysBeforeGrant = predeterminedDaysBeforeGrant;
		domain.predeterminedDaysAfterGrant = predeterminedDaysAfterGrant;
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

		//*****（未）　付与前・付与後の振り分けは、年休残数管理が実装されるまで、保留。
	}

	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(PredeterminedDaysOfMonthly target){
		
		this.predeterminedDays = this.predeterminedDays.addDays(target.predeterminedDays.v());
		this.predeterminedDaysBeforeGrant = this.predeterminedDaysBeforeGrant.addDays(target.predeterminedDaysBeforeGrant.v());
		this.predeterminedDaysAfterGrant = this.predeterminedDaysAfterGrant.addDays(target.predeterminedDaysAfterGrant.v());
	}
}
