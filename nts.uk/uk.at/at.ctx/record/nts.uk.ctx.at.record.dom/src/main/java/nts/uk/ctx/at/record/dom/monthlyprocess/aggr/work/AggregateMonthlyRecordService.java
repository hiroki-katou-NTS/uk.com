package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work;

import nts.arc.time.YearMonth;
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
	 * @param datePeriod 期間
	 * @return 集計結果
	 */
	AggregateMonthlyRecordValue aggregate(String companyId, String employeeId, YearMonth yearMonth, DatePeriod datePeriod);
}
