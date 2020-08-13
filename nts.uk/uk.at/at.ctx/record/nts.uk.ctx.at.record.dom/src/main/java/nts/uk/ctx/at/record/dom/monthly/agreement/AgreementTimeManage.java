package nts.uk.ctx.at.record.dom.monthly.agreement;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyAggregateAtr;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.weekly.WeeklyCalculation;

/**
 * 36協定時間管理
 * @author shuichi_ishida
 */
@Getter
public class AgreementTimeManage {

	/** 36協定時間 */
	private AgreementTimeOfMonthly agreementTime;
	/** 内訳 */
	private AgreementTimeBreakdown breakdown;
	
	/**
	 * コンストラクタ
	 */
	public AgreementTimeManage(){
		
		this.agreementTime = new AgreementTimeOfMonthly();
		this.breakdown = new AgreMaxTimeBreakdown();
	}
	
	/**
	 * ファクトリー
	 * @param agreementTime 36協定時間
	 * @param breakdown 内訳
	 * @return 36協定時間管理
	 */
	public static AgreementTimeManage of(
			AgreementTimeOfMonthly agreementTime,
			AgreementTimeBreakdown breakdown){
		
		AgreementTimeManage domain = new AgreementTimeManage();
		domain.agreementTime = agreementTime;
		domain.breakdown = breakdown;
		return domain;
	}
	
	/**
	 * 作成
	 * @param criteriaDate 基準日
	 * @param aggregateAtr 集計区分
	 * @param monthlyCalculation 月の計算
	 */
	public void aggregate(RequireM2 require, GeneralDate criteriaDate,
			MonthlyAggregateAtr aggregateAtr, MonthlyCalculation monthlyCalculation){
		
		// 36協定時間の対象を取得
		this.breakdown.getTargetItemOfAgreement(aggregateAtr, monthlyCalculation); 
		
		// 36協定時間内訳の合計時間を36協定時間とする
		this.agreementTime.setAgreementTime(this.breakdown.getTotalTime());
		
		// エラーアラーム値の取得
		this.agreementTime.getErrorAlarmValue(require, monthlyCalculation.getCompanyId(), 
				monthlyCalculation.getEmployeeId(), criteriaDate,
				monthlyCalculation.getYearMonth(), monthlyCalculation.getWorkingSystem());
		
		// エラーチェック
		this.agreementTime.errorCheck();
	}
	
	/**
	 * 作成　（週用）
	 * @param criteriaDate 基準日
	 * @param aggregateAtr 集計区分
	 * @param weeklyCalculation 週別の計算
	 * @param companySets 月別集計で必要な会社別設定
	 */
	public void aggregateForWeek(RequireM1 require, GeneralDate criteriaDate,
			MonthlyAggregateAtr aggregateAtr, WeeklyCalculation weeklyCalculation,
			MonAggrCompanySettings companySets){
		
		// 36協定時間の対象を取得
		this.breakdown.getTargetItemOfAgreementForWeek(aggregateAtr, weeklyCalculation, companySets);
		
		// 36協定時間内訳の合計時間を36協定時間とする
		this.agreementTime.setAgreementTime(this.breakdown.getTotalTime());
		
		// エラーアラーム値の取得
		this.agreementTime.getErrorAlarmValueForWeek(require, 
				weeklyCalculation.getCompanyId(), weeklyCalculation.getEmployeeId(),
				criteriaDate, weeklyCalculation.getWorkingSystem());
		
		// エラーチェック
		this.agreementTime.errorCheck();
	}
	
	public static interface RequireM2 extends AgreementTimeOfMonthly.RequireM2 {
		
	}
	
	public static interface RequireM1 extends AgreementTimeOfMonthly.RequireM1 {
		
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(AgreementTimeManage target){
		this.agreementTime.sum(target.agreementTime);
		this.breakdown.sum(target.breakdown);
	}
}
