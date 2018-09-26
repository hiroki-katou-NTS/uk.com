package nts.uk.ctx.at.record.dom.monthly.vacation.annualleave;

import java.util.List;
import java.util.Optional;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * リポジトリ：年休月別残数データ
 * @author shuichu_ishida
 */
public interface AnnLeaRemNumEachMonthRepository {

	/**
	 * 検索
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @return 該当する年休月別残数データ
	 */
	Optional<AnnLeaRemNumEachMonth> find(String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate);

	/**
	 * 検索　（年月）
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @return 年休月別残数データ　（開始年月日順）
	 */
	List<AnnLeaRemNumEachMonth> findByYearMonthOrderByStartYmd(String employeeId, YearMonth yearMonth);

	/**
	 * 検索　（年月と締めID）
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @return 年休月別残数データ　（開始年月日日順）
	 */
	List<AnnLeaRemNumEachMonth> findByYMAndClosureIdOrderByStartYmd(
			String employeeId, YearMonth yearMonth, ClosureId closureId);

	/**
	 * 検索　（社員IDリスト）
	 * @param employeeIds 社員IDリスト
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @return 該当する年休月別残数データ
	 */
	List<AnnLeaRemNumEachMonth> findbyEmployees(List<String> employeeIds, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate);

	/**
	 * 検索　（社員IDリストと年月リスト）
	 * @param employeeIds 社員IDリスト
	 * @param yearMonths 年月リスト
	 * @return 年休月別残数データ　（開始年月日順）
	 */
	List<AnnLeaRemNumEachMonth> findBySidsAndYearMonths(List<String> employeeIds, List<YearMonth> yearMonths);

	/**
	 * 検索　（社員IDと締め期間、条件＝締め済み）
	 * @param employeeId 社員ID
	 * @param closurePeriod 締め期間
	 * @return 年休月別残数データ　（締め開始日順）
	 */
	List<AnnLeaRemNumEachMonth> findByClosurePeriod(String employeeId, DatePeriod closurePeriod);
	
	/**
	 * 登録および更新
	 * @param domain 年休月別残数データ
	 */
	void persistAndUpdate(AnnLeaRemNumEachMonth domain);
	
	/**
	 * 削除
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 */
	void remove(String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate);
	
	/**
	 * 削除　（年月）
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 */
	void removeByYearMonth(String employeeId, YearMonth yearMonth);
}
