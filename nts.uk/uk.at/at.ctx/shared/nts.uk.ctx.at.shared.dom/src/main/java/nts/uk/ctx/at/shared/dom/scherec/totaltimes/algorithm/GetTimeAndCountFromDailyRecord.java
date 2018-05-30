package nts.uk.ctx.at.shared.dom.scherec.totaltimes.algorithm;

import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimes;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesResult;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 日別実績から回数集計結果を取得する
 * @author shuichu_ishida
 */
public interface GetTimeAndCountFromDailyRecord {

	/**
	 * データ設定
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 期間
	 */
	GetTimeAndCountFromDailyRecord setData(String companyId, String employeeId, DatePeriod period);
	
	/**
	 * 時間・回数を取得
	 * @param totalTimes 回数集計
	 * @return 回数集計結果情報
	 */
	TotalTimesResult getResult(TotalTimes totalTimes);
}
