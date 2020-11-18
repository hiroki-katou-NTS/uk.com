package nts.uk.ctx.at.function.dom.adapter.workrecord.erroralarm.alarmlistworkplace;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractresult.AlarmListExtractInfoWorkplace;

import java.util.List;

public interface AggregateProcessAdapter {
    List<AlarmListExtractInfoWorkplace> processMasterCheckBasic(String cid, DatePeriod period, List<String> workplaceErrorCheckIds, List<String> workplaceIds);

}
