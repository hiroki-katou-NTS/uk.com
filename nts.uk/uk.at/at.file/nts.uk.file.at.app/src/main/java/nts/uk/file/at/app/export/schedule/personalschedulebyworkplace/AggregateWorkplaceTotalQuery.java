package nts.uk.file.at.app.export.schedule.personalschedulebyworkplace;

import nts.arc.primitive.PrimitiveValueBase;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.aggregation.dom.common.ScheRecGettingAtr;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.TotalTimesCounterService;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.workplacecounter.CountLaborCostTimeService;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.workplacecounter.CountNumberOfPeopleByAttributeService;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.workplacecounter.LaborCostAggregationUnit;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.budget.laborcost.LaborCostBudget;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.budget.laborcost.LaborCostBudgetRepository;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.WorkplaceCounterCategory;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.laborcostandtime.LaborCostAndTime;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.laborcostandtime.WorkplaceCounterLaborCostAndTime;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.laborcostandtime.WorkplaceCounterLaborCostAndTimeRepo;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.timescounting.TimesNumberCounterSelection;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.timescounting.TimesNumberCounterSelectionRepo;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.timescounting.TimesNumberCounterType;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudget;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetRepository;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresults.ExternalBudgetActualResult;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresults.ExternalBudgetActualResultRepository;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresults.ExternalBudgetValues;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.AggregationUnitOfLaborCosts;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.service.AttendanceItemConvertFactory;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.ClassificationCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimes;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesRepository;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.UseAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.bs.employee.dom.classification.Classification;
import nts.uk.ctx.bs.employee.dom.classification.ClassificationRepository;
import nts.uk.ctx.bs.employee.dom.employment.Employment;
import nts.uk.ctx.bs.employee.dom.employment.EmploymentRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.JobTitleRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfo;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfoRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 職場計を集計する
 */
@Stateless
public class AggregateWorkplaceTotalQuery {
    @Inject
    private WorkplaceCounterLaborCostAndTimeRepo laborCostAndTimeRepo;

    @Inject
    private LaborCostBudgetRepository laborCostBudgetRepo;

    @Inject
    private TimesNumberCounterSelectionRepo timesNumberCounterSelectionRepo;

    @Inject
    private TotalTimesRepository totalTimesRepo;

    @Inject
    private WorkTypeRepository workTypeRepo;

    @Inject
    private AttendanceItemConvertFactory factory;

    @Inject
    private WorkTimeSettingRepository workTimeSettingRepo;

    @Inject
    private FixedWorkSettingRepository fixedWorkSettingRepo;

    @Inject
    private FlowWorkSettingRepository flowWorkSettingRepo;

    @Inject
    private FlexWorkSettingRepository flexWorkSettingRepo;

    @Inject
    private PredetemineTimeSettingRepository predetemineTimeSettingRepo;

    @Inject
    private BasicScheduleService basicScheduleService;

    @Inject
    private EmploymentRepository employmentRepo;

    @Inject
    private ClassificationRepository classificationRepo;

    @Inject
    private JobTitleInfoRepository jobTitleInfoRepo;

    @Inject
    private ExternalBudgetRepository externalBudgetRepo;

    @Inject
    private ExternalBudgetActualResultRepository extBudgetDailyRepository;

    /**
     * 集計する
     * @param targetOrg 対象組織
     * @param workplaceCounterCategories 職場計カテゴリ一覧
     * @param integrationOfDailyMap 日別勤怠リスト
     * @param period 期間
     * @return 職場計集計結果
     */
    public <T> Map<WorkplaceCounterCategory, Map<GeneralDate, T>> get(TargetOrgIdenInfor targetOrg, List<WorkplaceCounterCategory> workplaceCounterCategories, Map<ScheRecGettingAtr, List<IntegrationOfDaily>> integrationOfDailyMap, DatePeriod period) {
        String companyId = AppContexts.user().companyId();
        List<IntegrationOfDaily> integrationOfDailyList = new ArrayList<>();
        integrationOfDailyMap.forEach((scheRecAtr, dailyIntegrations) -> {
            integrationOfDailyList.addAll(dailyIntegrations);
        });
        Map<WorkplaceCounterCategory, Map<GeneralDate, T>> result = new HashMap<>();

        if (workplaceCounterCategories.contains(WorkplaceCounterCategory.LABOR_COSTS_AND_TIME)) {
            // 人件費・時間を集計する
            Optional<WorkplaceCounterLaborCostAndTime> wkpLaborCostAndTime = laborCostAndTimeRepo.get(AppContexts.user().companyId());
            if (wkpLaborCostAndTime.isPresent()) {
                Map<AggregationUnitOfLaborCosts, LaborCostAndTime> targetLaborCost = wkpLaborCostAndTime.get().getLaborCostAndTimeList();
                Map<GeneralDate, Map<LaborCostAggregationUnit, BigDecimal>> laborCostTimeMap = CountLaborCostTimeService.aggregate(
                        new CountLaborCostTimeService.Require() {
                            @Override
                            public List<LaborCostBudget> getLaborCostBudgetList(TargetOrgIdenInfor targetOrg, DatePeriod datePeriod) {
                                return laborCostBudgetRepo.get(companyId, targetOrg, period);
                            }
                        },
                        targetOrg,
                        period,
                        targetLaborCost,
                        integrationOfDailyList
                );
                result.put(WorkplaceCounterCategory.LABOR_COSTS_AND_TIME, (Map<GeneralDate, T>) laborCostTimeMap);
            }
        }

        if (workplaceCounterCategories.contains(WorkplaceCounterCategory.TIMES_COUNTING)) {
            // 回数集計を集計する
            Optional<TimesNumberCounterSelection> counterSelection = timesNumberCounterSelectionRepo.get(companyId, TimesNumberCounterType.WORKPLACE);
            if (counterSelection.isPresent()) {
                List<Integer> totalTimes = totalTimesRepo.getTotalTimesDetailByListNo(companyId, counterSelection.get().getSelectedNoList())
                        .stream().filter(t -> t.getUseAtr() == UseAtr.Use).map(TotalTimes::getTotalCountNo).collect(Collectors.toList());
                Map<GeneralDate, Map<Integer, BigDecimal>> timeCountMap = TotalTimesCounterService.countingNumberOfTotalTimeByDay(
                        new TotalTimesCounterService.Require() {
                            @Override
                            public Optional<WorkType> workType(String companyId, String workTypeCd) {
                                return workTypeRepo.findByPK(companyId, workTypeCd);
                            }
                            @Override
                            public DailyRecordToAttendanceItemConverter createDailyConverter() {
                                return factory.createDailyConverter();
                            }
                            @Override
                            public List<TotalTimes> getTotalTimesList(List<Integer> totalTimeNos) {
                                return totalTimesRepo.getTotalTimesDetailByListNo(companyId, totalTimeNos);
                            }
                        },
                        totalTimes,
                        integrationOfDailyList
                );
                result.put(WorkplaceCounterCategory.TIMES_COUNTING, (Map<GeneralDate, T>) timeCountMap);
            }
        }

        if (workplaceCounterCategories.contains(WorkplaceCounterCategory.EMPLOYMENT_PEOPLE)
                || workplaceCounterCategories.contains(WorkplaceCounterCategory.CLASSIFICATION_PEOPLE)
                || workplaceCounterCategories.contains(WorkplaceCounterCategory.POSITION_PEOPLE)) {
            // 属性ごとに人数を集計する
            CountNumberOfPeopleByAttributeService.Require require = new CountNumberOfPeopleByAttributeService.Require() {
                @Override
                public FixedWorkSetting getWorkSettingForFixedWork(WorkTimeCode code) {
                    return fixedWorkSettingRepo.findByKey(companyId, code.v()).orElse(null);
                }
                @Override
                public FlowWorkSetting getWorkSettingForFlowWork(WorkTimeCode code) {
                    return flowWorkSettingRepo.find(companyId, code.v()).orElse(null);
                }
                @Override
                public FlexWorkSetting getWorkSettingForFlexWork(WorkTimeCode code) {
                    return flexWorkSettingRepo.find(companyId, code.v()).orElse(null);
                }
                @Override
                public PredetemineTimeSetting getPredetermineTimeSetting(WorkTimeCode wktmCd) {
                    return predetemineTimeSettingRepo.findByWorkTimeCode(companyId, wktmCd.v()).orElse(null);
                }
                @Override
                public Optional<WorkType> getWorkType(String workTypeCd) {
                    return workTypeRepo.findByPK(companyId, workTypeCd);
                }
                @Override
                public Optional<WorkTimeSetting> getWorkTime(String workTimeCode) {
                    return workTimeSettingRepo.findByCode(companyId, workTimeCode);
                }
                @Override
                public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
                    return basicScheduleService.checkNeededOfWorkTimeSetting(workTypeCode);
                }
            };
            if (workplaceCounterCategories.contains(WorkplaceCounterCategory.EMPLOYMENT_PEOPLE)) {
                Map<GeneralDate, Map<EmploymentCode, BigDecimal>> countEmploymentMap = CountNumberOfPeopleByAttributeService.countingEachEmployments(require, integrationOfDailyList);
                // 雇用コード（List）から雇用を取得する
                Set<String> employmentCodes = new HashSet<>();
                countEmploymentMap.forEach((key, value) -> {
                    employmentCodes.addAll(value.keySet().stream().map(PrimitiveValueBase::v).collect(Collectors.toSet()));
                });
                Map<String, Employment> employments = employmentRepo.findByEmpCodes(companyId, new ArrayList<>(employmentCodes))
                        .stream().collect(Collectors.toMap(e -> e.getEmploymentCode().v(), e -> e));
                Map<GeneralDate, Map<Employment, BigDecimal>> resultMap = new HashMap<>();
                countEmploymentMap.forEach((key, value) -> {
                    Map<Employment, BigDecimal> convertedValue = new HashMap<>();
                    value.forEach((k, v) -> {
                        convertedValue.put(employments.get(k.v()), v);
                    });
                    resultMap.put(key, convertedValue);
                });
                result.put(WorkplaceCounterCategory.EMPLOYMENT_PEOPLE, (Map<GeneralDate, T>) resultMap);
            }
            if (workplaceCounterCategories.contains(WorkplaceCounterCategory.CLASSIFICATION_PEOPLE)) {
                Map<GeneralDate, Map<ClassificationCode, BigDecimal>> countClassificationMap = CountNumberOfPeopleByAttributeService.countingEachClassification(require, integrationOfDailyList);
                // 分類コード（List）から分類を取得する
                Set<String> classificationCodes = new HashSet<>();
                countClassificationMap.forEach((key, value) -> {
                    classificationCodes.addAll(value.keySet().stream().map(PrimitiveValueBase::v).collect(Collectors.toSet()));
                });
                Map<String, Classification> classifications = classificationRepo.getClassificationByCodes(companyId, new ArrayList<>(classificationCodes))
                        .stream().collect(Collectors.toMap(c -> c.getClassificationCode().v(), c -> c));
                Map<GeneralDate, Map<Classification, BigDecimal>> resultMap = new HashMap<>();
                countClassificationMap.forEach((key, value) -> {
                    Map<Classification, BigDecimal> convertedValue = new HashMap<>();
                    value.forEach((k, v) -> {
                        convertedValue.put(classifications.get(k.v()), v);
                    });
                    resultMap.put(key, convertedValue);
                });
                result.put(WorkplaceCounterCategory.CLASSIFICATION_PEOPLE, (Map<GeneralDate, T>) resultMap);
            }
            if (workplaceCounterCategories.contains(WorkplaceCounterCategory.POSITION_PEOPLE)) {
                Map<GeneralDate, Map<String, BigDecimal>> countJobMap = CountNumberOfPeopleByAttributeService.countingEachJobTitle(require, integrationOfDailyList);
                // 職位IDから職位を取得する
                Set<String> jobIds = new HashSet<>();
                countJobMap.forEach((key, value) -> {
                    jobIds.addAll(value.keySet());
                });
                Map<String, JobTitleInfo> jobTitles = jobTitleInfoRepo.findByIds(companyId, new ArrayList<>(jobIds), period.end())
                        .stream().collect(Collectors.toMap(JobTitleInfo::getJobTitleId, Function.identity()));
                Map<GeneralDate, Map<JobTitleInfo, BigDecimal>> resultMap = new HashMap<>();
                countJobMap.forEach((key, value) -> {
                    Map<JobTitleInfo, BigDecimal> convertedValue = new HashMap<>();
                    value.forEach((k, v) -> {
                        convertedValue.put(jobTitles.get(k), v);
                    });
                    resultMap.put(key, convertedValue);
                });
                result.put(WorkplaceCounterCategory.POSITION_PEOPLE, (Map<GeneralDate, T>) resultMap);
            }
        }

        if (workplaceCounterCategories.contains(WorkplaceCounterCategory.EXTERNAL_BUDGET)) {
            // 外部予算実績を取得する
            List<ExternalBudget> externalBudgets = externalBudgetRepo.findAll(companyId);
            Map<GeneralDate, Map<ExternalBudget, ExternalBudgetValues>> resultMap = new HashMap<>();
            externalBudgets.forEach(item -> {
                List<ExternalBudgetActualResult> extBudgetDailies = extBudgetDailyRepository.getByPeriod(
                        targetOrg,
                        period,
                        item.getExternalBudgetCd()
                );
                resultMap.putAll(extBudgetDailies.stream().collect(Collectors.toMap(e -> e.getYmd(), e -> {
                    Map<ExternalBudget, ExternalBudgetValues> tmp = new HashMap<>();
                    tmp.put(item, e.getActualValue());
                    return tmp;
                })));
            });
            result.put(WorkplaceCounterCategory.EXTERNAL_BUDGET, (Map<GeneralDate, T>) resultMap);
        }

        return result;
    }
}
