package nts.uk.ctx.at.function.ac.workrecord.erroralarm.alarmlistworkplace;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.workrecord.erroralarm.alarmlistworkplace.AggregateProcessAdapter;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.WorkplaceCategory;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractresult.AlarmListExtractionInfoWorkplace;
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
    public List<AlarmListExtractionInfoWorkplace> processMasterCheckBasic(String cid, DatePeriod period, List<String> workplaceErrorCheckIds, List<String> workplaceIds) {
        return convert(aggregateProcessPub.processMasterCheckBasic(cid, period, workplaceErrorCheckIds, workplaceIds));
    }

    private List<AlarmListExtractionInfoWorkplace> convert(List<AlarmListExtractionInfoWorkplaceExport> data) {
        return data.stream().map(x ->
                new AlarmListExtractionInfoWorkplace(
                        x.getCheckConditionId(),
                        EnumAdaptor.valueOf(x.getWorkplaceCategory(), WorkplaceCategory.class),
                        x.getExtractResults().stream().map(y ->
                                new ExtractResult(
                                        y.getAlarmValueMessage(),
                                        y.getStartDate(),
                                        y.getEndDate(),
                                        y.getAlarmItemName(),
                                        y.getCheckTargetValue(),
                                        y.getComment(),
                                        y.getWorkplaceId()))
                                .collect(Collectors.toList())
                )).collect(Collectors.toList());
    }
}
