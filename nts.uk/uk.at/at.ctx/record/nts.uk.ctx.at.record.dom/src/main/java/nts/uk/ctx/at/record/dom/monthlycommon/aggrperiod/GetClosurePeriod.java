package nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionType;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureIdHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureIdHistory;

/**
 * 集計期間を取得する
 * @author shuichi_ishida
 */
public class GetClosurePeriod {

	/**
	 * 集計期間を取得する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 * @param yearMonthOpt 年月（度）
	 * @param closureIdOpt 締めID
	 * @param executionTypeOpt 実行区分（通常、再実行）
	 * @return 締め処理期間リスト
	 */
	public static List<ClosurePeriod> get(RequireM1 require, String companyId, String employeeId, GeneralDate criteriaDate,
			Optional<YearMonth> yearMonthOpt, Optional<ClosureId> closureIdOpt, Optional<ExecutionType> executionTypeOpt) {
		
		// ※　Optinal引数の組み合わせ不足等エラーは、throw new RuntimeError...で。
		//    現時点では、引数の利用が未設計。（2018.3.15 shuichi_ishida）
		
		// 集計すべき期間を計算
		return CalcPeriodForAggregate.algorithm(require, employeeId, criteriaDate);
	}
	
	/**
	 * 年月を指定して集計期間を求める
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 * @param yearMonth 対象年月
	 * @return 締め処理期間リスト
	 */
	public static List<ClosurePeriod> fromYearMonth(RequireM2 require, CacheCarrier cacheCarrier, 
			String employeeId, GeneralDate criteriaDate, YearMonth yearMonth) {

		// 指定した年月の社員の締め履歴を取得する
		List<ClosureIdHistory> closureIdHistoryList =
				GetClosureIdHistory.ofEmployeeFromYearMonth(require, cacheCarrier, employeeId, yearMonth);
		
		// 締め履歴から集計期間を生成
		List<ClosurePeriod> closurePeriods = CalcPeriodForAggregate.fromClosureHistory(
				require, employeeId, criteriaDate, closureIdHistoryList);
		
		// 集計期間から指定した年月以外を削除する
		ListIterator<ClosurePeriod> itrClosurePeriods = closurePeriods.listIterator();
		while (itrClosurePeriods.hasNext()) {
			ClosurePeriod target = itrClosurePeriods.next();
			ListIterator<AggrPeriodEachActualClosure> itrAggrPeriods = target.getAggrPeriods().listIterator();
			while (itrAggrPeriods.hasNext()) {
				AggrPeriodEachActualClosure aggrPeriod = itrAggrPeriods.next();
				if (!aggrPeriod.getYearMonth().equals(yearMonth)) itrAggrPeriods.remove();
			}
			if (target.getAggrPeriods().size() == 0) itrClosurePeriods.remove();
		}
		
		// 締め処理期間リストを返す
		return closurePeriods;
	}
	
	/**
	 * 期間を指定して集計期間を求める
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 * @param period 対象期間
	 * @return 締め処理期間リスト
	 */
	public static List<ClosurePeriod> fromPeriod(RequireM2 require, CacheCarrier cacheCarrier, 
			String employeeId, GeneralDate criteriaDate, DatePeriod period) {

		// 指定した期間の社員の締め履歴を取得する
		List<ClosureIdHistory> closureIdHistoryList =
				GetClosureIdHistory.ofEmployeeFromPeriod(require, cacheCarrier, employeeId, period);
		
		// 締め履歴から集計期間を生成
		return CalcPeriodForAggregate.fromClosureHistory(require, employeeId, criteriaDate, closureIdHistoryList);
	}
	
	public static interface RequireM1 extends CalcPeriodForAggregate.RequireM2 {}
	
	public static interface RequireM2 extends GetClosureIdHistory.RequireM2, CalcPeriodForAggregate.RequireM1 {}
	
}
