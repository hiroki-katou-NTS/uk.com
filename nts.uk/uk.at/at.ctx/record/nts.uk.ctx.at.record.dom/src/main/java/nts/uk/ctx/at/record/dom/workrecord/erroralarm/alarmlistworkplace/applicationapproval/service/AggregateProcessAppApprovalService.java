package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.service;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.function.alarmworkplace.EmployeeInfoImport;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.FixedExtractionAppapvCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.FixedExtractionAppapvConRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.service.beforeaggregate.BeforeAggregateAppApproveService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.service.check.FixedExtractionCondCheckService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.AlarmListExtractionInfoWorkplaceDto;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.月次のアラームチェック.アルゴリズム.月次の集計処理
 *
 * @author Le Huu Dat
 */
@Stateless
public class AggregateProcessAppApprovalService {

    @Inject
    private FixedExtractionAppapvConRepository fixedExtractionAppapvConRepository;
    @Inject
    private BeforeAggregateAppApproveService beforeAggregateAppApproveService;
    @Inject
    private FixedExtractionCondCheckService fixedExtractionCondCheckService;

    /**
     * 申請承認の集計処理
     *
     * @param period              期間
     * @param fixedExtractCondIds List＜固定抽出条件ID＞
     * @param workplaceIds        List<職場ID＞
     * @return List＜アラームリスト抽出情報（職場）＞
     */
    public List<AlarmListExtractionInfoWorkplaceDto> process(DatePeriod period,
                                                             List<String> fixedExtractCondIds,
                                                             List<String> workplaceIds) {
        // ドメインモデル「アラームリスト（職場）申請承認の固定抽出条件」を取得
        List<FixedExtractionAppapvCon> fixExtractAppapvCons = fixedExtractionAppapvConRepository.getBy(fixedExtractCondIds, true);

        // 申請承認の集計する前のデータを準備
        Map<String, List<EmployeeInfoImport>> empsByWpMap = beforeAggregateAppApproveService.prepareData(workplaceIds, period);

        // 固定抽出条件をチェック
        // リスト「アラーム抽出結果（職場別）」を返す。
        return fixedExtractionCondCheckService.check(empsByWpMap, fixExtractAppapvCons, period);
    }
}
