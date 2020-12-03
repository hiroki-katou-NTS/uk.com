package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.service.beforemonthlyaggregate;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.workplace.EmployeeInfoImported;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.ExtractionMonthlyCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.FixedExtractionMonthlyCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.service.EmployeeInfoByWorkplaceService;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureResultDto;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.WorkClosureService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.月次のアラームチェック.アルゴリズム.月次の集計処理.月次の集計する前のデータを準備
 *
 * @author Le Huu Dat
 */
@Stateless
public class BeforeMonthlyAggregateService {

    @Inject
    private EmployeeInfoByWorkplaceService employeeInfoByWorkplaceService;
    @Inject
    private WorkClosureService workClosureService;

    /**
     * 月次の集計する前のデータを準備
     *
     * @param workplaceIds          List＜職場ID＞
     * @param period                年月
     * @param fixExtractMonthlyCons List＜アラームリスト（職場）月次の固定抽出条件＞
     * @param extractMonthlyCons    List＜アラームリスト（職場）月次の抽出条件＞
     * @return Map＜職場ID、List＜社員情報＞＞
     * List＜月別実績(Work)＞
     * List＜締め＞
     */
    public MonthlyCheckDataDto prepareData(List<String> workplaceIds, DatePeriod period,
                              List<FixedExtractionMonthlyCon> fixExtractMonthlyCons, List<ExtractionMonthlyCon> extractMonthlyCons) {
        // 職場ID一覧から社員情報を取得する。
        Map<String, List<EmployeeInfoImported>> empInfosByWpMap = employeeInfoByWorkplaceService.get(workplaceIds, period);

        if (empInfosByWpMap.size() > 0) {
            // 社員ID（List）、期間を設定して月別実績を取得する
            // TODO Q&A 36913
        }

        // 会社の締めを取得する
        List<ClosureResultDto> closures = workClosureService.findClosureByReferenceDate(GeneralDate.today());

        return new MonthlyCheckDataDto(empInfosByWpMap, closures);
    }
}
