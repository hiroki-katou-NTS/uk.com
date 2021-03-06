package nts.uk.ctx.at.function.app.find.alarmworkplace.alarmlist;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.adapter.alarm.AffAtWorkplaceExport;
import nts.uk.ctx.at.function.dom.adapter.alarm.EmployeeWorkplaceAdapter;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternCode;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.PreviousClassification;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.Days;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.EndSpecify;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.Month;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.StartSpecify;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.MonthNo;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.singlemonth.SingleMonth;
import nts.uk.ctx.at.function.dom.alarmworkplace.*;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.WorkplaceCategory;
import nts.uk.ctx.at.function.dom.alarmworkplace.export.AlarmListExtractResultWorkplaceData;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractprocessstatus.AlarmListExtractProcessStatusWorkplace;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractprocessstatus.AlarmListExtractProcessStatusWorkplaceRepository;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractprocessstatus.ExtractState;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractresult.AlarmListExtractInfoWorkplaceRepository;
import nts.uk.ctx.at.record.dom.organization.EmploymentHistoryImported;
import nts.uk.ctx.at.record.dom.organization.adapter.EmploymentAdapter;
import nts.uk.ctx.at.shared.app.service.workrule.closure.ClosureEmploymentService;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.CurrentMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * UKDesign.UniversalK.??????.KAL_?????????????????????.KAL011_?????????????????????(?????????)
 *
 * @author Le Huu Dat
 */
@Stateless
public class ExtractAlarmListWorkPlaceFinder {

    @Inject
    private AlarmPatternSettingWorkPlaceRepository alarmPatternSettingWorkPlaceRepo;
    @Inject
    private EmploymentAdapter employmentAdapter;
    @Inject
    private AlarmListExtractInfoWorkplaceRepository alarmListExtractInfoWorkplaceRepo;
    @Inject
    private AlarmListExtractProcessStatusWorkplaceRepository alarmListExtractProcessStatusWorkplaceRepo;
    @Inject
    private ClosureEmploymentService closureEmploymentService;
    @Inject
    private EmployeeWorkplaceAdapter empWkpAdapter;

    /**
     * ????????????????????????????????????
     */
    public InitActiveAlarmListDto initActiveAlarmList() {
        String cid = AppContexts.user().companyId();
        String sid = AppContexts.user().employeeId();
        GeneralDate baseDate = GeneralDate.today();

        List<AffAtWorkplaceExport> empWkpData = empWkpAdapter.getWorkplaceId(Arrays.asList(sid), baseDate);
        String workplaceId = empWkpData.isEmpty() ? null : empWkpData.get(0).getWorkplaceId();

        // ???????????????????????????????????????????????????????????????(?????????)?????????????????????
        List<AlarmPatternSettingWorkPlace> alarmPatterns = alarmPatternSettingWorkPlaceRepo.findByCompanyId(cid);
        alarmPatterns = alarmPatterns.stream().sorted(Comparator.comparing(AlarmPatternSettingWorkPlace::getAlarmPatternCD))
                .collect(Collectors.toList());

        // ???????????????????????????ID????????????????????????????????????????????????????????????????????????
        Optional<EmploymentHistoryImported> empHistory = this.employmentAdapter.getEmpHistBySid(cid, sid, baseDate);
        if (!empHistory.isPresent()) {
            return new InitActiveAlarmListDto(null, alarmPatterns, null, null, null);
        }
        String employmentCode = empHistory.get().getEmploymentCode();
        Integer closureId = null;
        // ????????????????????????????????????????????????
        Optional<Closure> closureOpt = closureEmploymentService.findClosureByEmployee(AppContexts.user().employeeId(),
                GeneralDate.today());
        // ?????????????????????????????????
        Integer processingYm = null;
        DatePeriod datePeriodClosure = null;
        if (closureOpt.isPresent()) {
            CurrentMonth closureMonth = closureOpt.get().getClosureMonth();
            if (closureMonth != null) {
                processingYm = closureMonth.getProcessingYm().v();

                // ??????????????????????????????
                datePeriodClosure = ClosureService.getClosurePeriod(
                        closureOpt.get().getClosureId().value,
                        closureOpt.get().getClosureMonth().getProcessingYm(),
                        closureOpt);
            }
        }
        return new InitActiveAlarmListDto(employmentCode, alarmPatterns, processingYm, datePeriodClosure, workplaceId);
    }

    /**
     * ??????????????????????????????????????????????????????
     *
     * @param alarmPatternCode ?????????????????????????????????ID
     * @param processingYm     ???????????????
     * @return
     */
    public List<CheckConditionDto> getCheckConditions(String alarmPatternCode, Integer processingYm,
                                                      GeneralDate closureStartDate, GeneralDate closureEndDate) {
        String cid = AppContexts.user().companyId();
        // ???????????????????????????????????????????????????????????????
        Optional<AlarmPatternSettingWorkPlace> patern = alarmPatternSettingWorkPlaceRepo.getBy(cid,
                new AlarmPatternCode(alarmPatternCode));
        List<CheckCondition> checkConList = patern.get().getCheckConList();

        // ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        return calcPeriod(checkConList, YearMonth.of(processingYm), new DatePeriod(closureStartDate, closureEndDate));
    }
    /**
     * ?????????????????????
     *
     * @param checkConditions ??????????????????????????????????????????
     * @param processingYm    ??????
     * @param closurePeriod   ????????????
     */
    private List<CheckConditionDto> calcPeriod(List<CheckCondition> checkConditions, YearMonth processingYm, DatePeriod closurePeriod) {
        List<CheckConditionDto> periods = new ArrayList<>();
        for (CheckCondition checkCond : checkConditions) {
            WorkplaceCategory category = checkCond.getWorkplaceCategory();
            CheckConditionDto period = null;
            switch (category) {
                case MASTER_CHECK_BASIC:
                    // ???????????????????????????????????????
                    period = getBasicPeriod(checkCond.getRangeToExtract(), processingYm);
                    break;
                case MASTER_CHECK_WORKPLACE:
                    // ???????????????????????????????????????
                    period = getWorkplacePeriod(checkCond.getRangeToExtract(), processingYm);
                    break;
                case MONTHLY:
                    // ????????????????????????
                    SingleMonth singleMonth = (SingleMonth) checkCond.getRangeToExtract();
                    // ??????????????????Input?????????????????????????????????????????????????????????????????????????????????
                    period = new CheckConditionDto(WorkplaceCategory.MONTHLY, processingYm.addMonths(-singleMonth.getMonthNo()));
                    break;
                case SCHEDULE_DAILY:
                    // ??????????????????????????????????????????????????????
                    period = getScheduleDailyPeriod(category, checkCond.getRangeToExtract(), processingYm, closurePeriod);
                    break;
                case MASTER_CHECK_DAILY:
                    // ?????????????????????(??????)???????????????????????????
                    period = getScheduleDailyPeriod(category, checkCond.getRangeToExtract(), processingYm, closurePeriod);
                    break;
                case APPLICATION_APPROVAL:
                    // ???????????????????????????????????????
                    period = getScheduleDailyPeriod(category, checkCond.getRangeToExtract(), processingYm, closurePeriod);
                    break;
            }
            if (period != null) {
                periods.add(period);
            }
        }

        return periods;
    }

    /**
     * ???????????????????????????????????????
     *
     * @param rangeToExtract ???????????????????????????(?????????)???
     * @param processingYm   ???????????????
     * @return ??????
     */
    private CheckConditionDto getBasicPeriod(RangeToExtract rangeToExtract, YearMonth processingYm) {
        return getYmPeriod(WorkplaceCategory.MASTER_CHECK_BASIC, rangeToExtract, processingYm);
    }

    /**
     * ???????????????????????????????????????
     *
     * @param rangeToExtract ???????????????????????????(?????????)???
     * @param processingYm   ???????????????
     * @return ??????
     */
    private CheckConditionDto getWorkplacePeriod(RangeToExtract rangeToExtract, YearMonth processingYm) {
        return getYmPeriod(WorkplaceCategory.MASTER_CHECK_WORKPLACE, rangeToExtract, processingYm);
    }

    private CheckConditionDto getYmPeriod(WorkplaceCategory category, RangeToExtract rangeToExtract, YearMonth processingYm) {
        ExtractionPeriodMonthly period = (ExtractionPeriodMonthly) rangeToExtract;
        // ???Input?????????????????????-???Input???????????????????????????(?????????)?????????????????????????????????
        Optional<MonthNo> startMonthNo = period.getStartMonth().getStrMonthNo();
        YearMonth startYm = null;
        if (startMonthNo.isPresent()) {
            startYm = processingYm.addMonths(startMonthNo.get().getMonthNo());
        }
        // ???Input?????????????????????-???Input???????????????????????????(?????????)?????????????????????????????????
        Optional<MonthNo> endMonthNo = period.getEndMonth().getEndMonthNo();
        YearMonth endYm = null;
        if (endMonthNo.isPresent()) {
            endYm = processingYm.addMonths(endMonthNo.get().getMonthNo());
        }
        return new CheckConditionDto(category, startYm, endYm);
    }

    /**
     * ????????????????????????????????????????????????????????????
     *
     * @param category       ????????????
     * @param rangeToExtract ???????????????????????????(?????????)???
     * @param processingYm   ??????
     * @return ??????
     */
    private CheckConditionDto getScheduleDailyPeriod(WorkplaceCategory category, RangeToExtract rangeToExtract,
                                                     YearMonth processingYm, DatePeriod closurePeriod) {
        return new CheckConditionDto(category,
                getStartDate(rangeToExtract, processingYm, closurePeriod),
                getEndDate(rangeToExtract, processingYm, closurePeriod));
    }

    /**
     * ????????????????????????
     */
    private GeneralDate getStartDate(RangeToExtract rangeToExtract, YearMonth processingYm, DatePeriod closurePeriod) {
        ExtractionPeriodDaily period = (ExtractionPeriodDaily) rangeToExtract;
        GeneralDate systemDate = GeneralDate.today();
        GeneralDate startDate = null;
        // ??????????????????????????????????????????????????????
        if (StartSpecify.DAYS.equals(period.getStartDate().getStartSpecify())) {
            Optional<Days> strDays = period.getStartDate().getStrDays();
            if (strDays.isPresent()) {
                // ????????????????????????????????????
                if (PreviousClassification.BEFORE.equals(strDays.get().getDayPrevious())) {
                    // ???????????????????????????
                    // ?????????????????????????????????????????????Input???????????????(?????????)????????????????????????????????????
                    startDate = systemDate.addDays(-strDays.get().getDay().v());
                } else {
                    // ???????????????????????????????????????+???Input???????????????(?????????)????????????????????????????????????
                    startDate = systemDate.addDays(strDays.get().getDay().v());
                }
            }
        } else if (StartSpecify.MONTH.equals(period.getStartDate().getStartSpecify())) {
            // ???????????????????????????
            // ???????????????????????????????????????????????????
            Optional<Closure> closureOpt = closureEmploymentService.findClosureByEmployee(AppContexts.user().employeeId(),
                    GeneralDate.today());
            Optional<Month> strMonth = period.getStartDate().getStrMonth();
            if (strMonth.isPresent()) {
                // ??????????????????????????????Input?????????????????????+???Input???????????????(?????????)??????????????????????????????????????????
                YearMonth ym = processingYm.addMonths(strMonth.get().getMonth());
                if (closureOpt.isPresent()) {
                    List<DatePeriod> periodList = closureOpt.get().getPeriodByYearMonth(ym);
                    if (!periodList.isEmpty()) {
                        startDate = periodList.get(0).start();
                    }
                }
            }
        }
        return startDate;
    }

    /**
     * ????????????????????????
     */
    private GeneralDate getEndDate(RangeToExtract rangeToExtract, YearMonth processingYm, DatePeriod closurePeriod) {
        ExtractionPeriodDaily period = (ExtractionPeriodDaily) rangeToExtract;
        GeneralDate systemDate = GeneralDate.today();
        GeneralDate endDate = null;
        // ??????????????????????????????????????????????????????
        if (EndSpecify.DAYS.equals(period.getEndDate().getEndSpecify())) {
            Optional<Days> endDays = period.getEndDate().getEndDays();
            if (endDays.isPresent()) {
                // ????????????????????????????????????
                if (PreviousClassification.BEFORE.equals(endDays.get().getDayPrevious())) {
                    // ???????????????????????????
                    // ?????????????????????????????????????????????Input???????????????(?????????)????????????????????????????????????
                    endDate = systemDate.addDays(-endDays.get().getDay().v());
                } else {
                    // ???????????????????????????????????????+???Input???????????????(?????????)????????????????????????????????????
                    endDate = systemDate.addDays(endDays.get().getDay().v());
                }
            }
        } else if (EndSpecify.MONTH.equals(period.getEndDate().getEndSpecify())) {
            // ???????????????????????????
            // ???????????????????????????????????????????????????
            Optional<Closure> closureOpt = closureEmploymentService.findClosureByEmployee(AppContexts.user().employeeId(),
                    GeneralDate.today());
            Optional<Month> startMonth = period.getEndDate().getEndMonth();
            if (startMonth.isPresent()) {
                // ?????????????????????Input??????????????????????????????+???Input???????????????(?????????)??????????????????????????????????????????
                YearMonth ym = processingYm.addMonths(startMonth.get().getMonth());
                if (closureOpt.isPresent()) {
                    List<DatePeriod> periodList = closureOpt.get().getPeriodByYearMonth(ym);
                    if (!periodList.isEmpty()) {
                        endDate = periodList.get(0).end();
                    }
                }

            }

        }
        return endDate;
    }

    private GeneralDate getDate(YearMonth ym, int day) {
        GeneralDate lastDate = ym.lastGeneralDate();
        if (day > lastDate.day()) {
            return lastDate;
        } else {
            return GeneralDate.ymd(ym.year(), ym.month(), day);
        }
    }
    /**
     * ???????????????????????????
     */
    public void checkProcessStatus() {
        String cid = AppContexts.user().companyId();
        String sid = AppContexts.user().employeeId();
        // ???????????????????????????????????????????????????????????????(?????????)????????????????????????
        List<AlarmListExtractProcessStatusWorkplace> processes = alarmListExtractProcessStatusWorkplaceRepo.getBy(cid, sid, ExtractState.PROCESSING);
        if (!CollectionUtil.isEmpty(processes)) {
            throw new BusinessException("Msg_993");
        }
    }
    public List<AlarmListExtractResultWorkplaceData> getExtractResult(String processId) {
        // ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        return AlarmListExtractResultWorkplaceData.fromDomains(alarmListExtractInfoWorkplaceRepo.getById(processId));
    }
}
