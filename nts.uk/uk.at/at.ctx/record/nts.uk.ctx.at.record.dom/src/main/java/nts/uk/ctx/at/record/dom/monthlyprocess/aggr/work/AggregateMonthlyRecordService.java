package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * ドメインサービス：月別実績を集計する
 * @author shuichi_ishida
 */
public interface AggregateMonthlyRecordService {

	/**
	 * 集計処理
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param datePeriod 期間
	 * @param prevAggrResult 前回集計結果　（年休積立年休の集計結果）
	 * @return 集計結果
	 */
	AggregateMonthlyRecordValue aggregate(String companyId, String employeeId,
			YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate, DatePeriod datePeriod,
			AggrResultOfAnnAndRsvLeave prevAggrResult);
}
