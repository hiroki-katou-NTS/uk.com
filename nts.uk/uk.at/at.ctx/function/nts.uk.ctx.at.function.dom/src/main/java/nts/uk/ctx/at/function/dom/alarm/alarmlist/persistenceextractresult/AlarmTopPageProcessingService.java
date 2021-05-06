package nts.uk.ctx.at.function.dom.alarm.alarmlist.persistenceextractresult;

import nts.uk.ctx.at.function.dom.alarm.alarmlist.PeriodByAlarmCategory;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.AlarmExtractionCondition;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.PersistenceAlarmListExtractResult;

import java.util.List;

public interface AlarmTopPageProcessingService {
    void persisTopPageProcessing(String runCode, String pattentCd, List<String> lstSid,
                                        List<PeriodByAlarmCategory> lstCategoryPeriod,
                                        PersistenceAlarmListExtractResult alarmResult,
                                        List<AlarmExtractionCondition> alarmExtractConds,
                                        boolean isDisplayByAdmin, boolean isDisplayByPerson);
}
