package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.service;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.workplace.EmployeeInfoImported;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.*;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.service.clscodecfm.ClsCodeCfmService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.service.empcodecfm.EmpCodeCfmService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.service.est36timecfm.Est36TimeCfmService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.service.esttimeamountcfm.EstTimeAmountCfmService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.service.positioncodecfm.PositionCodeCfmService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.service.publichdcfm.PublicHdCfmService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.service.reftimesetcfm.RefTimeSetCfmService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.service.workplacecodecfm.WorkplaceCodeCfmService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.*;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.service.beforecheck.DailyBeforeCheckService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.AlarmListExtractionInfoWorkplaceDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.service.EmployeeInfoByWorkplaceService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.マスタチェック（日別）.アルゴリズム.マスタチェック(日別)の集計処理
 *
 * @author Le Huu Dat
 */
@Stateless
public class AggregateProcessMasterCheckDailyService {

    @Inject
    private EmployeeInfoByWorkplaceService employeeInfoByWorkplaceService;
    @Inject
    private FixedExtractionDayConRepository fixedExtractionDayConRepo;
    @Inject
    private FixedExtractionDayItemsRepository fixedExtractionDayItemsRepo;
    @Inject
    private DailyBeforeCheckService dailyBeforeCheckService;

    /**
     * マスタチェック(日別)の集計処理
     *
     * @param cid             会社ID
     * @param period          期間
     * @param alarmCheckWkpId List＜職場のエラームチェックID＞
     * @param workplaceIds    List＜職場ID＞
     * @return List＜アラーム抽出結果＞
     */
    public List<AlarmListExtractionInfoWorkplaceDto> process(String cid, DatePeriod period, List<String> alarmCheckWkpId, List<String> workplaceIds) {
        // 職場ID一覧から社員情報を取得する。
        Map<String, List<EmployeeInfoImported>> empInfoMap = employeeInfoByWorkplaceService.get(workplaceIds, period);

        List<AlarmListExtractionInfoWorkplaceDto> alarmListResults = new ArrayList<>();

        // 取得したList＜社員情報＞をチェック
        if (empInfoMap.size() == 0) {
            return alarmListResults;
        }
        // ドメインモデル「アラームリスト（職場）日別の固定抽出条件」を取得する
        List<FixedExtractionDayCon> fixedExtractDayCons = fixedExtractionDayConRepo.getRange(alarmCheckWkpId);

        List<FixedCheckDayItems> fixedCheckDayItems = fixedExtractDayCons.stream()
                .map(FixedExtractionDayCon::getFixedCheckDayItems).collect(Collectors.toList());
        // ドメインモデル「アラームリスト（職場）日別の固定抽出項目」を取得
        List<FixedExtractionDayItems> items = fixedExtractionDayItemsRepo.get(fixedCheckDayItems);

        // ※取得したMap＜職場ID、List＜社員情報＞＞からList＜社員ID＞を作成
        List<String> employeeIds = empInfoMap.entrySet().stream().flatMap(x -> x.getValue().stream()
                .map(EmployeeInfoImported::getSid)).distinct().collect(Collectors.toList());
        // チェック前の取得するデータ
        dailyBeforeCheckService.getData(employeeIds, items, period, workplaceIds);

        // チェック処理
        // TODO

        // 取得したList＜アラーム抽出結果（職場別）＞を返す
        return alarmListResults;
    }
}
