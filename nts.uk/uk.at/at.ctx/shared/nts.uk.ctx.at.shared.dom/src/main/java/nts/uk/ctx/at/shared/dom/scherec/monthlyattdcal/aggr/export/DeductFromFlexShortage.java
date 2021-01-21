package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.export;

import java.util.Optional;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.MonthlyAggregationErrorInfo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonthlyOldDatas;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.MonthlyAggregateAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * フレックス不足から年休と欠勤を控除する
 * @author shuichu_ishida
 */
public class DeductFromFlexShortage {

	/**
	 * フレックス不足から年休と欠勤を控除する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 * @param period 期間
	 * @param annualLeaveDeductDays 年休控除日数
	 * @param absenceDeductTime 欠勤控除時間
	 * @return 月別実績の月の計算、エラーメッセージID
	 */
	public static DeductFromFlexShortageValue calc(RequireM1 require, CacheCarrier cacheCarrier, 
			String companyId, String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate, DatePeriod period, 
			AttendanceDaysMonth annualLeaveDeductDays, AttendanceTimeMonth absenceDeductTime) {
		
		DeductFromFlexShortageValue returnValue = new DeductFromFlexShortageValue();
		MonthlyCalculation monthlyCalculation = new MonthlyCalculation();
		
		// 月別集計で必要な会社別設定を取得
		val companySets = MonAggrCompanySettings.loadSettings(require, companyId);
		
		// 月別集計で必要な社員別設定を取得
		val employeeSets = MonAggrEmployeeSettings.loadSettings(require, cacheCarrier, 
				companyId, employeeId, period);
		if (employeeSets.getErrorInfos().size() > 0){
			for (val errorInfo : employeeSets.getErrorInfos().entrySet()){
				returnValue.getErrorInfos().add(new MonthlyAggregationErrorInfo(
						errorInfo.getKey(), errorInfo.getValue()));
			}
			return returnValue;
		}
		
		// 労働条件項目を取得する
		val workConditionItemOpt = require.workingConditionItem(employeeId, period.end());
		if (!workConditionItemOpt.isPresent()){
			returnValue.getErrorInfos().add(new MonthlyAggregationErrorInfo(
					"006", new ErrMessageContent(TextResource.localize("Msg_430"))));
			return returnValue;
		}
		val workConditionItem = workConditionItemOpt.get();

		// 集計に必要な日別実績データを取得する
		MonthlyCalculatingDailys monthlyCalcDailys = MonthlyCalculatingDailys.loadData(require,
				employeeId, period, employeeSets);

		// 集計前の月別実績データを確認する
		MonthlyOldDatas monthlyOldDatas = MonthlyOldDatas.loadData(require,
				employeeId, yearMonth, closureId, closureDate);
		
		// 履歴ごとに月別実績を集計する
		monthlyCalculation.prepareAggregation(require, cacheCarrier, companyId, employeeId, 
				yearMonth, closureId, closureDate, period, workConditionItem, 1, 
				companySets, employeeSets, monthlyCalcDailys, monthlyOldDatas);
		if (monthlyCalculation.getErrorInfos().size() > 0){
			return returnValue;
		}
		monthlyCalculation.aggregate(require, cacheCarrier, period, MonthlyAggregateAtr.MONTHLY,
				Optional.of(annualLeaveDeductDays), Optional.of(absenceDeductTime),
				Optional.empty());
		returnValue.getErrorInfos().addAll(monthlyCalculation.getErrorInfos());
		
		// 「月別実績の月の計算」を返す
		returnValue.setMonthlyCalculation(monthlyCalculation);
		return returnValue;
	}
	
	public static interface RequireM1 extends MonAggrCompanySettings.RequireM6,
		MonthlyOldDatas.RequireM1, MonAggrEmployeeSettings.RequireM2,
		MonthlyCalculatingDailys.RequireM4, MonthlyCalculation.RequireM5, MonthlyCalculation.RequireM4 {
		
	}
}
