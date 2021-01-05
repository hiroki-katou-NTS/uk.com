package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.service.fixedextractcond;

import nts.arc.time.YearMonth;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.adapter.workplace.EmployeeInfoImported;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.AlarmListExtractionInfoWorkplaceDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.FixedExtractionMonthlyCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.FixedExtractionMonthlyItems;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.FixedExtractionMonthlyItemsRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.enums.FixedCheckMonthlyItemName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.service.fixedextractcond.monthlyundecided.MonthlyUndecidedCheckService;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcess;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.MessageDisplay;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureInfo;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.月次のアラームチェック.アルゴリズム.月次の集計処理.固定抽出条件をチェック
 *
 * @author Le Huu Dat
 */
@Stateless
public class FixedExtractCondCheckService {

    @Inject
    private FixedExtractionMonthlyItemsRepository fxedExtractionMonthlyItemsRepo;
    @Inject
    private MonthlyUndecidedCheckService monthlyUndecidedCheckService;

    /**
     * 固定抽出条件をチェック
     *
     * @param cid                   会社ID
     * @param empInfosByWpMap       Map＜職場ID、List＜社員情報＞＞
     * @param fixExtractMonthlyCons List＜アラームリスト（職場）月次の固定抽出条件＞
     * @param ym                    年月
     * @param closures              List＜締め＞
     * @param approvalProcess       承認処理の利用設定
     * @return List＜アラーム抽出結果（職場別）＞
     */
    public List<AlarmListExtractionInfoWorkplaceDto> check(String cid, Map<String, List<EmployeeInfoImported>> empInfosByWpMap,
                                                           List<FixedExtractionMonthlyCon> fixExtractMonthlyCons,
                                                           YearMonth ym, List<ClosureInfo> closures,
                                                           Optional<ApprovalProcess> approvalProcess) {
        List<AlarmListExtractionInfoWorkplaceDto> alarmListResults = new ArrayList<>();
        List<FixedCheckMonthlyItemName> nos = fixExtractMonthlyCons.stream().map(FixedExtractionMonthlyCon::getNo)
                .collect(Collectors.toList());
        // ドメインモデル「アラームリスト（職場）月次の固定抽出項目」を取得
        List<FixedExtractionMonthlyItems> items = fxedExtractionMonthlyItemsRepo.getBy(nos);

        // Input．List＜アラームリスト（職場）月次の固定抽出条件＞をループする
        for (FixedExtractionMonthlyCon fixCond : fixExtractMonthlyCons) {
            List<ExtractResultDto> extractResults = new ArrayList<>();
            Optional<FixedExtractionMonthlyItems> itemOpt = items.stream().filter(x -> x.getNo().equals(fixCond.getNo())).findFirst();
            if (!itemOpt.isPresent()) continue;
            FixedExtractionMonthlyItems item = itemOpt.get();

            // Noをチェック
            switch (item.getNo()) {
                case MONTHLY_UNDECIDED:
                    // 月次データ未確定をチェックする
                    extractResults = monthlyUndecidedCheckService.check(cid, empInfosByWpMap, closures, ym, approvalProcess);
                    break;
            }

            // 取得した「List＜アラーム抽出結果（職場別）＞」をチェック
            if (CollectionUtil.isEmpty(extractResults)) continue;

            extractResults.forEach(x -> {
                x.setAlarmItemName(item.getMonthlyCheckName());
                x.setComment(Optional.of(new MessageDisplay(fixCond.getMessageDisp().v())));
            });

            // 「アラームリスト抽出情報（職場）」の値をセット
            List<AlarmListExtractionInfoWorkplaceDto> results = extractResults.stream().map(x ->
                    new AlarmListExtractionInfoWorkplaceDto(fixCond.getErrorAlarmWorkplaceId(), 4, x))
                    .collect(Collectors.toList());
            alarmListResults.addAll(results);
        }

        // List＜アラームリスト抽出情報（職場）＞を返す
        return alarmListResults;
    }
}
