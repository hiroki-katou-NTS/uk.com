package nts.uk.ctx.at.function.app.find.alarmworkplace.alarmlist;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternCode;
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
                    SingleMonth singleMonth = (SingleMonth) checkCond.getRangeToExtract();
                    Integer ym = YearMonth.of(processingYm.year(), singleMonth.getMonthNo()).v();
                    period = new CheckConditionDto(category, ym);
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
                    // 申請承認の期間を取得する。//TODO Q&A 38144
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
     */
    private CheckConditionDto getBasicPeriod(RangeToExtract rangeToExtract, YearMonth processingYm) {
        return getYmPeriod(WorkplaceCategory.MASTER_CHECK_BASIC, rangeToExtract, processingYm);
    }

    /**
     * 職場の期間を表示に取得する
     *
     * @param rangeToExtract ドメイン「抽出期間(月単位)」
     * @param processingYm   当月の年月
     */
    private CheckConditionDto getWorkplacePeriod(RangeToExtract rangeToExtract, YearMonth processingYm) {
        return getYmPeriod(WorkplaceCategory.MASTER_CHECK_WORKPLACE, rangeToExtract, processingYm);
    }

    private CheckConditionDto getYmPeriod(WorkplaceCategory category, RangeToExtract rangeToExtract, YearMonth processingYm) {
        ExtractionPeriodMonthly period = (ExtractionPeriodMonthly) rangeToExtract;
        // 「Input．当月の年月」-「Input．開始月．抽出期間(月単位)．月数」を開始月とする
        Optional<MonthNo> startMonthNo = period.getStartMonth().getStrMonthNo();
        Integer startYm = null;
        if (startMonthNo.isPresent()) {
            startYm = YearMonth.of(processingYm.year(), startMonthNo.get().getMonthNo()).v();
        }

        // 「Input．当月の年月」-「Input．終了月．抽出期間(月単位)．月数」を終了月とする
        Optional<MonthNo> endMonthNo = period.getEndMonth().getEndMonthNo();
        Integer endYm = null;
        if (endMonthNo.isPresent()) {
            endYm = YearMonth.of(processingYm.year(), endMonthNo.get().getMonthNo()).v();
        }
        return new CheckConditionDto(category, startYm, endYm);
    }

    /**
     * スケジュール／日次の期間を表示に取得する
     *
     * @param category       カテゴリ
     * @param rangeToExtract ドメイン「抽出期間(日単位)」
     * @param processingYm   当月の年月
     */
    private CheckConditionDto getScheduleDailyPeriod(WorkplaceCategory category, RangeToExtract rangeToExtract,
                                                     YearMonth processingYm) {
        ExtractionPeriodDaily period = (ExtractionPeriodDaily) rangeToExtract;
        GeneralDate systemDate = GeneralDate.today();

        // TODO Q&A 38158
        // 開始日の指定方法をチェックする。
        if (StartSpecify.DAYS.equals(period.getStartDate().getStartSpecify())) {
            // 開始日を作成する。
            // ・開始日　＝　システム日付ー「Input．抽出期間(日単位)．開始日．日数指定．日」
            Optional<Days> strDays = period.getStartDate().getStrDays();
            GeneralDate startdate;
            if (strDays.isPresent()) {
                startdate = GeneralDate.ymd(systemDate.year(), systemDate.month(), strDays.get().getDay().v());
            } else {
                startdate = GeneralDate.ymd(systemDate.year(), systemDate.month(), 1); // TODO
            }

            // 終了日を作成する。
            // ・終了日　＝　システム日付ー「Input．抽出期間(日単位)．終了日．日数指定．日」
            Optional<Days> endDays = period.getEndDate().getEndDays();
            GeneralDate enddate;
            if (endDays.isPresent()) {
                enddate = GeneralDate.ymd(systemDate.year(), systemDate.month(), endDays.get().getDay().v());
            } else {
                enddate = GeneralDate.ymd(systemDate.year(), systemDate.month(), 1).addMonths(1).addDays(-1); // TODO
            }
            return new CheckConditionDto(category, startdate, enddate);

        } else if (StartSpecify.MONTH.equals(period.getStartDate().getStartSpecify())) {
            // 開始日を作成する。
            // ・開始日　＝　「Input．当月の年月」－　「Input．抽出期間(日単位)．開始日．締め日指定．月数」
            Optional<Month> strMonth = period.getStartDate().getStrMonth();
            int startYm = processingYm.month();
            if (strMonth.isPresent()) {
                startYm = YearMonth.of(processingYm.year(), period.getStartDate().getStrMonth().get().getMonth()).v();
            }

            // 終了日を作成する。
            // ・終了日　＝　「Input．当月の年月」－　「Input．抽出期間(日単位)．終了日．締め日指定．月数」
            Optional<Month> endMonth = period.getEndDate().getEndMonth();
            int endYm = processingYm.month();
            if (endMonth.isPresent()) {
                endYm = YearMonth.of(processingYm.year(), period.getEndDate().getEndMonth().get().getMonth()).v();
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
