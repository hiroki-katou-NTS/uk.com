package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.service;

import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.AlarmListExtractionInfoWorkplaceDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.ExtractionMonthlyCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.ExtractionMonthlyConRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.FixedExtractionMonthlyCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.FixedExtractionMonthlyConRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.service.beforemonthlyaggregate.BeforeMonthlyAggregateService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.service.beforemonthlyaggregate.MonthlyCheckDataDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.service.fixedextractcond.FixedExtractCondCheckService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.月次のアラームチェック.アルゴリズム.月次の集計処理
 *
 * @author Le Huu Dat
 */
@Stateless
public class AggregateProcessMasterCheckMonthlyService {

    @Inject
    private FixedExtractionMonthlyConRepository fixedExtractionMonthlyConRepo;
    @Inject
    private ExtractionMonthlyConRepository extractionMonthlyConRepo;
    @Inject
    private BeforeMonthlyAggregateService beforeMonthlyAggregateService;
    @Inject
    private FixedExtractCondCheckService fixedExtractCondCheckService;

    /**
     * 月次の集計処理
     *
     * @param cid                 会社ID
     * @param ym                  年月
     * @param fixedExtractCondIds List＜固定抽出条件ID＞
     * @param extractCondIds      List＜任意抽出条件ID＞
     * @param workplaceIds        List<職場ID＞
     * @return List＜アラームリスト抽出情報（職場）＞
     */
    public List<AlarmListExtractionInfoWorkplaceDto> process(String cid, YearMonth ym, List<String> fixedExtractCondIds,
                                                             List<String> extractCondIds, List<String> workplaceIds) {
        // ドメインモデル「アラームリスト（職場）月次の固定抽出条件」を取得
        List<FixedExtractionMonthlyCon> fixExtractMonthlyCons = fixedExtractionMonthlyConRepo.getBy(fixedExtractCondIds, true);

        // ドメインモデル「アラームリスト（職場）月次の抽出条件」を取得
        List<ExtractionMonthlyCon> extractMonthlyCons = extractionMonthlyConRepo.getBy(extractCondIds, true);

        // 月次の集計する前のデータを準備
        MonthlyCheckDataDto data = beforeMonthlyAggregateService.prepareData(workplaceIds, ym);

        List<AlarmListExtractionInfoWorkplaceDto> alarmListResults = new ArrayList<>();

        // 固定抽出条件をチェック
        alarmListResults.addAll(fixedExtractCondCheckService.check(cid, data.getEmpInfosByWpMap(), fixExtractMonthlyCons, ym, data.getClosures()));

        // 任意抽出条件をチェック

        // リスト「アラーム抽出結果（職場別）」を返す。
        return alarmListResults;
    }
}
