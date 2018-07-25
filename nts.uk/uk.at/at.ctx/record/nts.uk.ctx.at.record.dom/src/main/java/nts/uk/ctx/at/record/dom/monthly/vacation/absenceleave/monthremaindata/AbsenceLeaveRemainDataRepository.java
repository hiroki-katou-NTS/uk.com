package nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata;

import java.util.List;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.ClosureStatus;

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
	 * 振休月別残数データ 　を追加および更新
	 * @param domain 振休月別残数データ
	 */
	void persistAndUpdate(AbsenceLeaveRemainData domain);
}
