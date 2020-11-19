package nts.uk.ctx.at.function.ac.workrecord.erroralarm.alarmlistworkplace;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.workplace.WorkPlaceInforExport;
import nts.uk.ctx.at.function.dom.adapter.workrecord.erroralarm.alarmlistworkplace.AggregateProcessAdapter;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.WorkplaceCategory;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractresult.AlarmListExtractInfoWorkplace;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractresult.ExtractResult;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.alarmlistworkplace.AggregateProcessPub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.alarmlistworkplace.AlWorkPlaceInforExport;
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
    public List<AlarmListExtractInfoWorkplace> processMasterCheckBasic(String cid, DatePeriod period,
                                                                       List<String> alarmCheckWkpId,
                                                                       List<String> workplaceIds,
                                                                       List<WorkPlaceInforExport> workPlaceInfos) {
        return convert(aggregateProcessPub.processMasterCheckBasic(cid, period, alarmCheckWkpId, workplaceIds, convertworkPlaceInfo(workPlaceInfos)));
    }

    @Override
    public List<AlarmListExtractInfoWorkplace> processMasterCheckDaily(String cid, DatePeriod period,
                                                                       List<String> alarmCheckWkpId,
                                                                       List<String> workplaceIds,
                                                                       List<WorkPlaceInforExport> workPlaceInfos) {
        return convert(aggregateProcessPub.processMasterCheckDaily(cid, period, alarmCheckWkpId, workplaceIds, convertworkPlaceInfo(workPlaceInfos)));
    }

    private List<AlarmListExtractInfoWorkplace> convert(List<AlarmListExtractionInfoWorkplaceExport> data) {
        return data.stream().map(x ->
                new AlarmListExtractInfoWorkplace(
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

    private List<AlWorkPlaceInforExport> convertworkPlaceInfo(List<WorkPlaceInforExport> data) {
        return data.stream().map(x ->
                new AlWorkPlaceInforExport(
                        x.getWorkplaceId(),
                        x.getHierarchyCode(),
                        x.getWorkplaceCode(),
                        x.getWorkplaceName(),
                        x.getWorkplaceDisplayName(),
                        x.getWorkplaceGenericName(),
                        x.getWorkplaceExternalCode()))
                .collect(Collectors.toList());
    }
}
