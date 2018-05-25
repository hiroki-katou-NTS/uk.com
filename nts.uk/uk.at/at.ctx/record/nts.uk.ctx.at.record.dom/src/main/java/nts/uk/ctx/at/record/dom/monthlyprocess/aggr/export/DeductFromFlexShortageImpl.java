package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyAggregateAtr;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.MonthlyAggregationErrorInfo;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.RepositoriesRequiredByMonthlyAggr;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 実装：フレックス不足から年休と欠勤を控除する
 * @author shuichu_ishida
 */
@Stateless
public class DeductFromFlexShortageImpl implements DeductFromFlexShortage {

	/** 月別集計が必要とするリポジトリ */
	@Inject
	private RepositoriesRequiredByMonthlyAggr repositories;
	
	/** フレックス不足から年休と欠勤を控除する */
	@Override
	public DeductFromFlexShortageValue calc(String companyId, String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate, DatePeriod period, AttendanceDaysMonth annualLeaveDeductDays,
			AttendanceTimeMonth absenceDeductTime) {
		
		DeductFromFlexShortageValue returnValue = new DeductFromFlexShortageValue();
		MonthlyCalculation monthlyCalculation = new MonthlyCalculation();
		
		// 労働条件項目を取得する
		val workConditionItemOpt =
				this.repositories.getWorkingConditionItem().getBySidAndStandardDate(employeeId, period.end());
		if (!workConditionItemOpt.isPresent()){
			returnValue.getErrorInfos().add(new MonthlyAggregationErrorInfo(
					"099", new ErrMessageContent("not exist WorkingConditionItem")));
			return returnValue;
		}
		val workConditionItem = workConditionItemOpt.get();
		
		// 履歴ごとに月別実績を集計する
		monthlyCalculation.prepareAggregation(companyId, employeeId, yearMonth, closureId, closureDate,
				period, workConditionItem, Optional.empty(), 1, this.repositories);
		for (val errorInfo : monthlyCalculation.getErrorInfos()){
			if (errorInfo.getResourceId().compareTo("002") == 0) return returnValue;
		}
		monthlyCalculation.aggregate(period, MonthlyAggregateAtr.MONTHLY,
				Optional.of(annualLeaveDeductDays), Optional.of(absenceDeductTime), this.repositories);
		returnValue.getErrorInfos().addAll(monthlyCalculation.getErrorInfos());
		
		// 「月別実績の月の計算」を返す
		returnValue.setMonthlyCalculation(monthlyCalculation);
		return returnValue;
	}
}
