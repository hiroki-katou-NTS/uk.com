package nts.uk.ctx.at.shared.dom.scherec.totaltimes.algorithm;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimes;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesResult;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 日別実績から回数集計結果を取得する
 * @author shuichu_ishida
 */
public interface GetTotalTimesFromDailyRecord {

	/**
	 * 回数集計結果情報を取得
	 * @param totalTimes 回数集計
	 * @return 回数集計結果情報
	 */
	Optional<TotalTimesResult> getResult(String companyId, String employeeId, DatePeriod period,
			TotalTimes totalTimes);

	/**
	 * 回数集計結果情報を取得
	 * @param totalTimesList 回数集計リスト
	 * @return 回数集計結果情報マップ
	 */
	Map<Integer, TotalTimesResult> getResults(String companyId, String employeeId, DatePeriod period,
			List<TotalTimes> totalTimesList);
}
