package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * ドメインサービス：月別実績を集計する
 * @author shuichi_ishida
 */
public interface AggregateMonthlyRecordService {

	/**
	 * 集計処理
	 * @param companyCode 会社コード
	 * @param employeeID 社員ID
	 * @param datePeriod 期間
	 * @return 集計結果
	 */
	AggregateMonthlyRecordValue aggregate(String companyCode, String employeeID, DatePeriod datePeriod);
}
