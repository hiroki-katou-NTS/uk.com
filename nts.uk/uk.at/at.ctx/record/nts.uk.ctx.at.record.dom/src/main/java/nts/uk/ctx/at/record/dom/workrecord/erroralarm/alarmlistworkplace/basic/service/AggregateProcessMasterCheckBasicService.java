package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.service;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.workplace.EmployeeInfoImported;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.service.clscodecfm.ClsCodeCfmService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.service.est36timecfm.Est36TimeCfmService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.service.esttimeamountcfm.EstTimeAmountCfmService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.service.positioncodecfm.PositionCodeCfmService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.service.publichdcfm.PublicHdCfmService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.service.reftimesetcfm.RefTimeSetCfmService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.service.workplacecodecfm.WorkplaceCodeCfmService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.AlarmListExtractionInfoWorkplaceDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.*;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.service.empcodecfm.EmpCodeCfmService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.service.EmployeeInfoByWorkplaceService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.マスタチェック(基本).アルゴリズム.マスタチェック(基本)の集計処理
 *
 * @author Le Huu Dat
 */
@Stateless
public class AggregateProcessMasterCheckBasicService {

    @Inject
    private EmployeeInfoByWorkplaceService employeeInfoByWorkplaceService;
    @Inject
    private BasicFixedExtractionConditionRepository basicFixedExtractionConditionRepo;
    @Inject
    private BasicFixedExtractionItemRepository basicFixedExtractionItemRepo;
    @Inject
    private EmpCodeCfmService empCodeCfmService;
    @Inject
    private ClsCodeCfmService clsCodeCfmService;
    @Inject
    private PositionCodeCfmService positionCodeCfmService;
    @Inject
    private WorkplaceCodeCfmService workplaceCodeCfmService;
    @Inject
    private RefTimeSetCfmService refTimeSetCfmService;
    @Inject
    private Est36TimeCfmService est36TimeCfmService;
    @Inject
    private PublicHdCfmService publicHdCfmService;
    @Inject
    private EstTimeAmountCfmService estTimeAmountCfmService;

    /**
     * マスタチェック(基本)の集計処理
     *
     * @param cid             会社ID
     * @param period          期間
     * @param alarmCheckWkpId List＜職場のエラームチェックID＞
     * @param workplaceIds    List＜職場ID＞
     * @return List＜アラーム抽出結果＞
     */
    public List<AlarmListExtractionInfoWorkplaceDto> process(String cid, DatePeriod period, List<String> alarmCheckWkpId, List<String> workplaceIds) {
        // 職場の社員の情報を取得する。
        Map<String, List<EmployeeInfoImported>> empInfoMap = employeeInfoByWorkplaceService.get(workplaceIds, period);

        // 空欄のList＜アラームリスト抽出情報（職場）＞を作成する。
        List<AlarmListExtractionInfoWorkplaceDto> alarmListResults = new ArrayList<>();

        // ドメインオブジェクト「アラームリスト（職場）基本の固定抽出条件」を取得する。
        List<BasicFixedExtractionCondition> basicConditions = basicFixedExtractionConditionRepo.getBy(alarmCheckWkpId, true);

        for (BasicFixedExtractionCondition basic : basicConditions) {
            BasicFixedCheckItem no = basic.getNo();

            // ドメインモデル「アラームリスト（職場）基本の固定抽出項目」．基本チェック名称を取得する。
            Optional<BasicFixedExtractionItem> basicItemOpt = basicFixedExtractionItemRepo.getBy(no);
            if (!basicItemOpt.isPresent()) continue;
            BasicFixedExtractionItem basicItem = basicItemOpt.get();

            List<ExtractResultDto> extractResults = new ArrayList<>();
            // ループ中項目の「アラームリスト（職場）基本の固定抽出条件」．Noをチェックする。
            switch (no) {
                case EMPLOYMENT_CODE_CONFIRMATION:
                    // 雇用コードを確認する。
                    extractResults = empCodeCfmService.confirm(cid, basicItem.getName(), basic.getDisplayMessage(),
                            empInfoMap, period);
                    break;
                case CLS_CODE_CONFIRMATION:
                    // 分類コードを確認する。
                    extractResults = clsCodeCfmService.confirm(cid, basicItem.getName(), basic.getDisplayMessage(),
                            empInfoMap, period);
                    break;
                case POSITION_CODE_CONFIRMATION:
                    // 職位コードを確認する。
                    extractResults = positionCodeCfmService.confirm(cid, basicItem.getName(), basic.getDisplayMessage(),
                            empInfoMap, period);
                    break;
                case WORKPLACE_CODE_CONFIRMATION:
                    // 職場コードを確認する。
                    extractResults = workplaceCodeCfmService.confirm(cid, basicItem.getName(), basic.getDisplayMessage(),
                            empInfoMap, period);
                    break;
                case NO_REF_TIME_SET:
                    // 基準時間を確認する。
                    extractResults = refTimeSetCfmService.confirm(cid, basicItem.getName(), basic.getDisplayMessage(), period);
                    break;
                case EST36_TIME_NOT_SET:
                    // ３６協定目安時間確認
                    extractResults = est36TimeCfmService.confirm(cid, basicItem.getName(), basic.getDisplayMessage(), period);
                    break;
                case UNSET_PUBLIC_HD:
                    // 公休日数を確認する。
                    extractResults = publicHdCfmService.confirm(cid, basicItem.getName(), basic.getDisplayMessage(), period);
                    break;
                case EST_TIME_AMOUNT_NOT_SET:
                    // 目安時間・金額を確認する。
                    extractResults = estTimeAmountCfmService.confirm(cid, basicItem.getName(), basic.getDisplayMessage(), period);
                    break;
            }

            // アラームリスト抽出情報（職場）を作成してList＜アラームリスト抽出情報（職場）＞を追加
            AlarmListExtractionInfoWorkplaceDto alarmListResult = new AlarmListExtractionInfoWorkplaceDto(basic.getId(),
                    0, extractResults);
            alarmListResults.add(alarmListResult);
        }

        // List＜アラームリスト抽出情報（職場）＞を返す。
        return alarmListResults;
    }
}
