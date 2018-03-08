package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import nts.uk.ctx.at.record.dom.daily.DeductionTotalTime;

/**
 * 控除時間計算(DomainService)
 * @author keisuke_hoshina
 *
 */
public interface CalculationDeductionTimeService {

	/**
	 *　控除合計時間の計算 
	 */
	public DeductionTotalTime createDudAllTime(ConditionAtr conditionAtr, DeductionAtr dedAtr,TimeSheetRoundingAtr pertimesheet, CalculationRangeOfOneDay oneDay);
}
