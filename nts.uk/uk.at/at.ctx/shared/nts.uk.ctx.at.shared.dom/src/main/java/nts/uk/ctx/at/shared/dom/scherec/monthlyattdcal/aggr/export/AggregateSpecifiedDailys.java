package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.export;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.AggregateMonthlyRecordService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.IntegrationOfMonthly;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * 指定した日別実績を集計
 * @author shuichu_ishida
 */
public class AggregateSpecifiedDailys {

	/**
	 * アルゴリズム
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param period 実行期間
	 * @param empCalAndSumExecLogID 就業計算と集計実行ログID
	 * @param dailyWorks 日別実績(WORK)List
	 * @param monthlyWork 月別実績(WORK)
	 * @return 月別実績(Work)
	 */
	public static Optional<IntegrationOfMonthly> algorithm(RequireM1 require, CacheCarrier cacheCarrier,
			String companyId, String employeeId, 
			YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate, DatePeriod period, 
			Optional<String> empCalAndSumExecLogID, List<IntegrationOfDaily> dailyWorks, 
			Optional<IntegrationOfMonthly> monthlyWork) {

		// 月別集計で必要な会社別設定を取得する
		val companySets = MonAggrCompanySettings.loadSettings(require, companyId);
		if (companySets.getErrorInfos().size() > 0){
			// エラー発生時
			return Optional.empty();
		}
		
		// 月別集計で必要な社員別設定を取得
		val employeeSets = MonAggrEmployeeSettings.loadSettings(require, cacheCarrier, companyId, employeeId, period);
		if (employeeSets.getErrorInfos().size() > 0){
			// エラー発生時
			return Optional.empty();
		}
		
		// 前回集計結果　（年休積立年休の集計結果）
		AggrResultOfAnnAndRsvLeave prevAggrResult = new AggrResultOfAnnAndRsvLeave();
		
		// 月別実績を集計する　（アルゴリズム）
		val value = AggregateMonthlyRecordService.aggregate(require, cacheCarrier, companyId, employeeId,
				yearMonth, closureId, closureDate, period,
				prevAggrResult, Optional.empty(), Optional.empty(), Collections.emptyMap(),
				companySets, employeeSets, Optional.of(dailyWorks), monthlyWork);
		if (value.getErrorInfos().size() > 0) {
			// エラー発生時
			return Optional.empty();
		}

		// 月別実績(Work)を返す
		return Optional.of(value.getIntegration());
	}
	
	public static interface RequireM1 extends MonAggrCompanySettings.RequireM6, MonAggrEmployeeSettings.RequireM2,
		AggregateMonthlyRecordService.RequireM2 {
		
	}
}
