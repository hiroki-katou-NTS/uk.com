package nts.uk.ctx.at.record.dom.monthly.affiliation;

import java.util.List;
import java.util.Optional;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * リポジトリ：月別実績の所属情報
 * @author shuichu_ishida
 */
public interface AffiliationInfoOfMonthlyRepository {

	/**
	 * 検索
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @return 該当する月別実績の所属情報
	 */
	Optional<AffiliationInfoOfMonthly> find(String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate);

	/**
	 * 検索　（社員IDと年月）
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @return 該当する月別実績の所属情報
	 */
	List<AffiliationInfoOfMonthly> findBySidAndYearMonth(String employeeId, YearMonth yearMonth);

	/**
	 * 検索　（社員IDリスト）
	 * @param employeeIds 社員IDリスト
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @return 該当する月別実績の所属情報
	 */
	List<AffiliationInfoOfMonthly> findByEmployees(List<String> employeeIds, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate);

	/**
	 * 検索　（社員IDリストと年月リスト）
	 * @param employeeIds 社員IDリスト
	 * @param yearMonths 年月リスト
	 * @return 該当する月別実績の所属情報
	 */
	List<AffiliationInfoOfMonthly> findBySidsAndYearMonths(List<String> employeeIds, List<YearMonth> yearMonths);

	/**
	 * 登録および更新
	 * @param attendanceTimeOfMonthly 月別実績の所属情報
	 */
	void persistAndUpdate(AffiliationInfoOfMonthly attendanceTimeOfMonthly);
	
	/**
	 * 削除
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 */
	void remove(String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate);
	
	/**
	 * 削除　（社員IDと年月）
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 */
	void removeBySidAndYearMonth(String employeeId, YearMonth yearMonth);
}
