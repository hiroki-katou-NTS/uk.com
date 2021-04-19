package nts.uk.ctx.at.record.dom.adapter.function.alarmworkplace;

import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;
import java.util.Map;

public interface ReAlarmWorkplaceAdapter {

    /**
     * 事前取得処理
     *
     * @param workplaceIds List＜職場ID＞
     * @param period       期間
     * @return Map＜職場ID、List＜社員情報＞＞
     */
    Map<String, List<EmployeeInfoImport>> advanceAcquisitionProcess(List<String> workplaceIds, DatePeriod period);
}
