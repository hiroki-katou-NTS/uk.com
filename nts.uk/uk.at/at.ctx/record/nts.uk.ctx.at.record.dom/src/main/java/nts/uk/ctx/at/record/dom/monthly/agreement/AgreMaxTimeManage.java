package nts.uk.ctx.at.record.dom.monthly.agreement;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyAggregateAtr;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyCalculation;

/**
 * 36協定上限時間管理
 * @author shuichi_ishida
 */
@Getter
public class AgreMaxTimeManage {

	/** 36協定時間 */
	private AgreMaxTimeOfMonthly agreementTime;
	/** 内訳 */
	private AgreMaxTimeBreakdown breakdown;
	
	/**
	 * コンストラクタ
	 */
	public AgreMaxTimeManage(){
		
		this.agreementTime = new AgreMaxTimeOfMonthly();
		this.breakdown = new AgreMaxTimeBreakdown();
	}
	
	/**
	 * ファクトリー
	 * @param agreementTime 36協定時間
	 * @param breakdown 内訳
	 * @return 36協定上限時間管理
	 */
	public static AgreMaxTimeManage of(
			AgreMaxTimeOfMonthly agreementTime,
			AgreMaxTimeBreakdown breakdown){
		
		AgreMaxTimeManage domain = new AgreMaxTimeManage();
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
	public void aggregate(RequireM1 require,
			GeneralDate criteriaDate,
			MonthlyAggregateAtr aggregateAtr,
			MonthlyCalculation monthlyCalculation){
		
		// 36協定上限時間の対象時間を取得
		this.breakdown.getTargetItemOfAgreMax(require, aggregateAtr, monthlyCalculation);
		
		// 合計時間を取得
		this.agreementTime.setAgreementTime(this.breakdown.getTotalTime());
		
		// 上限時間の取得
		this.agreementTime.setMaxTime(require,
				monthlyCalculation.getCompanyId(), monthlyCalculation.getEmployeeId(), criteriaDate,
				monthlyCalculation.getWorkingSystem());
		
		// エラーチェック
		this.agreementTime.errorCheck();
	}
	
	public static interface RequireM1 extends AgreMaxTimeBreakdown.RequireM1, AgreMaxTimeOfMonthly.RequireM1 {
		
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(AgreMaxTimeManage target){
		this.agreementTime.sum(target.agreementTime);
		this.breakdown.sum(target.breakdown);
	}
}
