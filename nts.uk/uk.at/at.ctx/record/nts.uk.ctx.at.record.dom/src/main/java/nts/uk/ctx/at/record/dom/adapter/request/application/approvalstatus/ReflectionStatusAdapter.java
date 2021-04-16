package nts.uk.ctx.at.record.dom.adapter.request.application.approvalstatus;

import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;
import java.util.Map;

public interface ReflectionStatusAdapter {

    /**
     * [No.690]社員（List）,期間に一致する申請を取得する
     * @param employeeIDS
     * @param period
     * @return
     */
    public Map<String, List<ApplicationDateImport>> getAppByEmployeeDate(List<String> employeeIDS, DatePeriod period);
}
