package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.workplace.service;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.workplace.EmployeeInfoImported;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.AlarmListExtractionInfoWorkplaceDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.service.EmployeeInfoByWorkplaceService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.workplace.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.マスタチェック（職場）.アルゴリズム.マスタチェック(職場)の集計処理
 *
 * @author Le Huu Dat
 */
@Stateless
public class AggregateProcessMasterCheckWorkplaceService {

    @Inject
    private EmployeeInfoByWorkplaceService employeeInfoByWorkplaceService;
    @Inject
    private AlarmFixedExtractionConditionRepository alarmFixedExtractionConditionRepo;
    @Inject
    private AlarmFixedExtractionItemRepository alarmFixedExtractionWpItemRepo;
    @Inject
    private NoRefTimeCfmService noRefTimeCfmService;
    @Inject
    private TimeNotSetFor36EstCfmService timeNotSetFor36EstCfmService;
    @Inject
    private UnsetHdCfmService unsetHdCfmService;
    @Inject
    private UnRegisterManagerCfmService unRegisterManagerCfmService;

    /**
     * マスタチェック(職場)の集計処理
     *
     * @param cid             会社ID
     * @param period          期間
     * @param alarmCheckWkpId List＜職場のエラームチェックID＞
     * @param workplaceIds    List＜職場ID＞
     * @return List＜アラームリスト抽出情報（職場）＞
     */
    public List<AlarmListExtractionInfoWorkplaceDto> process(String cid, DatePeriod period, List<String> alarmCheckWkpId,
                                                             List<String> workplaceIds) {
        // 職場ID一覧から社員情報を取得する。
        Map<String, List<EmployeeInfoImported>> empInfosByWp = employeeInfoByWorkplaceService.get(workplaceIds, period);

        // 空欄のリスト「アラーム抽出結果（職場別）」を作成する。
        List<AlarmListExtractionInfoWorkplaceDto> alarmListResults = new ArrayList<>();

        // ドメインオブジェクト「アラームリスト（職場）固定抽出条件」を取得する。
        List<AlarmFixedExtractionCondition> fixedExtractWpConds = alarmFixedExtractionConditionRepo.getBy(alarmCheckWkpId, true);

        for (AlarmFixedExtractionCondition wp : fixedExtractWpConds) {
            List<ExtractResultDto> extractResults = new ArrayList<>();

            // ドメインモデル「アラームリスト（職場）固定抽出項目」．職場チェック名称を取得する。
            Optional<AlarmFixedExtractionItem> wpItemOpt = alarmFixedExtractionWpItemRepo.getBy(wp.getNo());
            if (!wpItemOpt.isPresent()) continue;
            AlarmFixedExtractionItem wpItem = wpItemOpt.get();

            // ループ中項目の「アラームリスト（職場）固定抽出条件」．Noをチェックする。
            switch (wp.getNo()) {
                case NO_REF_TIME:
                    // 基準時間の未設定を確認する。
                    extractResults = noRefTimeCfmService.confirm(cid, wpItem.getWorkplaceCheckName(), wpItem.getDisplayMessage(), period, workplaceIds);
                    break;
                case TIME_NOT_SET_FOR_36_ESTIMATED:
                    // 36協定目安時間の未設定を確認する
                    extractResults = timeNotSetFor36EstCfmService.confirm(wpItem.getWorkplaceCheckName(), wpItem.getDisplayMessage(), period, workplaceIds);
                    break;
                case UNSET_OF_HD:
                    // 公休日数の未設定を確認する。
                    extractResults = unsetHdCfmService.confirm(cid, wpItem.getWorkplaceCheckName(), wpItem.getDisplayMessage(), period, workplaceIds);
                    break;
                case ESTIMATED_OR_AMOUNT_TIME_NOT_SET:
                    break;
                case NOT_REGISTERED:
                    // 管理者を確認する。
                    extractResults = unRegisterManagerCfmService.confirm(wpItem.getWorkplaceCheckName(), wpItem.getDisplayMessage(), period, workplaceIds);
                    break;
            }

            // アラームリスト抽出情報（職場）を作成してList＜アラームリスト抽出情報（職場）＞に追加
            AlarmListExtractionInfoWorkplaceDto alarmListResult = new AlarmListExtractionInfoWorkplaceDto(wp.getId(),
                    1, extractResults);
            alarmListResults.add(alarmListResult);
        }

        // リスト「アラーム抽出結果（職場別）」を返す。
        return alarmListResults;
    }
}
