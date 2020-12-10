package nts.uk.ctx.at.record.pubimp.workrecord.erroralarm.alarmlistworkplace;

import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.service.AggregateProcessAppApprovalService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.service.AggregateProcessMasterCheckBasicService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.service.AggregateProcessMasterCheckDailyService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.AlarmListExtractionInfoWorkplaceDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.service.AggregateProcessMonthlyService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.service.AggregateProcessScheduleService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.workplace.service.AggregateProcessMasterCheckWorkplaceService;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.alarmlistworkplace.AggregateProcessPub;
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
    @Inject
    private AggregateProcessMasterCheckWorkplaceService aggregateProcessMasterCheckWorkplaceService;
    @Inject
    private AggregateProcessScheduleService aggregateProcessScheduleService;
    @Inject
    private AggregateProcessMonthlyService aggregateProcessMonthlyService;
    @Inject
    private AggregateProcessAppApprovalService aggregateProcessAppApprovalService;

    @Override
    public List<AlarmListExtractionInfoWorkplaceExport> processMasterCheckBasic(String cid, DatePeriod period,
                                                                                List<String> alarmCheckWkpId,
                                                                                List<String> workplaceIds) {
        return convert(aggregateProcessMasterCheckBasicService.process(cid, period, alarmCheckWkpId, workplaceIds));
    }

    @Override
    public List<AlarmListExtractionInfoWorkplaceExport> processMasterCheckDaily(String cid, DatePeriod period,
                                                                                List<String> alarmCheckWkpId,
                                                                                List<String> workplaceIds) {
        return convert(aggregateProcessMasterCheckDailyService.process(cid, period, alarmCheckWkpId, workplaceIds));
    }

    @Override
    public List<AlarmListExtractionInfoWorkplaceExport> processMasterCheckWorkplace(String cid, DatePeriod period,
                                                                                    List<String> alarmCheckWkpId,
                                                                                    List<String> workplaceIds) {
        return convert(aggregateProcessMasterCheckWorkplaceService.process(cid, period, alarmCheckWkpId, workplaceIds));
    }

    @Override
    public List<AlarmListExtractionInfoWorkplaceExport> processSchedule(String cid, DatePeriod period,
                                                                        List<String> fixedExtractCondIds,
                                                                        List<String> extractCondIds,
                                                                        List<String> workplaceIds) {
        return convert(aggregateProcessScheduleService.process(cid, period, fixedExtractCondIds, extractCondIds, workplaceIds));
    }

    @Override
    public List<AlarmListExtractionInfoWorkplaceExport> processMonthly(String cid, YearMonth ym,
                                                                       List<String> fixedExtractCondIds,
                                                                       List<String> extractCondIds,
                                                                       List<String> workplaceIds) {
        return convert(aggregateProcessMonthlyService.process(cid, ym, fixedExtractCondIds, extractCondIds, workplaceIds));

    }

    @Override
    public List<AlarmListExtractionInfoWorkplaceExport> process(DatePeriod period,
                                                                List<String> fixedExtractCondIds,
                                                                List<String> workplaceIds) {
        return convert(aggregateProcessAppApprovalService.process(period, fixedExtractCondIds, workplaceIds));
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
}
