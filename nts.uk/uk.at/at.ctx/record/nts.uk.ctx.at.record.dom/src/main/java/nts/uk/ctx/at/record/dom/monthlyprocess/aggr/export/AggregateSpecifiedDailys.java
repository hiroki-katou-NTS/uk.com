package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export;

import java.util.List;
import java.util.Optional;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.IntegrationOfMonthly;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 指定した日別実績を集計
 * @author shuichu_ishida
 */
public interface AggregateSpecifiedDailys {

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
	Optional<IntegrationOfMonthly> algorithm(String companyId, String employeeId,
			YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate, DatePeriod period,
			Optional<String> empCalAndSumExecLogID,
			List<IntegrationOfDaily> dailyWorks, Optional<IntegrationOfMonthly> monthlyWork);
}
