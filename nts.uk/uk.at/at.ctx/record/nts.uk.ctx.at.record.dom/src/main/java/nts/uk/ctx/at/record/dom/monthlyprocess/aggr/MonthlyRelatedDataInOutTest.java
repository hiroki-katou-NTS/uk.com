package nts.uk.ctx.at.record.dom.monthlyprocess.aggr;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.AggregateMonthlyRecordValue;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * テスト：月別実績関連データの永続化入出力
 * @author shuichu_ishida
 */
public interface MonthlyRelatedDataInOutTest {

	/**
	 * 集計処理
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param datePeriod 期間
	 * @return 集計結果
	 */
	AggregateMonthlyRecordValue aggregate(String companyId, String employeeId,
			YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate, DatePeriod datePeriod);
}
