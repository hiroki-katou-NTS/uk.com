package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.service.beforecheck;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.workschedule.budgetcontrol.budgetperformance.ExBudgetDailyAdapter;
import nts.uk.ctx.at.record.dom.adapter.workschedule.budgetcontrol.budgetperformance.ExBudgetDailyImport;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.service.GetStampCardQuery;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.FixedCheckDayItems;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.FixedExtractionDayItems;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampDakokuRepository;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.PersonEmpBasicInfoImport;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.service.beforecheck.unregistedstamp.UnregistedStampCardService;
import nts.uk.ctx.at.shared.dom.adapter.temporaryabsence.SharedTempAbsenceAdapter;
import nts.uk.ctx.at.shared.dom.adapter.temporaryabsence.TempAbsenceImport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.group.AffWorkplaceGroupImport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.group.SharedAffWorkplaceGroupAdapter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

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
    @Inject
    private GetStampCardQuery getStampCardQuery;
    @Inject
    private StampDakokuRepository stampDakokuRepo;
    @Inject
    private SharedTempAbsenceAdapter sharedTempAbsenceAdapter;
    @Inject
    private SharedAffWorkplaceGroupAdapter sharedAffWorkplaceGroupAdapter;
    @Inject
    private ExBudgetDailyAdapter exBudgetDailyAdapter;

    /**
     * チェック前の取得するデータ
     *
     * @param cid                  会社ID
     * @param employeeIds          List＜社員ID＞
     * @param fixedExtractDayItems List<アラームリスト（職場）日別の固定抽出項目＞
     * @param period               期間
     * @param workplaceIds         List＜職場ID＞
     * @return Object
     * List＜個人社員基本情報＞
     * List＜休職・休業社員ID＞
     * Map＜職場ID、List＜打刻日、未登録打刻カード＞＞
     * List＜日次の外部予算実績＞
     * Map＜社員ID、打刻＞
     */
    public DataBeforeCheckDto getData(String cid, List<String> employeeIds, List<FixedExtractionDayItems> fixedExtractDayItems,
                                      DatePeriod period, List<String> workplaceIds) {
        DataBeforeCheckDto data = new DataBeforeCheckDto();

        // Input．List<アラームリスト（職場）日別の固定抽出項目＞のNOをチェック
        if (fixedExtractDayItems.stream().anyMatch(x -> FixedCheckDayItems.NEW_EMPLOYEE.equals(x.getFixedCheckDayItems()) ||
                FixedCheckDayItems.RETIREE.equals(x.getFixedCheckDayItems()) ||
                FixedCheckDayItems.PERSONS_ELIGIBLE_ANNUAL_LEAVE.equals(x.getFixedCheckDayItems()) ||
                FixedCheckDayItems.STAMPING_BEFORE_JOINING.equals(x.getFixedCheckDayItems()) ||
                FixedCheckDayItems.STAMPING_AFTER_RETIREMENT.equals(x.getFixedCheckDayItems()))) {
            // 社員ID(List)から個人社員基本情報を取得
            List<PersonEmpBasicInfoImport> personInfos = empEmployeeAdapter.getPerEmpBasicInfo(employeeIds);
            data.setPersonInfos(personInfos);
        }

        if (fixedExtractDayItems.stream().anyMatch(x -> FixedCheckDayItems.LEAVE.equals(x.getFixedCheckDayItems()))) {
            // 社員（List）と期間から休職休業を取得する
            TempAbsenceImport tempAbsence = sharedTempAbsenceAdapter.getTempAbsence(cid, period, employeeIds);
            data.setTempAbsence(tempAbsence);
        }

        if (fixedExtractDayItems.stream().anyMatch(x -> FixedCheckDayItems.CARD_UNREGISTERD_STAMP.equals(x.getFixedCheckDayItems()))) {
            // 職場IDから未登録打刻カードを取得
            Map<String, List<Object>> unregistedStampCards = unregistedStampService.getUnregistedStampCard(workplaceIds, period);
            data.setUnregistedStampCardsByWpMap(unregistedStampCards);
        }

        if (fixedExtractDayItems.stream().anyMatch(x -> FixedCheckDayItems.PLAN_NOT_REGISTERED_PEOPLE.equals(x.getFixedCheckDayItems()) ||
                FixedCheckDayItems.PLAN_NOT_REGISTERED_TIME.equals(x.getFixedCheckDayItems()) ||
                FixedCheckDayItems.PLAN_NOT_REGISTERED_AMOUNT.equals(x.getFixedCheckDayItems()))) {
            // ドメインモデル「職場グループ所属情報」を取得する
            List<AffWorkplaceGroupImport> wpGroups = sharedAffWorkplaceGroupAdapter.getByListWkpIds(cid, workplaceIds);
            List<String> wpGroupIds = wpGroups.stream().map(AffWorkplaceGroupImport::getWKPGRPID).distinct().collect(Collectors.toList());
            List<ExBudgetDailyImport> exBudgetDailies = new ArrayList<>();
            for (String wpGroupId : wpGroupIds){
                // ドメインモデル「日次の外部予算実績」を取得
                exBudgetDailies.addAll(exBudgetDailyAdapter.getAllExtBudgetDailyByPeriod(1, null, wpGroupId, period));
            }

            data.setExBudgetDailies(exBudgetDailies);
        }

        if (fixedExtractDayItems.stream().anyMatch(x -> FixedCheckDayItems.STAMPING_BEFORE_JOINING.equals(x.getFixedCheckDayItems()) ||
                FixedCheckDayItems.STAMPING_AFTER_RETIREMENT.equals(x.getFixedCheckDayItems()))) {
            // List<社員ID＞から打刻カードを全て取得する
            Map<String, StampNumber> stampCards = getStampCardQuery.getStampNumberBy(employeeIds);

            Map<String, List<Stamp>> stampsByEmpMap = new HashMap<>();
            // ドメインモデル「打刻」を取得する
            for (Map.Entry<String, StampNumber> stampCard : stampCards.entrySet()) {
                List<Stamp> stamps = stampDakokuRepo.getByCardAndPeriod(cid, Collections.singletonList(stampCard.getValue().v()), period);
                stampsByEmpMap.put(stampCard.getKey(), stamps);
            }
            data.setStampsByEmpMap(stampsByEmpMap);
        }

        // 全て取得したデータを返す
        return data;
    }
}
