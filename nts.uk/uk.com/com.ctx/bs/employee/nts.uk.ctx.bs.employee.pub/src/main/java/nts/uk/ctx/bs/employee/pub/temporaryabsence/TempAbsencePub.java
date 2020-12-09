package nts.uk.ctx.bs.employee.pub.temporaryabsence;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.bs.employee.pub.workplace.affiliate.AffWorkplaceHistoryItemExport;

import java.util.List;

public interface TempAbsencePub {

    /**
     * 社員（List）と期間から休職休業を取得する
     *
     * @param cid         会社ID
     * @param period      期間
     * @param employeeIds List<社員ID>
     * @return List<休職休業履歴, 休職休業履歴項目>
     */
    TempAbsenceExport getTempAbsence(String cid, DatePeriod period, List<String> employeeIds);

}
