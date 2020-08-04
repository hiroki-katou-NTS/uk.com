package nts.uk.ctx.at.record.dom.monthly.agreement;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyAggregateAtr;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.RepositoriesRequiredByMonthlyAggr;
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
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	public void aggregate(
			GeneralDate criteriaDate,
			MonthlyAggregateAtr aggregateAtr,
			MonthlyCalculation monthlyCalculation,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		// 36協定時間の対象を取得
		this.breakdown.getTargetItemOfAgreement(aggregateAtr, monthlyCalculation, repositories);
		
		// 36協定時間内訳の合計時間を36協定時間とする
		this.agreementTime.setAgreementTime(this.breakdown.getTotalTime());
		
		// エラーアラーム値の取得
		this.agreementTime.getErrorAlarmValue(
				monthlyCalculation.getCompanyId(), monthlyCalculation.getEmployeeId(), criteriaDate,
				monthlyCalculation.getYearMonth(), monthlyCalculation.getWorkingSystem(), repositories);
		
		// エラーチェック
		this.agreementTime.errorCheck();
	}
	
	/**
	 * 作成　（週用）
	 * @param criteriaDate 基準日
	 * @param aggregateAtr 集計区分
	 * @param weeklyCalculation 週別の計算
	 * @param companySets 月別集計で必要な会社別設定
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	public void aggregateForWeek(
			GeneralDate criteriaDate,
			MonthlyAggregateAtr aggregateAtr,
			WeeklyCalculation weeklyCalculation,
			MonAggrCompanySettings companySets,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		// 36協定時間の対象を取得
		this.breakdown.getTargetItemOfAgreementForWeek(aggregateAtr, weeklyCalculation, companySets);
		
		// 36協定時間内訳の合計時間を36協定時間とする
		this.agreementTime.setAgreementTime(this.breakdown.getTotalTime());
		
		// エラーアラーム値の取得
		this.agreementTime.getErrorAlarmValueForWeek(
				weeklyCalculation.getCompanyId(), weeklyCalculation.getEmployeeId(),
				criteriaDate, weeklyCalculation.getWorkingSystem(), repositories);
		
		// エラーチェック
		this.agreementTime.errorCheck();
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
