package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.service.arbitraryextractcond;

import nts.arc.time.YearMonth;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.adapter.workplace.EmployeeInfoImported;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.AlarmListExtractionInfoWorkplaceDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.ExtractionMonthlyCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.FixedExtractionMonthlyCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.FixedExtractionMonthlyItems;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.FixedExtractionMonthlyItemsRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.enums.FixedCheckMonthlyItemName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.service.fixedextractcond.monthlyundecided.MonthlyUndecidedCheckService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureResultDto;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.月次のアラームチェック.アルゴリズム.月次の集計処理.任意抽出条件をチェック
 *
 * @author Le Huu Dat
 */
@Stateless
public class ArbitaryExtractCondCheckService {

    @Inject
    private FixedExtractionMonthlyItemsRepository fxedExtractionMonthlyItemsRepo;
    @Inject
    private MonthlyUndecidedCheckService monthlyUndecidedCheckService;

    /**
     * 任意抽出条件をチェック
     *
     * @param cid                       会社ID
     * @param empInfosByWpMap           Map＜職場ID、List＜社員情報＞＞
     * @param extractMonthlyCons        List＜アラームリスト（職場）月次の抽出条件＞
     * @param ym                        年月
     * @param attendanceTimeOfMonthlies List＜月別実績の勤怠時間＞
     * @return List＜アラーム抽出結果（職場別）＞
     */
    public List<AlarmListExtractionInfoWorkplaceDto> check(String cid, Map<String, List<EmployeeInfoImported>> empInfosByWpMap,
                                                           List<ExtractionMonthlyCon> extractMonthlyCons,
                                                           YearMonth ym,
                                                           List<AttendanceTimeOfMonthly> attendanceTimeOfMonthlies) {
        List<AlarmListExtractionInfoWorkplaceDto> alarmListResults = new ArrayList<>();
        for (ExtractionMonthlyCon cond : extractMonthlyCons) {
            List<ExtractResultDto> extractResults = new ArrayList<>();

            // Input．Map＜職場ID、List＜社員情報＞＞をループする
            for (Map.Entry<String, List<EmployeeInfoImported>> empInfosByWp : empInfosByWpMap.entrySet()) {
                List<AttendanceTimeOfMonthly> times = attendanceTimeOfMonthlies.stream().filter(x -> empInfosByWp.getValue().stream()
                        .anyMatch(c -> c.getSid().equals(x.getEmployeeId()))).collect(Collectors.toList());

                switch (cond.getCheckMonthlyItemsType()) {
                    case AVERAGE_TIME:
                        break;
                    case AVERAGE_NUMBER_DAY:
                        break;
                    case AVERAGE_NUMBER_TIME:
                        break;
                    case AVERAGE_RATIO:
                        break;
                    default:
                        break;
                }
            }

            // アラームリスト抽出情報（職場）を作成
            alarmListResults.add(new AlarmListExtractionInfoWorkplaceDto(cond.getErrorAlarmWorkplaceId(), 4, extractResults));
        }

        // List＜アラームリスト抽出情報（職場）＞を返す
        return alarmListResults;
    }
}
