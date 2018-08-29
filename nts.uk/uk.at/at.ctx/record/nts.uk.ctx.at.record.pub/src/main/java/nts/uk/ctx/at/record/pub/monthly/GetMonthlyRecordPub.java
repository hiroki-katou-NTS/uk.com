package nts.uk.ctx.at.record.pub.monthly;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
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

	/**
	 * 月別実績データを取得する
	 * @param employeeIds 社員IDリスト
	 * @param period 期間（年月）
	 * @param itemIds 勤怠項目IDリスト
	 * @return 月別実績データ値マップ　（社員ID、月別実績データ値リスト）
	 */
	// RequestList436
	Map<String, List<MonthlyRecordValuesExport>> algorithm(
			List<String> employeeIds, YearMonthPeriod period, List<Integer> itemIds);
	
	/**
	 * 月別実績の勤怠時間を取得する
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @return 月別実績の勤怠時間
	 */
	// RequestList396
	Optional<AttendanceTimeOfMonthly> getAttendanceTime(String employeeId, YearMonth yearMonth);

	/**
	 * 月別実績の値を取得する
	 * @param employeeIds 社員IDリスト
	 * @param period 期間（年月）
	 * @param itemIds 勤怠項目IDリスト
	 * @return 月別実績データ値マップ　（社員ID、月別実績データ値リスト）
	 */
	// RequestList495
	Map<String, List<MonthlyRecordValuesExport>> getRecordValues(
			List<String> employeeIds, YearMonthPeriod period, List<Integer> itemIds);
}
