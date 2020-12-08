package nts.uk.ctx.at.function.dom.adapter.workrecord.erroralarm.alarmlistworkplace;

import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.workplace.WorkPlaceInforExport;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractresult.AlarmListExtractInfoWorkplace;

import java.util.List;

public interface AggregateProcessAdapter {
    List<AlarmListExtractInfoWorkplace> processMasterCheckBasic(String cid, DatePeriod period,
                                                                List<String> alarmCheckWkpId,
                                                                List<String> workplaceIds);

    List<AlarmListExtractInfoWorkplace> processMasterCheckDaily(String cid, DatePeriod period,
                                                                List<String> alarmCheckWkpId,
                                                                List<String> workplaceIds);

    List<AlarmListExtractInfoWorkplace> processMasterCheckWorkplace(String cid, DatePeriod period,
                                                                    List<String> alarmCheckWkpId,
                                                                    List<String> workplaceIds);

    List<AlarmListExtractInfoWorkplace> processSchedule(String cid, DatePeriod period,
                                                        List<String> alarmCheckWkpId,
                                                        List<String> optionalIds,
                                                        List<String> workplaceIds);

    List<AlarmListExtractInfoWorkplace> processMonthly(String cid, YearMonth ym,
                                                       List<String> fixedExtractCondIds,
                                                       List<String> extractCondIds,
                                                       List<String> workplaceIds);
}
