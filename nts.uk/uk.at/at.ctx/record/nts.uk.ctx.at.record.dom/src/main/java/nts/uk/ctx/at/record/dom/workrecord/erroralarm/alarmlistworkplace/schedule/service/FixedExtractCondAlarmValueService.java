package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.service;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.workplace.EmployeeInfoImported;
import nts.uk.ctx.at.record.dom.adapter.workschedule.WorkScheduleWorkInforImport;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.AlarmListExtractionInfoWorkplaceDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.FixedExtractionScheduleCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.FixedExtractionScheduleItems;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.service.itemchecksystemunique.ScheduleUndecidedService;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueDate;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueMessage;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.MessageDisplay;
import nts.uk.shr.com.i18n.TextResource;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.スケジュール／日次.アルゴリズム.固定抽出条件のアラーム値を作成する
 *
 * @author Le Huu Dat
 */
@Stateless
public class FixedExtractCondAlarmValueService {

    @Inject
    private ScheduleUndecidedService scheduleUndecidedService;

    /**
     * 固定抽出条件のアラーム値を作成する
     *
     * @param empInfosByWpMap           Map＜職場ID、List＜社員情報＞＞
     * @param workScheduleWorkInfos     List＜勤務予定＞
     * @param period                    期間
     * @param fixedExtractScheduleCons  List＜アラームリスト（職場別）スケジュール／日次の固定抽出条件＞
     * @param fixedExtractScheduleItems List＜アラームリスト（職場別）スケジュール／日次の固定抽出項目＞
     * @return List＜アラームリスト抽出情報（職場）＞
     */
    public List<AlarmListExtractionInfoWorkplaceDto> create(Map<String, List<EmployeeInfoImported>> empInfosByWpMap,
                                                            List<WorkScheduleWorkInforImport> workScheduleWorkInfos,
                                                            DatePeriod period,
                                                            List<FixedExtractionScheduleCon> fixedExtractScheduleCons,
                                                            List<FixedExtractionScheduleItems> fixedExtractScheduleItems) {
        List<AlarmListExtractionInfoWorkplaceDto> alarmListResults = new ArrayList<>();
        // Input．List＜アラームリスト（職場別）スケジュール／日次の固定抽出条件＞をループする
        for (FixedExtractionScheduleCon fixedCond : fixedExtractScheduleCons) {
            List<ExtractResultDto> extractResults = new ArrayList<>();
            // Input．Map＜職場ID、List＜社員情報＞＞をループする
            for (Map.Entry<String, List<EmployeeInfoImported>> empInfosByWp : empInfosByWpMap.entrySet()) {
                Optional<FixedExtractionScheduleItems> itemOpt = fixedExtractScheduleItems.stream()
                        .filter(x -> x.getFixedCheckDayItemName().equals(fixedCond.getFixedCheckDayItemName())).findFirst();
                if (!itemOpt.isPresent()) continue;
                FixedExtractionScheduleItems item = itemOpt.get();
                List<String> employeeIds = empInfosByWp.getValue().stream().map(EmployeeInfoImported::getSid)
                        .distinct().collect(Collectors.toList());

                switch (fixedCond.getFixedCheckDayItemName()) {
                    case SCHEDULE_UNDECIDED:
                        // スケジュール未確定
                        int count = scheduleUndecidedService.count(employeeIds, workScheduleWorkInfos);
                        if (count > 0) {
                            // 抽出結果を作成
                            String message = TextResource.localize("KAL020_317", String.valueOf(count));
                            // ドメインオブジェクト「抽出結果」を作成します。
                            ExtractResultDto result = new ExtractResultDto(new AlarmValueMessage(message),
                                    new AlarmValueDate(period.start().toString("yyyyMMdd"),
                                            Optional.of(period.end().toString("yyyyMMdd"))),
                                    item.getScheduleCheckName(),
                                    Optional.ofNullable(TextResource.localize("KAL020_318", String.valueOf(employeeIds.size()))),
                                    Optional.of(new MessageDisplay(fixedCond.getMessageDisp().v())),
                                    empInfosByWp.getKey()
                            );
                            extractResults.add(result);
                        }
                        break;
                }
            }

            // アラームリスト抽出情報（職場）を作成
            List<AlarmListExtractionInfoWorkplaceDto> results = extractResults.stream().map(x ->
                    new AlarmListExtractionInfoWorkplaceDto(fixedCond.getErrorAlarmWorkplaceId(), 3, x))
                    .collect(Collectors.toList());
            alarmListResults.addAll(results);
        }
        return alarmListResults;
    }
}
