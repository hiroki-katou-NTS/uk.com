package nts.uk.ctx.at.function.ac.workrecord.erroralarm.alarmlistworkplace;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.function.dom.adapter.workrecord.erroralarm.alarmlistworkplace.AggregateProcessAdapter;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.WorkplaceCategory;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractresult.AlarmListExtractInfoWorkplace;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractresult.ExtractResult;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.alarmlistworkplace.AggregateProcessPub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.alarmlistworkplace.AlarmListExtractionInfoWorkplaceExport;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class AggregateProcessAcAdapter implements AggregateProcessAdapter {

    @Inject
    private AggregateProcessPub aggregateProcessPub;

    @Override
    public List<AlarmListExtractInfoWorkplace> processMasterCheckBasic(String cid, YearMonthPeriod ymPeriod,
                                                                       List<String> alarmCheckWkpId,
                                                                       List<String> workplaceIds) {
        return convert(aggregateProcessPub.processMasterCheckBasic(cid, ymPeriod, alarmCheckWkpId, workplaceIds));
    }

    @Override
    public List<AlarmListExtractInfoWorkplace> processMasterCheckDaily(String cid, DatePeriod period,
                                                                       List<String> alarmCheckWkpId,
                                                                       List<String> workplaceIds) {
        return convert(aggregateProcessPub.processMasterCheckDaily(cid, period, alarmCheckWkpId, workplaceIds));
    }

    @Override
    public List<AlarmListExtractInfoWorkplace> processMasterCheckWorkplace(String cid, YearMonthPeriod ymPeriod,
                                                                           List<String> alarmCheckWkpId,
                                                                           List<String> workplaceIds) {
        return convert(aggregateProcessPub.processMasterCheckWorkplace(cid, ymPeriod, alarmCheckWkpId, workplaceIds));
    }

    @Override
    public List<AlarmListExtractInfoWorkplace> processSchedule(String cid, DatePeriod period,
                                                               List<String> fixedExtractCondIds,
                                                               List<String> extractCondIds,
                                                               List<String> workplaceIds) {
        return convert(aggregateProcessPub.processSchedule(cid, period, fixedExtractCondIds, extractCondIds, workplaceIds));
    }

    @Override
    public List<AlarmListExtractInfoWorkplace> processMonthly(String cid, YearMonth ym,
                                                              List<String> fixedExtractCondIds,
                                                              List<String> extractCondIds,
                                                              List<String> workplaceIds) {
        return convert(aggregateProcessPub.processMonthly(cid, ym, fixedExtractCondIds, extractCondIds, workplaceIds));
    }

    @Override
    public List<AlarmListExtractInfoWorkplace> processAppApproval(DatePeriod period,
                                                                  List<String> fixedExtractCondIds,
                                                                  List<String> workplaceIds) {
        return convert(aggregateProcessPub.processAppApproval(period, fixedExtractCondIds, workplaceIds));
    }

    private List<AlarmListExtractInfoWorkplace> convert(List<AlarmListExtractionInfoWorkplaceExport> data) {
        return data.stream().map(x ->
                new AlarmListExtractInfoWorkplace(
                        x.getCheckConditionId(),
                        EnumAdaptor.valueOf(x.getWorkplaceCategory(), WorkplaceCategory.class),
                        new ExtractResult(
                                x.getExtractResult().getAlarmValueMessage(),
                                x.getExtractResult().getStartDate(),
                                x.getExtractResult().getEndDate(),
                                x.getExtractResult().getAlarmItemName(),
                                x.getExtractResult().getCheckTargetValue(),
                                x.getExtractResult().getComment(),
                                x.getExtractResult().getWorkplaceId())))
                .collect(Collectors.toList());
    }
}
