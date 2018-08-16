package nts.uk.ctx.at.record.pub.monthly;

import java.util.List;

import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

/**
 * 月別実績データを取得する
 * @author shuichu_ishida
 */
public interface GetMonthlyRecordPub {

	/**
	 * 月別実績データを取得する
	 * @param employeeId 社員ID
	 * @param period 期間（年月）
	 * @param itemIds 勤怠項目IDリスト
	 * @return 月別実績データ値リスト
	 */
	// RequestList436
	List<MonthlyRecordValuesExport> algorithm(String employeeId, YearMonthPeriod period, List<Integer> itemIds);
}
