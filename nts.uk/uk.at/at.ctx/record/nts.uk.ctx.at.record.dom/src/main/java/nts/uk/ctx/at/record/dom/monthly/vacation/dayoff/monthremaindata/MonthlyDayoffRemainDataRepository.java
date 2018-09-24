package nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata;

import java.util.List;
import java.util.Optional;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

public interface MonthlyDayoffRemainDataRepository {
	
	/**
	 * ドメインモデル「代休月別残数データ」を取得
	 * @param employeeId社員ID=パラメータ「社員ID」
	 * @param ym 年月=処理中の年月
	 * @param status 締め処理状態
	 * @return 代休月別残数データリスト
	 */
	List<MonthlyDayoffRemainData> getDayOffDataBySidYmStatus(String employeeId, YearMonth ym, ClosureStatus status);
	
	/**
	 * 検索
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @return 代休月別残数データリスト
	 */
	Optional<MonthlyDayoffRemainData> find(String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate);
	
	/**
	 * 検索　（年月）
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @return 代休月別残数データリスト
	 */
	List<MonthlyDayoffRemainData> findByYearMonthOrderByStartYmd(String employeeId, YearMonth yearMonth);

	/**
	 * 検索　（社員IDリストと年月リスト）
	 * @param employeeIds 社員IDリスト
	 * @param yearMonths 年月リスト
	 * @return 代休月別残数データ
	 */
	List<MonthlyDayoffRemainData> findBySidsAndYearMonths(List<String> employeeIds, List<YearMonth> yearMonths);
	
	/**
	 * 代休月別残数データ 　を追加および削除
	 * @param domain 代休月別残数データ
	 */
	void persistAndUpdate(MonthlyDayoffRemainData domain);
	
	/**
	 * 削除
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 */
	void remove(String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate);
}
