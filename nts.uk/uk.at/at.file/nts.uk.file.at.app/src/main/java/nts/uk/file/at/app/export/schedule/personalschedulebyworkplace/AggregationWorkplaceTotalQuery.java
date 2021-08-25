package nts.uk.file.at.app.export.schedule.personalschedulebyworkplace;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.aggregation.dom.common.ScheRecGettingAtr;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.TotalTimesCounterService;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.workplacecounter.CountLaborCostTimeService;
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
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.AggregationUnitOfLaborCosts;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.service.AttendanceItemConvertFactory;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimes;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesRepository;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.UseAtr;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 職場計を集計する
 */
@Stateless
public class AggregationWorkplaceTotalQuery {
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
            Optional<WorkplaceCounterLaborCostAndTime> wkpLaborCostAndTime = laborCostAndTimeRepo.get(AppContexts.user().companyId());
            if (wkpLaborCostAndTime.isPresent()) {
                Map<AggregationUnitOfLaborCosts, LaborCostAndTime> targetLaborCost = new HashMap<>();
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

        }

        if (workplaceCounterCategories.contains(WorkplaceCounterCategory.EXTERNAL_BUDGET)) {

        }

        return result;
    }
}
