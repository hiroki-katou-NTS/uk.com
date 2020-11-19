package nts.uk.ctx.at.record.pubimp.workrecord.erroralarm.alarmlistworkplace;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.adapter.workplace.AlWorkPlaceInforImport;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.service.AggregateProcessMasterCheckBasicService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.service.AggregateProcessMasterCheckDailyService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.AlarmListExtractionInfoWorkplaceDto;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.alarmlistworkplace.AggregateProcessPub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.alarmlistworkplace.AlWorkPlaceInforExport;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.alarmlistworkplace.AlarmListExtractionInfoWorkplaceExport;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.alarmlistworkplace.ExtractResultExport;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class AggregateProcessPubImpl implements AggregateProcessPub {

    @Inject
    private AggregateProcessMasterCheckBasicService aggregateProcessMasterCheckBasicService;
    @Inject
    private AggregateProcessMasterCheckDailyService aggregateProcessMasterCheckDailyService;

    @Override
    public List<AlarmListExtractionInfoWorkplaceExport> processMasterCheckBasic(String cid, DatePeriod period,
                                                                                List<String> alarmCheckWkpId,
                                                                                List<String> workplaceIds,
                                                                                List<AlWorkPlaceInforExport> workPlaceInfos) {
        return convert(aggregateProcessMasterCheckBasicService.process(cid, period, alarmCheckWkpId, workplaceIds, convertworkPlaceInfo(workPlaceInfos)));
    }

    @Override
    public List<AlarmListExtractionInfoWorkplaceExport> processMasterCheckDaily(String cid, DatePeriod period,
                                                                                List<String> alarmCheckWkpId,
                                                                                List<String> workplaceIds,
                                                                                List<AlWorkPlaceInforExport> workPlaceInfos) {
        return convert(aggregateProcessMasterCheckDailyService.process(cid, period, alarmCheckWkpId, workplaceIds, convertworkPlaceInfo(workPlaceInfos)));
    }

    private List<AlarmListExtractionInfoWorkplaceExport> convert(List<AlarmListExtractionInfoWorkplaceDto> data) {
        return data.stream().map(x ->
                new AlarmListExtractionInfoWorkplaceExport(
                        x.getCheckConditionId(), x.getWorkplaceCategory(),
                        x.getExtractResults().stream().map(y ->
                                new ExtractResultExport(
                                        y.getAlarmValueMessage().v(),
                                        y.getAlarmValueDate().getStartDate(),
                                        y.getAlarmValueDate().getEndDate().isPresent() ? y.getAlarmValueDate().getEndDate().get() : null,
                                        y.getAlarmItemName(),
                                        y.getCheckTargetValue().orElse(null),
                                        y.getComment().isPresent() ? y.getComment().get().v() : null,
                                        y.getWorkplaceId()
                                )).collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    private List<AlWorkPlaceInforImport> convertworkPlaceInfo(List<AlWorkPlaceInforExport> data) {
        return data.stream().map(x ->
                new AlWorkPlaceInforImport(
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
