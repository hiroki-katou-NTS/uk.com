package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.service;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.workplace.EmployeeInfoImported;
import nts.uk.ctx.at.record.dom.adapter.workschedule.WorkScheduleWorkInforAdapter;
import nts.uk.ctx.at.record.dom.adapter.workschedule.WorkScheduleWorkInforImport;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.AlarmListExtractionInfoWorkplaceDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.*;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.service.EmployeeInfoByWorkplaceService;
import nts.uk.ctx.at.shared.dom.adapter.attendanceitemname.AttendanceItemNameAdapter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.スケジュール／日次.アルゴリズム.スケジュール／日次の集計処理
 *
 * @author Le Huu Dat
 */
@Stateless
public class AggregateProcessScheduleService {

    @Inject
    private EmployeeInfoByWorkplaceService employeeInfoByWorkplaceService;
    @Inject
    private WorkScheduleWorkInforAdapter workScheduleWorkInforAdapter;
    @Inject
    private AttendanceItemNameAdapter attendanceItemNameAdapter;
    @Inject
    private ExtractionScheduleConRepository extractionScheduleConRepo;
    @Inject
    private FixedExtractionScheduleConRepository fixedExtractionScheduleConRepo;
    @Inject
    private FixedExtractionScheduleItemsRepository fixedExtractionScheduleItemsRepo;
    @Inject
    private FixedExtractCondAlarmValueService fixedExtractCondAlarmValueService;

    /**
     * スケジュール／日次の集計処理
     *
     * @param cid                 会社ID
     * @param period              期間
     * @param fixedExtractCondIds List＜固定抽出条件ID＞
     * @param extractCondIds      List＜任意抽出条件ID＞
     * @param workplaceIds        List<職場ID＞
     * @return List＜アラームリスト抽出情報（職場）＞
     */
    public List<AlarmListExtractionInfoWorkplaceDto> process(String cid, DatePeriod period, List<String> fixedExtractCondIds,
                                                             List<String> extractCondIds, List<String> workplaceIds) {
        // 職場ID一覧から社員情報を取得する。
        Map<String, List<EmployeeInfoImported>> empInfosByWpMap = employeeInfoByWorkplaceService.get(workplaceIds, period);
        List<AlarmListExtractionInfoWorkplaceDto> alarmListResults = new ArrayList<>();
        List<String> employeeIds = empInfosByWpMap.entrySet().stream()
                .flatMap(x -> x.getValue().stream().map(EmployeeInfoImported::getSid))
                .distinct().collect(Collectors.toList());

        // 全て職場の社員をチェック
        if (empInfosByWpMap.size() == 0) return alarmListResults;

        // スケジュールの勤怠項目を取得する
        attendanceItemNameAdapter.getAttendanceItemNameAsMapName(0); // TODO Q&A 38756

        // 日次の勤怠項目を取得する
        attendanceItemNameAdapter.getAttendanceItemNameAsMapName(cid, 1); // TODO Q&A 38756

        // 社員ID(List)、期間を設定して勤務予定を取得する
        List<WorkScheduleWorkInforImport> workScheduleWorkInfos = workScheduleWorkInforAdapter.getBy(employeeIds, period);

        // ドメインモデル「アラームリスト（職場別）スケジュール／日次の抽出条件」を取得する
        List<ExtractionScheduleCon> extractScheduleCons = extractionScheduleConRepo.getBy(extractCondIds, true);

        // ドメインモデル「アラームリスト（職場別）スケジュール／日次の固定抽出条件」を取得する
        List<FixedExtractionScheduleCon> fixedExtractScheduleCons = fixedExtractionScheduleConRepo.getBy(fixedExtractCondIds, true);

        // ドメインモデル「アラームリスト（職場別）スケジュール／日次の固定抽出項目」を取得
        List<FixedCheckDayItemName> nos = fixedExtractScheduleCons.stream()
                .map(FixedExtractionScheduleCon::getFixedCheckDayItemName).collect(Collectors.toList());
        List<FixedExtractionScheduleItems> fixedExtractScheduleItems = fixedExtractionScheduleItemsRepo.getBy(nos);

        // チェック条件の種類ごとにチェックを行う
        alarmListResults = fixedExtractCondAlarmValueService.create(empInfosByWpMap, workScheduleWorkInfos, period,
                fixedExtractScheduleCons, fixedExtractScheduleItems);

        // List＜アラーム抽出結果（職場別）＞を返す
        return alarmListResults;
    }
}
