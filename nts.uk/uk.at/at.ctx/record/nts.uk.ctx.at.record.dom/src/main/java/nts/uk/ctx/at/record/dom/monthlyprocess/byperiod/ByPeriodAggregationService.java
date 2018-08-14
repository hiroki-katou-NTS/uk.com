package nts.uk.ctx.at.record.dom.monthlyprocess.byperiod;

import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.CreateDailyResultDomainServiceImpl.ProcessState;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.OptionalAggrPeriod;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 任意期間集計Mgr
 * @author shuichu_ishida
 */
public interface ByPeriodAggregationService {

	/**
	 * 任意期間集計Mgrクラス
	 * @param companyId 会社ID
	 * @param excuteId 実行ID
	 * @param async 同期コマンドコンテキスト
	 */
	<C> void manager(String companyId, String executeId, AsyncCommandHandlerContext<C> async);
	
	/**
	 * 社員の任意期間別実績を集計する
	 * @param async 同期コマンドコンテキスト
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 集計期間
	 * @param executeId 実行ID
	 * @param optionalPeriod 任意集計期間
	 * @param companySets 月別集計で必要な会社別設定
	 * @return 終了状態
	 */
	ProcessState aggregate(AsyncCommandHandlerContext async,
			String companyId, String employeeId, DatePeriod period, String executeId,
			OptionalAggrPeriod optionalPeriod, MonAggrCompanySettings companySets);
}
