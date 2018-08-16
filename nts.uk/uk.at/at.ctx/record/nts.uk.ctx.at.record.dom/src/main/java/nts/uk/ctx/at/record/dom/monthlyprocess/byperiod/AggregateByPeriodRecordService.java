package nts.uk.ctx.at.record.dom.monthlyprocess.byperiod;

import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.OptionalAggrPeriod;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 任意期間集計Mgr　（アルゴリズム）
 * @author shuichu_ishida
 */
public interface AggregateByPeriodRecordService {

	/**
	 * アルゴリズム
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @param optionalPeriod 任意集計期間
	 * @param companySets 月別集計で必要な会社別設定
	 * @param employeeSets 月別集計で必要な社員別設定
	 * @return 集計結果
	 */
	AggregateByPeriodRecordValue algorithm(String companyId, String employeeId, DatePeriod period,
			OptionalAggrPeriod optionalPeriod,
			MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets);
}
