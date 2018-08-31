package nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata;

import java.util.List;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

public interface AbsenceLeaveRemainDataRepository {
	
	/**
	 * 振休月別残数データ
	 * @param employeeId 社員ID
	 * @param ym 年月
	 * @param status 締め処理状態
	 * @return 振休月別残数データリスト
	 */
	List<AbsenceLeaveRemainData> getDataBySidYmClosureStatus(String employeeId, YearMonth ym, ClosureStatus status);

	/**
	 * 検索　（年月）
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @return 振休月別残数データリスト　（開始年月日順）
	 */
	List<AbsenceLeaveRemainData> findByYearMonthOrderByStartYmd(String employeeId, YearMonth yearMonth);
	
	/**
	 * 振休月別残数データ 　を追加および更新
	 * @param domain 振休月別残数データ
	 */
	void persistAndUpdate(AbsenceLeaveRemainData domain);
	
	/**
	 * 削除
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 */
	void remove(String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate);
}
