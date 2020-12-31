package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.service.check;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.function.alarmworkplace.EmployeeInfoImport;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.CheckItemAppapv;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.FixedExtractionAppapvCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.FixedExtractionAppapvItems;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.FixedExtractionAppapvItemsRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.service.check.reflectionstatus.ReflectionStatusCheckService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.service.check.unapprove.UpapproveCheckService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.AlarmListExtractionInfoWorkplaceDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.MessageDisplay;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.申請承認.アルゴリズム.申請承認の集計処理.固定抽出条件をチェック
 *
 * @author Le Huu Dat
 */
@Stateless
public class FixedExtractionCondCheckService {

    @Inject
    private FixedExtractionAppapvItemsRepository fixedExtractionAppapvItemsRepo;
    @Inject
    private UpapproveCheckService upapproveCheckService;
    @Inject
    private ReflectionStatusCheckService reflectionStatusCheckService;

    /**
     * 固定抽出条件をチェック
     *
     * @param empsByWpMap          Map＜職場ID、List＜社員情報＞＞
     * @param fixExtractAppapvCons List＜アラームリスト（職場）申請承認の固定抽出条件＞
     * @param period               期間
     * @return List＜アラーム抽出結果（職場別）＞
     */
    public List<AlarmListExtractionInfoWorkplaceDto> check(Map<String, List<EmployeeInfoImport>> empsByWpMap,
                                                           List<FixedExtractionAppapvCon> fixExtractAppapvCons,
                                                           DatePeriod period) {
        List<AlarmListExtractionInfoWorkplaceDto> alarmListResults = new ArrayList<>();
        List<CheckItemAppapv> nos = fixExtractAppapvCons.stream().map(FixedExtractionAppapvCon::getCheckItemAppapv)
                .collect(Collectors.toList());
        // ドメインモデル「アラームリスト（職場）申請承認の固定抽出項目」を取得
        List<FixedExtractionAppapvItems> items = fixedExtractionAppapvItemsRepo.getBy(nos);
        // Input．List＜アラームリスト（職場）申請承認の固定抽出条件＞をループする
        for (FixedExtractionAppapvCon condition : fixExtractAppapvCons) {
            Optional<FixedExtractionAppapvItems> itemOpt = items.stream()
                    .filter(x -> x.getCheckItemAppapv().equals(condition.getCheckItemAppapv())).findFirst();
            if (!itemOpt.isPresent()) continue;
            FixedExtractionAppapvItems item = itemOpt.get();
            List<ExtractResultDto> extractResults = new ArrayList<>();

            for (Map.Entry<String, List<EmployeeInfoImport>> empsByWp : empsByWpMap.entrySet()) {
                ExtractResultDto extractResult = null;
                switch (item.getCheckItemAppapv()) {
                    case UNAPPROVED_1:
                        // 未承認１
                        // 承認フェーズ番号　＝　１
                        extractResult = upapproveCheckService.check(empsByWp.getValue(), period, 1);
                        break;
                    case UNAPPROVED_2:
                        // 未承認２
                        // 承認フェーズ番号　＝　２
                        extractResult = upapproveCheckService.check(empsByWp.getValue(), period, 2);
                        break;
                    case UNAPPROVED_3:
                        // 未承認３
                        // 承認フェーズ番号　＝　３
                        extractResult = upapproveCheckService.check(empsByWp.getValue(), period, 3);
                        break;
                    case UNAPPROVED_4:
                        // 未承認４
                        // 承認フェーズ番号　＝　４
                        extractResult = upapproveCheckService.check(empsByWp.getValue(), period, 4);
                        break;
                    case UNAPPROVED_5:
                        // 未承認５
                        // 承認フェーズ番号　＝　５
                        extractResult = upapproveCheckService.check(empsByWp.getValue(), period, 5);
                        break;
                    case UNAPPROVED:
                        // 未承認（反映条件未達）
                        // 反映状態　＝　未反映
                        extractResult = reflectionStatusCheckService.check(empsByWp.getValue(), period, 0);
                        break;
                    case DENIAL:
                        // 否認
                        // 反映状態　＝　否認
                        extractResult = reflectionStatusCheckService.check(empsByWp.getValue(), period, 5);
                        break;
                    case NOT_REFLECTED:
                        // 未反映（反映条件達成）
                        // 反映状態　＝　反映待ち
                        extractResult = reflectionStatusCheckService.check(empsByWp.getValue(), period, 1);
                        break;
                    case PROXY_APPROVAL:
                        break;
                }

                // 抽出結果の値を補足する
                if (extractResult == null) continue;
                extractResult.setAlarmItemName(item.getAppapvCheckName());
                extractResult.setComment(Optional.of(new MessageDisplay(condition.getMessageDisp().v())));
                extractResult.setWorkplaceId(empsByWp.getKey());
                extractResults.add(extractResult);
            }

            // アラームリスト抽出情報（職場）を作成
            List<AlarmListExtractionInfoWorkplaceDto> results = extractResults.stream().map(x ->
                    new AlarmListExtractionInfoWorkplaceDto(condition.getErrorAlarmWorkplaceId(), 5, x))
                    .collect(Collectors.toList());
            alarmListResults.addAll(results);
        }

        return alarmListResults;
    }

}
