package nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata;

import java.util.List;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

public interface SpecialHolidayRemainDataRepository {
	
	/**
	 * ドメインモデル「特別休暇月別残数データ」を取得
	 * @param sid
	 * @param ym 年月=処理中の年月
	 * @param status 締め処理状態=status
	 * @return
	 */
	List<SpecialHolidayRemainData> getByYmStatus(String sid, YearMonth ym, ClosureStatus status);

	/**
	 * 検索
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @return 特別休暇月別残数データ
	 */
	// add 2018.9.13 shuichi_ishida
	List<SpecialHolidayRemainData> find(String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate);
	
	/**
	 * 検索　（年月）
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @return 特別休暇月別残数データ
	 */
	// add 2018.8.24 shuichi_ishida
	List<SpecialHolidayRemainData> findByYearMonthOrderByStartYmd(String employeeId, YearMonth yearMonth);

	/**
	 * 検索　（社員IDリストと年月リスト）
	 * @param employeeIds 社員IDリスト
	 * @param yearMonths 年月リスト
	 * @return 特別休暇月別残数データ
	 */
	// add 2018.8.30 shuichi_ishida
	List<SpecialHolidayRemainData> findBySidsAndYearMonths(List<String> employeeIds, List<YearMonth> yearMonths);
	
	/**
	 * 登録および更新
	 * @param domain 特別休暇月別残数データ
	 */
	// add 2018.8.22 shuichi_ishida
	void persistAndUpdate(SpecialHolidayRemainData domain);
	
	/**
	 * 削除
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param specialHolidayCode 特別休暇コード
	 */
	// add 2018.8.22 shuichi_ishida
	void remove(String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate,
			int specialHolidayCode);
	
	/**
	 * 削除
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 */
	// add 2018.8.24 shuichi_ishida
	void remove(String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate);
	
	/**
	 * 削除　（年月）
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 */
	// add 2018.8.22 shuichi_ishida
	void removeByYearMonth(String employeeId, YearMonth yearMonth);
}
