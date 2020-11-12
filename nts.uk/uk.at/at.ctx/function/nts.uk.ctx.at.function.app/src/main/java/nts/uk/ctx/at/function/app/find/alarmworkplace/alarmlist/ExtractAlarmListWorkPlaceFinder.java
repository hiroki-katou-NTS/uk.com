package nts.uk.ctx.at.function.app.find.alarmworkplace.alarmlist;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternCode;
import nts.uk.ctx.at.function.dom.alarm.workplace.checkcondition.WorkplaceCategory;
import nts.uk.ctx.at.function.dom.alarmworkplace.AlarmPatternSettingWorkPlace;
import nts.uk.ctx.at.function.dom.alarmworkplace.AlarmPatternSettingWorkPlaceRepository;
import nts.uk.ctx.at.function.dom.alarmworkplace.CheckCondition;
import nts.uk.ctx.at.function.dom.alarmworkplace.RangeToExtract;
import nts.uk.ctx.at.function.dom.alarmworkplace.alarmlist.ExtractAlarmListWorkPlaceService;
import nts.uk.ctx.at.record.dom.organization.EmploymentHistoryImported;
import nts.uk.ctx.at.record.dom.organization.adapter.EmploymentAdapter;
import nts.uk.ctx.at.shared.app.query.workrule.closure.WorkClosureQueryProcessor;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.CurrentMonth;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * UKDesign.UniversalK.就業.KAL_アラームリスト.KAL011_アラームリスト(職場別)
 *
 * @author Le Huu Dat
 */
@Stateless
public class ExtractAlarmListWorkPlaceFinder {

    @Inject
    private ExtractAlarmListWorkPlaceService extractAlarmListWorkPlaceService;
    @Inject
    private AlarmPatternSettingWorkPlaceRepository alarmPatternSettingWorkPlaceRepo;
    @Inject
    private EmploymentAdapter employmentAdapter;
    @Inject
    private WorkClosureQueryProcessor workClosureQueryProcessor;
    @Inject
    private ClosureRepository closureRepo;

    /**
     * アラームリストの初期起動
     */
    public InitActiveAlarmListDto initActiveAlarmList() {
        String cid = AppContexts.user().companyId();
        String sid = AppContexts.user().employeeId();

        // ドメインモデル「アラームリストパターン設定(職場別)」を取得する。
        List<AlarmPatternSettingWorkPlace> alarmPatterns = alarmPatternSettingWorkPlaceRepo.findByCompanyId(cid);

        // アルゴリズム「社員IDと基準日から社員の雇用コードを取得」を実行する。
        Optional<EmploymentHistoryImported> empHistory = this.employmentAdapter.getEmpHistBySid(cid, sid,
                GeneralDate.today());

        if (!empHistory.isPresent()) {
            return new InitActiveAlarmListDto(null, alarmPatterns, null);
        }

        String employmentCode = empHistory.get().getEmploymentCode();
        // 雇用に紐づく締めIDを取得する
        Integer closureId = workClosureQueryProcessor.findClosureByEmploymentCode(employmentCode);
        // 当月の年月を取得する。
        Integer processingYm = null;
        Optional<Closure> closureOpt = closureRepo.findById(cid, closureId);
        if (closureOpt.isPresent()) {
            CurrentMonth closureMonth = closureOpt.get().getClosureMonth();
            if (closureMonth != null) {
                processingYm = closureMonth.getProcessingYm().v();
            }
        }
        return new InitActiveAlarmListDto(employmentCode, alarmPatterns, processingYm);
    }

    /**
     * アラームリストパータン設定を選択する
     *
     * @param alarmPatternCode 職場のエラームチェックID
     * @param processingYm     当月の年月
     * @return
     */
    public List<CheckConditionDto> getCheckConditions(String alarmPatternCode, Integer processingYm) {
        String cid = AppContexts.user().companyId();
        // ドメインモデル「チェック条件」を取得する。
        List<CheckCondition> checkConList = alarmPatternSettingWorkPlaceRepo.getCheckCondition(cid,
                new AlarmPatternCode(alarmPatternCode));

        // ドメインモデル「チェック条件」．抽出する範囲をもとに初期表示する期間を求める
        return calcPeriod(checkConList, YearMonth.of(processingYm));
    }

    /**
     * 期間を算出する
     *
     * @param checkConditions ドメイン「チェック条件」一覧
     * @param processingYm    当月の年月
     */
    private List<CheckConditionDto> calcPeriod(List<CheckCondition> checkConditions, YearMonth processingYm) {
        List<CheckConditionDto> periods = new ArrayList<>();
        for (CheckCondition checkCond : checkConditions) {
            WorkplaceCategory category = checkCond.getWorkplaceCategory();
            switch (category) {
                case MASTER_CHECK_BASIC:
                    // 基本の期間を表示に取得する
                    periods.add(getBasicPeriod(checkCond.getRangeToExtract(), processingYm));
                    break;
                case MASTER_CHECK_WORKPLACE:
                    // 職場の期間を表示に取得する
                    periods.add(getWorkplacePeriod(checkCond.getRangeToExtract(), processingYm));
                    break;
                case MONTHLY:
                    // 期間を作成する。
                    // TODO
                    periods.add(new CheckConditionDto(category, GeneralDate.today(), GeneralDate.today()));
                    break;
                case SCHEDULE_DAILY:
                    // スケジュール／日次の期間を取得する。
                    periods.add(getScheduleDailyPeriod(category, checkCond.getRangeToExtract(), processingYm));
                    break;
                case MASTER_CHECK_DAILY:
                    // マスタチェック(日次)の期間を取得する。
                    periods.add(getScheduleDailyPeriod(category, checkCond.getRangeToExtract(), processingYm));
                    break;
                case APPLICATION_APPROVAL:
                    // 申請承認の期間を取得する。
                    // TODO
                    periods.add(new CheckConditionDto(category, GeneralDate.today(), GeneralDate.today()));
                    break;
            }
        }

        return periods;
    }

    /**
     * 基本の期間を表示に取得する
     *
     * @param rangeToExtract ドメイン「抽出期間(月単位)」
     * @param processingYm   当月の年月
     */
    private CheckConditionDto getBasicPeriod(RangeToExtract rangeToExtract, YearMonth processingYm) {
        // TODO
        // 「Input．当月の年月」-「Input．開始月．抽出期間(月単位)．月数」を開始月とする
        GeneralDate startdate = GeneralDate.today();
        // 「Input．当月の年月」-「Input．終了月．抽出期間(月単位)．月数」を終了月とする
        GeneralDate enddate = GeneralDate.today();
        return new CheckConditionDto(WorkplaceCategory.MASTER_CHECK_BASIC, startdate, enddate);
    }

    /**
     * 職場の期間を表示に取得する
     *
     * @param rangeToExtract ドメイン「抽出期間(月単位)」
     * @param processingYm   当月の年月
     */
    private CheckConditionDto getWorkplacePeriod(RangeToExtract rangeToExtract, YearMonth processingYm) {
        // TODO
        // 「Input．当月の年月」-「Input．開始月．抽出期間(月単位)．月数」を開始月とする
        GeneralDate startdate = GeneralDate.today();
        // 「Input．当月の年月」-「Input．終了月．抽出期間(月単位)．月数」を終了月とする
        GeneralDate enddate = GeneralDate.today();
        return new CheckConditionDto(WorkplaceCategory.MASTER_CHECK_WORKPLACE, startdate, enddate);
    }

    /**
     * スケジュール／日次の期間を取得する。
     *
     * @param category       カテゴリ
     * @param rangeToExtract ドメイン「抽出期間(日単位)」
     * @param processingYm   当月の年月
     */
    private CheckConditionDto getScheduleDailyPeriod(WorkplaceCategory category, RangeToExtract rangeToExtract,
                                                     YearMonth processingYm) {
        // TODO
        // 開始日の指定方法をチェックする。

        GeneralDate startdate = GeneralDate.today();
        GeneralDate enddate = GeneralDate.today();
        return new CheckConditionDto(category, startdate, enddate);
    }
}
