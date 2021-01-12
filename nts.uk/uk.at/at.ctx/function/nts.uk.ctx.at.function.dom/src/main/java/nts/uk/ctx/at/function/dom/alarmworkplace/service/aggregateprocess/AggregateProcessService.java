package nts.uk.ctx.at.function.dom.alarmworkplace.service.aggregateprocess;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.function.dom.adapter.workplace.WorkPlaceInforExport;
import nts.uk.ctx.at.function.dom.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.function.dom.adapter.workrecord.erroralarm.alarmlistworkplace.AggregateProcessAdapter;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternCode;
import nts.uk.ctx.at.function.dom.alarmworkplace.AlarmPatternSettingWorkPlace;
import nts.uk.ctx.at.function.dom.alarmworkplace.AlarmPatternSettingWorkPlaceRepository;
import nts.uk.ctx.at.function.dom.alarmworkplace.CheckCondition;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.AlarmCheckCdtWkpCtgRepository;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.AlarmCheckCdtWorkplaceCategory;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.WorkplaceCategory;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractresult.AlarmListExtractInfoWorkplace;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractresult.AlarmListExtractInfoWorkplaceRepository;
import nts.uk.shr.com.i18n.TextResource;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.就業機能.アラーム_職場別.アルゴリズム.集計処理.集計処理
 * 集計処理
 *
 * @author Le Huu Dat
 */
@Stateless
public class AggregateProcessService {

    @Inject
    private AlarmPatternSettingWorkPlaceRepository alarmPatternSettingWorkPlaceRepo;
    @Inject
    private AlarmCheckCdtWkpCtgRepository alarmCheckCdtWkpCtgRepo;
    @Inject
    private AggregateProcessAdapter aggregateProcessAdapter;
    @Inject
    private AlarmListExtractInfoWorkplaceRepository alarmListExtractInfoWorkplaceRepo;
    @Inject
    private WorkplaceAdapter workplaceAdapter;

    /**
     * 集計処理
     *
     * @param cid              会社ID
     * @param alarmPatternCode パターンコード
     * @param workplaceIds     List<職場ID>
     * @param periods          List<カテゴリ別期間>
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<AlarmListExtractInfoWorkplace> process(String cid, String alarmPatternCode, List<String> workplaceIds,
                                                       List<PeriodByAlarmCategory> periods, String processId,
                                                       Consumer<Integer> counter, Supplier<Boolean> shouldStop) {
        GeneralDate baseDate = GeneralDate.today();
        List<AlarmListExtractInfoWorkplace> extractInfoAll = new ArrayList<>();
        // [No.560]職場IDから職場の情報をすべて取得する
        Map<String, WorkPlaceInforExport> wpInfoMap = this.workplaceAdapter.getWorkplaceInforByWkpIds(cid, workplaceIds, baseDate)
                .stream().collect(Collectors.toMap(WorkPlaceInforExport::getWorkplaceId, x -> x));

        // パラメータ．パターンコードをもとにドメインモデル「アラームリストパターン設定(職場別)」を取得する
        Optional<AlarmPatternSettingWorkPlace> patternOpt = alarmPatternSettingWorkPlaceRepo.getBy(cid, new AlarmPatternCode(alarmPatternCode));
        if (!patternOpt.isPresent()) {
            throw new RuntimeException("「アラームリストパターン設定(職場別) 」が見つかりません！");
        }

        // 取得した「アラームリストパターン設定(職場別)」．カテゴリ別チェック条件をループする
        List<CheckCondition> checkConList = patternOpt.get().getCheckConList();
        for (PeriodByAlarmCategory ctgPeriod : periods) {
            List<AlarmListExtractInfoWorkplace> extractInfos = new ArrayList<>();

            // 抽出処理停止フラグが立っているかチェックする
            if (shouldStop.get()) {
                break;
            }

            Optional<CheckCondition> checkCdtOpt = checkConList.stream().filter(x -> x.getWorkplaceCategory().value == ctgPeriod.category).findFirst();
            if (!checkCdtOpt.isPresent()) {
                counter.accept(1);
                continue;
            }

            CheckCondition checkCdt = checkCdtOpt.get();
            WorkplaceCategory category = checkCdt.getWorkplaceCategory();
            // ドメインモデル「カテゴリ別アラームチェック条件(職場別)」を取得する。
            List<AlarmCheckCdtWorkplaceCategory> conditionCtgs = alarmCheckCdtWkpCtgRepo.getBy(category,
                    checkCdt.getCheckConditionLis());
            List<String> alarmCheckWkpId = new ArrayList<>();
            List<String> optionalIds = new ArrayList<>();
            for (AlarmCheckCdtWorkplaceCategory condCtg : conditionCtgs) {
                alarmCheckWkpId.addAll(condCtg.getCondition().getAlarmCheckWkpID());
                optionalIds.addAll(condCtg.getCondition().getListOptionalIDs());
            }

            // ループ中のカテゴリをチェック
            switch (category) {
                case MASTER_CHECK_BASIC:
                    // アルゴリズム「マスタチェック(基本)の集計処理」を実行する
                    extractInfos = aggregateProcessAdapter.processMasterCheckBasic(cid, ctgPeriod.getYmPeriod(),
                            alarmCheckWkpId, workplaceIds);
                    break;
                case MASTER_CHECK_DAILY:
                    extractInfos = aggregateProcessAdapter.processMasterCheckDaily(cid, ctgPeriod.getPeriod(),
                            alarmCheckWkpId, workplaceIds);
                    // アルゴリズム「マスタチェック(日別)の集計処理」を実行する
                    break;
                case MASTER_CHECK_WORKPLACE:
                    // アルゴリズム「マスタチェック(職場)の集計処理」を実行する
                    extractInfos = aggregateProcessAdapter.processMasterCheckWorkplace(cid, ctgPeriod.getYmPeriod(),
                            alarmCheckWkpId, workplaceIds);
                    break;
                case SCHEDULE_DAILY:
                    // アルゴリズム「スケジュール／日次の集計処理」を実行する
                    extractInfos = aggregateProcessAdapter.processSchedule(cid, ctgPeriod.getPeriod(),
                            alarmCheckWkpId, optionalIds, workplaceIds);
                    break;
                case MONTHLY:
                    // アルゴリズム「月次の集計処理」を実行する
                    extractInfos = aggregateProcessAdapter.processMonthly(cid, ctgPeriod.getYearMonth(),
                            alarmCheckWkpId, optionalIds, workplaceIds);
                    break;
                case APPLICATION_APPROVAL:
                    // アルゴリズム「申請承認の集計処理」を実行する
                    extractInfos = aggregateProcessAdapter.processAppApproval(ctgPeriod.getPeriod(),
                            alarmCheckWkpId, workplaceIds);
                    break;
            }

            // List＜アラーム抽出結果（職場別）＞に返す値を追加
            extractInfos.forEach(x -> {
                x.setProcessId(processId);
                x.setCategoryName(TextResource.localize(x.getCategory().nameId));

                if (!x.getExtractResult().getWorkplaceId().isPresent()) return;
                if (wpInfoMap.containsKey(x.getExtractResult().getWorkplaceId().get())) {
                    WorkPlaceInforExport wpInfo = wpInfoMap.get(x.getExtractResult().getWorkplaceId().get());
                    x.getExtractResult().setWorkplaceInfo(wpInfo.getWorkplaceCode(), wpInfo.getWorkplaceName(), wpInfo.getHierarchyCode());
                }

            });
            alarmListExtractInfoWorkplaceRepo.addAll(extractInfos);
            extractInfoAll.addAll(extractInfos);
            counter.accept(1);
        }

        return extractInfoAll;
    }
}
