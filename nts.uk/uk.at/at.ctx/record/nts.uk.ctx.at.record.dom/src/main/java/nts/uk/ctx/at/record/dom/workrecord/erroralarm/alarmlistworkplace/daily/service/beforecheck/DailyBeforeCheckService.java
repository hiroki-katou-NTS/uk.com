package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.service.beforecheck;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.FixedCheckDayItems;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.FixedExtractionDayItems;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.PersonEmpBasicInfoImport;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.service.beforecheck.unregistedstamp.UnregistedStampCardService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.マスタチェック（日別）.アルゴリズム.マスタチェック(日別)の集計処理.チェック前の取得するデータ
 *
 * @author Le Huu Dat
 */
@Stateless
public class DailyBeforeCheckService {

    @Inject
    private EmpEmployeeAdapter empEmployeeAdapter;
    @Inject
    private UnregistedStampCardService unregistedStampService;

    /**
     * チェック前の取得するデータ
     *
     * @param employeeIds  List＜社員ID＞
     * @param items        List<アラームリスト（職場）日別の固定抽出項目＞
     * @param period       期間
     * @param workplaceIds List＜職場ID＞
     * @return Object
     * List＜個人社員基本情報＞
     * List＜休職・休業社員ID＞
     * Map＜職場ID、List＜打刻日、未登録打刻カード＞＞
     * List＜日次の外部予算実績＞
     * Map＜社員ID、打刻＞
     */
    public DataBeforeCheckDto getData(List<String> employeeIds, List<FixedExtractionDayItems> items, DatePeriod period, List<String> workplaceIds) {
        DataBeforeCheckDto data = new DataBeforeCheckDto();

        // Input．List<アラームリスト（職場）日別の固定抽出項目＞のNOをチェック
        if (items.stream().anyMatch(x -> FixedCheckDayItems.NEW_EMPLOYEE.equals(x.getFixedCheckDayItems()) ||
                FixedCheckDayItems.RETIREE.equals(x.getFixedCheckDayItems()) ||
                FixedCheckDayItems.PERSONS_ELIGIBLE_ANNUAL_LEAVE.equals(x.getFixedCheckDayItems()) ||
                FixedCheckDayItems.STAMPING_BEFORE_JOINING.equals(x.getFixedCheckDayItems()) ||
                FixedCheckDayItems.STAMPING_AFTER_RETIREMENT.equals(x.getFixedCheckDayItems()))) {
            // 社員ID(List)から個人社員基本情報を取得
            List<PersonEmpBasicInfoImport> empInfos = empEmployeeAdapter.getPerEmpBasicInfo(employeeIds);
            data.setEmpInfos(empInfos);
        }

        if (items.stream().anyMatch(x -> FixedCheckDayItems.LEAVE.equals(x.getFixedCheckDayItems()))) {
            // 社員（List）と期間から休職休業を取得する
            // TODO Q&A 36518
        }

        if (items.stream().anyMatch(x -> FixedCheckDayItems.CARD_UNREGISTERD_STAMP.equals(x.getFixedCheckDayItems()))) {
            // 職場IDから未登録打刻カードを取得
            Map<String, List<Object>> unregistedStampCards = unregistedStampService.getUnregistedStampCard(workplaceIds, period);
            data.setUnregistedStampCards(unregistedStampCards);
        }

        if (items.stream().anyMatch(x -> FixedCheckDayItems.PLAN_NOT_REGISTERED_PEOPLE.equals(x.getFixedCheckDayItems()) ||
                FixedCheckDayItems.PLAN_NOT_REGISTERED_TIME.equals(x.getFixedCheckDayItems()) ||
                FixedCheckDayItems.PLAN_NOT_REGISTERED_AMOUNT.equals(x.getFixedCheckDayItems()))) {
            // ドメインモデル「日次の外部予算実績」を取得
            // TODO Q&A 36545
        }

        if (items.stream().anyMatch(x -> FixedCheckDayItems.STAMPING_BEFORE_JOINING.equals(x.getFixedCheckDayItems()) ||
                FixedCheckDayItems.STAMPING_AFTER_RETIREMENT.equals(x.getFixedCheckDayItems()))) {
            // List<社員ID＞から打刻カードを全て取得する

            // ドメインモデル「打刻」を取得する
        }

        // 全て取得したデータを返す
        return data;
    }
}
