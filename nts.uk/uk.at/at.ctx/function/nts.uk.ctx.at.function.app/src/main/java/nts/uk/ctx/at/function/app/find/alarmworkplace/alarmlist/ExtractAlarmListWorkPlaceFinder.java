package nts.uk.ctx.at.function.app.find.alarmworkplace.alarmlist;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternCode;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.PreviousClassification;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.Days;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.Month;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.StartSpecify;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.MonthNo;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.singlemonth.SingleMonth;
import nts.uk.ctx.at.function.dom.alarmworkplace.*;
import nts.uk.ctx.at.function.dom.alarmworkplace.alarmlist.ExtractAlarmListWorkPlaceService;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.WorkplaceCategory;
import nts.uk.ctx.at.function.dom.alarmworkplace.export.AlarmListExtractResultWorkplaceData;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractresult.AlarmListExtractInfoWorkplaceRepository;
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
import java.util.Comparator;
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
    @Inject
    private AlarmListExtractInfoWorkplaceRepository alarmListExtractInfoWorkplaceRepo;

    /**
     * アラームリストの初期起動
     */
    public InitActiveAlarmListDto initActiveAlarmList() {
        String cid = AppContexts.user().companyId();
        String sid = AppContexts.user().employeeId();

        // ドメインモデル「アラームリストパターン設定(職場別)」を取得する。
        List<AlarmPatternSettingWorkPlace> alarmPatterns = alarmPatternSettingWorkPlaceRepo.findByCompanyId(cid);
        alarmPatterns = alarmPatterns.stream().sorted(Comparator.comparing(AlarmPatternSettingWorkPlace::getAlarmPatternCD))
                .collect(Collectors.toList());

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
        Optional<AlarmPatternSettingWorkPlace> patern = alarmPatternSettingWorkPlaceRepo.getBy(cid,
                new AlarmPatternCode(alarmPatternCode));
        List<CheckCondition> checkConList = patern.get().getCheckConList();

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
            CheckConditionDto period = null;
            switch (category) {
                case MASTER_CHECK_BASIC:
                    // 基本の期間を表示に取得する
                    period = getBasicPeriod(checkCond.getRangeToExtract(), processingYm);
                    break;
                case MASTER_CHECK_WORKPLACE:
                    // 職場の期間を表示に取得する
                    period = getWorkplacePeriod(checkCond.getRangeToExtract(), processingYm);
                    break;
                case MONTHLY:
                    // 期間を作成する。
                    period = getMonthTime(checkCond.getRangeToExtract());
                    break;
                case SCHEDULE_DAILY:
                    // スケジュール／日次の期間を取得する。
                    period = getScheduleDailyPeriod(category, checkCond.getRangeToExtract(), processingYm);
                    break;
                case MASTER_CHECK_DAILY:
                    // マスタチェック(日次)の期間を取得する。
                    period = getScheduleDailyPeriod(category, checkCond.getRangeToExtract(), processingYm);
                    break;
                case APPLICATION_APPROVAL:
                    // 申請承認の期間を取得する。
                    period = getScheduleDailyPeriod(category, checkCond.getRangeToExtract(), processingYm);
                    break;
            }
            if (period != null) {
                periods.add(period);
            }
        }

        return periods;
    }

    /**
     * 基本の期間を表示に取得する
     *
     * @param rangeToExtract ドメイン「抽出期間(月単位)」
     * @param processingYm   当月の年月
     * @return 期間
     */
    private CheckConditionDto getBasicPeriod(RangeToExtract rangeToExtract, YearMonth processingYm) {
        return getYmPeriod(WorkplaceCategory.MASTER_CHECK_BASIC, rangeToExtract, processingYm);
    }

    /**
     * 職場の期間を表示に取得する
     *
     * @param rangeToExtract ドメイン「抽出期間(月単位)」
     * @param processingYm   当月の年月
     * @return 期間
     */
    private CheckConditionDto getWorkplacePeriod(RangeToExtract rangeToExtract, YearMonth processingYm) {
        return getYmPeriod(WorkplaceCategory.MASTER_CHECK_WORKPLACE, rangeToExtract, processingYm);
    }

    private CheckConditionDto getYmPeriod(WorkplaceCategory category, RangeToExtract rangeToExtract, YearMonth processingYm) {
        ExtractionPeriodMonthly period = (ExtractionPeriodMonthly) rangeToExtract;
        // 「Input．当月の年月」-「Input．開始月．抽出期間(月単位)．月数」を開始月とする
        Optional<MonthNo> startMonthNo = period.getStartMonth().getStrMonthNo();
        YearMonth startYm = null;
        if (startMonthNo.isPresent()) {
            startYm = processingYm.addMonths(-startMonthNo.get().getMonthNo());
        }

        // 「Input．当月の年月」-「Input．終了月．抽出期間(月単位)．月数」を終了月とする
        Optional<MonthNo> endMonthNo = period.getEndMonth().getEndMonthNo();
        YearMonth endYm = null;
        if (endMonthNo.isPresent()) {
            endYm = processingYm.addMonths(-endMonthNo.get().getMonthNo());
        }
        return new CheckConditionDto(category, startYm, endYm);
    }

    /**
     * 月次の時間を取得する
     *
     * @param rangeToExtract ドメイン「抽出期間(単月)」
     * @return 期間
     */
    private CheckConditionDto getMonthTime(RangeToExtract rangeToExtract) {
        SingleMonth singleMonth = (SingleMonth) rangeToExtract;
        // 時間を作成する。
        YearMonth ym = YearMonth.of(GeneralDate.today().year(), GeneralDate.today().month());
        // ・　時間　＝　システム日付の年月ー「Input．抽出期間(単月)．月数指定．月数」
        ym = ym.addMonths(-singleMonth.getMonthNo());
        // 作成した時間を返す。
        return new CheckConditionDto(WorkplaceCategory.MONTHLY, ym);
    }

    /**
     * スケジュール／日次の期間を表示に取得する
     *
     * @param category       カテゴリ
     * @param rangeToExtract ドメイン「抽出期間(日単位)」
     * @param processingYm   当月の年月
     * @return 期間
     */
    private CheckConditionDto getScheduleDailyPeriod(WorkplaceCategory category, RangeToExtract rangeToExtract,
                                                     YearMonth processingYm) {
        ExtractionPeriodDaily period = (ExtractionPeriodDaily) rangeToExtract;
        GeneralDate systemDate = GeneralDate.today();

        // TODO Q&A 38158
        // 開始日の指定方法をチェックする。
        if (StartSpecify.DAYS.equals(period.getStartDate().getStartSpecify())) {
            Optional<Days> strDays = period.getStartDate().getStrDays();
            Optional<Days> endDays = period.getEndDate().getEndDays();
            GeneralDate startDate = null;
            GeneralDate endDate = null;
            if (strDays.isPresent()) {
                // 「前・後区分」をチェックする
                if (PreviousClassification.BEFORE.equals(strDays.get().getDayPrevious())) {
                    // 開始日を作成する。
                    // ・開始日　＝　システム日付ー「Input．抽出期間(日単位)．開始日．日数指定．日
                    startDate = systemDate.addDays(-strDays.get().getDay().v());
                } else {
                    // ・開始日　＝　システム日付+「Input．抽出期間(日単位)．開始日．日数指定．日」
                    startDate = systemDate.addDays(strDays.get().getDay().v());
                }
            }

            if (endDays.isPresent()) {
                // 「前・後区分」をチェックする
                if (PreviousClassification.BEFORE.equals(endDays.get().getDayPrevious())) {
                    // 終了日を作成する。
                    // ・終了日　＝　システム日付ー「Input．抽出期間(日単位)．終了日．日数指定．日
                    endDate = systemDate.addDays(-endDays.get().getDay().v());
                } else {
                    // 終了日を作成する。
                    // ・終了日　＝　システム日付+「Input．抽出期間(日単位)．終了日．日数指定．日」
                    endDate = systemDate.addDays(endDays.get().getDay().v());
                }
            }

            return new CheckConditionDto(category, startDate, endDate);

        } else if (StartSpecify.MONTH.equals(period.getStartDate().getStartSpecify())) {
            // 開始日を作成する。
            Optional<Month> strMonth = period.getStartDate().getStrMonth();
            YearMonth startYm = null;
            if (strMonth.isPresent()) {
                // 開始日　＝　「Input．当月の年月」－　「Input．抽出期間(日単位)．開始日．締め日指定．月数」
                startYm = processingYm.addMonths(-strMonth.get().getMonth());
            }

            // 終了日を作成する。
            Optional<Month> endMonth = period.getEndDate().getEndMonth();
            YearMonth endYm = null;
            if (endMonth.isPresent()) {
                // ・終了日　＝　「Input．当月の年月」－　「Input．抽出期間(日単位)．終了日．締め日指定．月数」
                endYm = processingYm.addMonths(-endMonth.get().getMonth());
            }

            return new CheckConditionDto(category, startYm, endYm);
        }

        return null;
    }

    public List<AlarmListExtractResultWorkplaceData> getExtractResult(String processId) {
        // 取得した職場情報一覧から「アラーム抽出結果（職場別）」にデータをマッピングする
        return AlarmListExtractResultWorkplaceData.fromDomains(alarmListExtractInfoWorkplaceRepo.getById(processId));
    }
}
