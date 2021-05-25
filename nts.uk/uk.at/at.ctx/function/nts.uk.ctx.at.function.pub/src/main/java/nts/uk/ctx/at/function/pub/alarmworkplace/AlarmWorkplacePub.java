package nts.uk.ctx.at.function.pub.alarmworkplace;

import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;
import java.util.Map;

public interface AlarmWorkplacePub {
    /**
     * 事前取得処理
     *
     * @param workplaceIds List＜職場ID＞
     * @param period       期間
     * @return Map＜職場ID、List＜社員情報＞＞
     */
    Map<String, List<EmployeeInfoExport>> advanceAcquisitionProcess(List<String> workplaceIds, DatePeriod period);
}
