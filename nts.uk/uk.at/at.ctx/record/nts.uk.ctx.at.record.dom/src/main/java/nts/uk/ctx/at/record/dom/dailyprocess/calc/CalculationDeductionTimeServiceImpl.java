package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.daily.DeductionTotalTime;

/**
 * 控除時間計算(DomainService)
 * @author keisuke_hoshina
 *
 */

@Stateless
public class CalculationDeductionTimeServiceImpl implements CalculationDeductionTimeService{

	@Override
	public DeductionTotalTime createDudAllTime(ConditionAtr conditionAtr, DeductionAtr dedAtr,
			TimeSheetRoundingAtr pertimesheet, CalculationRangeOfOneDay oneDay) {
		// TODO Auto-generated method stub
		return null;
	}

}
