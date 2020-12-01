package nts.uk.ctx.at.shared.dom.adapter.temporaryabsence;

import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;

public interface SharedTempAbsenceAdapter {
    /**
     * 社員（List）と期間から休職休業を取得する
     *
     * @param cid         会社ID
     * @param period      期間
     * @param employeeIds List<社員ID>
     * @return List<休職休業履歴, 休職休業履歴項目>
     */
    List<TempAbsenceHistoryImport> getTempAbsenceHistories(String cid, DatePeriod period, List<String> employeeIds);
}
