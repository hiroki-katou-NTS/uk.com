package nts.uk.ctx.at.record.dom.monthly.anyitem;

import java.util.List;
import java.util.Optional;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * リポジトリ：月別実績の任意項目
 * @author shuichu_ishida
 */
public interface AnyItemOfMonthlyRepository {

	/**
	 * 検索
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param anyItemId 任意項目ID
	 * @return 該当する月別実績の任意項目
	 */
	Optional<AnyItemOfMonthly> find(String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate, int anyItemId);
	
	/**
	 * 検索
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param anyItemId 任意項目ID一覧
	 * @return 該当する月別実績の任意項目一覧
	 */
	List<AnyItemOfMonthly> find(String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate, List<Integer> anyItemIds);

	/**
	 * 検索　（月度と締め）
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @return 該当する月別実績の任意項目
	 */
	List<AnyItemOfMonthly> findByMonthlyAndClosure(String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate);

	/**
	 * 検索　（月度）
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @return 該当する月別実績の任意項目
	 */
	List<AnyItemOfMonthly> findByMonthly(String employeeId, YearMonth yearMonth);

	/**
	 * 検索　（社員IDリスト）
	 * @param employeeIds 社員IDリスト
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param anyItemId 任意項目ID
	 * @return 該当する月別実績の任意項目
	 */
	List<AnyItemOfMonthly> findByEmployees(List<String> employeeIds, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate, int anyItemId);

	/**
	 * 検索　（社員IDリスト）
	 * @param employeeIds 社員IDリスト
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @return 該当する月別実績の任意項目
	 */
	List<AnyItemOfMonthly> findByEmployees(List<String> employeeIds, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate);

	/**
	 * 検索　（社員IDリストと月度リスト）
	 * @param employeeIds 社員IDリスト
	 * @param yearMonths 年月リスト
	 * @return 該当する月別実績の任意項目
	 */
	List<AnyItemOfMonthly> findBySidsAndMonths(List<String> employeeIds, List<YearMonth> yearMonths);
	
	/**
	 * 登録および更新
	 * @param anyItemOfMonthly 月別実績の任意項目
	 */
	void persistAndUpdate(AnyItemOfMonthly anyItemOfMonthly);
	
	/**
	 * 登録および更新
	 * @param anyItemOfMonthly 月別実績の任意項目
	 */
	void persistAndUpdate(List<AnyItemOfMonthly> anyItemOfMonthly);
	
	/**
	 * 削除
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param anyItemId 任意項目ID
	 */
	void remove(String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate, int anyItemId);
	
	/**
	 * 削除　（月度と締め）
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 */
	void removeByMonthlyAndClosure(String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate);
	
	/**
	 * 削除　（月度）
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 */
	void removeByMonthly(String employeeId, YearMonth yearMonth);
}
