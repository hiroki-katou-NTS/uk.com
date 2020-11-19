package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.service.check;

import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.adapter.workplace.EmployeeInfoImported;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.service.GetStampCardQuery;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.adapter.workplace.AlWorkPlaceInforImport;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.FixedCheckDayItems;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.FixedExtractionDayCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.FixedExtractionDayItems;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.service.beforecheck.DataBeforeCheckDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.service.beforecheck.unregistedstamp.UnregistedStampCardService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.service.check.leave.LeaveCheckService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.service.check.newemployee.NewEmployeeCheckService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.service.check.retiree.RetireeCheckService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.AlarmListExtractionInfoWorkplaceDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampDakokuRepository;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.PersonEmpBasicInfoImport;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.マスタチェック（日別）.アルゴリズム.マスタチェック(日別)の集計処理.チェック処理
 *
 * @author Le Huu Dat
 */
@Stateless
public class DailyCheckService {

    @Inject
    private EmpEmployeeAdapter empEmployeeAdapter;
    @Inject
    private NewEmployeeCheckService newEmployeeCheckService;
    @Inject
    private LeaveCheckService leaveCheckService;
    @Inject
    private RetireeCheckService retireeCheckService;

    /**
     * チェック処理
     *
     * @param cid                  会社ID
     * @param empInfosByWpMap      Map＜職場ID、List＜社員情報＞＞
     * @param personInfos          List＜個人社員基本情報＞
     * @param empLeaves            List<休職休業履歴，休職休業履歴項目の名称>
     * @param unregistedStampCards Map＜職場ID、List＜打刻日、未登録打刻カード＞＞
     * @param dailyExtBudgets      List＜日次の外部予算実績＞
     * @param period               期間
     * @param fixedExtractDayCons  List＜アラームリスト（職場）日別の固定抽出条件＞
     * @param fixedCheckDayItems   List＜アラームリスト（職場）日別の固定抽出項目＞
     * @param stampByEmp           Map＜社員ID、打刻＞
     * @param workPlaceInfos       List＜職場情報＞
     * @return List＜アラームリスト抽出情報（職場）＞
     */
    public List<AlarmListExtractionInfoWorkplaceDto> process(String cid,
                                                             Map<String, List<EmployeeInfoImported>> empInfosByWpMap,
                                                             List<PersonEmpBasicInfoImport> personInfos,
                                                             List<Object> empLeaves,
                                                             Map<String, List<Object>> unregistedStampCards,
                                                             List<Object> dailyExtBudgets,
                                                             DatePeriod period,
                                                             List<FixedExtractionDayCon> fixedExtractDayCons,
                                                             List<FixedExtractionDayItems> fixedCheckDayItems,
                                                             Map<String, Stamp> stampByEmp,
                                                             List<AlWorkPlaceInforImport> workPlaceInfos) {
        List<AlarmListExtractionInfoWorkplaceDto> alarmListResults = new ArrayList<>();

        // Input．List＜アラームリスト（職場）日別の固定抽出条件＞をループする
        for (FixedExtractionDayCon fixedExtractDayCon : fixedExtractDayCons) {
            List<ExtractResultDto> extractResults = new ArrayList<>();

            // Input．Map＜職場ID、List＜社員情報＞＞をループする
            for (Map.Entry<String, List<EmployeeInfoImported>> empInfosByWp : empInfosByWpMap.entrySet()) {
                List<ExtractResultDto> results = new ArrayList<>();
                // Noをチェックする
                switch (fixedExtractDayCon.getFixedCheckDayItems()) {
                    case NEW_EMPLOYEE:
                        results = newEmployeeCheckService.check(personInfos, period);
                        break;
                    case LEAVE:
                        results = leaveCheckService.check(empLeaves, period, empInfosByWp.getValue());
                        break;
                    case RETIREE:
                        results = retireeCheckService.check(personInfos, period);
                        break;
                    case PERSONS_ELIGIBLE_ANNUAL_LEAVE:
                        break;
                    case PERSONS_NOT_ELIGIBLE:
                        break;
                    case PLAN_NOT_REGISTERED_PEOPLE:
                    case PLAN_NOT_REGISTERED_TIME:
                    case PLAN_NOT_REGISTERED_AMOUNT:
                        break;
                    case CARD_UNREGISTERD_STAMP:
                        break;
                    case STAMPING_BEFORE_JOINING:
                        break;
                    case STAMPING_AFTER_RETIREMENT:
                        break;
                }

                // 取得したList＜抽出結果＞を総合List＜抽出結果＞に追加
                extractResults.addAll(results);
            }

            // 「アラームリスト抽出情報（職場）」を作成してList＜アラームリスト抽出情報（職場）＞に追加
            AlarmListExtractionInfoWorkplaceDto alarmListResult = new AlarmListExtractionInfoWorkplaceDto(
                    fixedExtractDayCon.getErrorAlarmWorkplaceId(), 1, extractResults);
            alarmListResults.add(alarmListResult);
        }

        // List＜アラーム抽出結果（職場別）＞を返す
        return alarmListResults;
    }
}
