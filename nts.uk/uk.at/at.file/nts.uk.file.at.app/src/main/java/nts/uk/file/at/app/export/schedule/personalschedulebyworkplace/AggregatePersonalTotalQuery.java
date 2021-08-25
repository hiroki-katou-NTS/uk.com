package nts.uk.file.at.app.export.schedule.personalschedulebyworkplace;

import nts.arc.primitive.PrimitiveValueBase;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.DateInMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.aggregation.dom.adapter.dailyrecord.DailyRecordAdapter;
import nts.uk.ctx.at.aggregation.dom.adapter.workschedule.WorkScheduleAdapter;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personcounter.*;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.*;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.PersonalCounterCategory;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.AttendanceTimesForAggregation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentHisAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentPeriodImported;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 個人計を集計する
 */
@Stateless
public class AggregatePersonalTotalQuery {
    @Inject
    private WorkScheduleAdapter workScheduleAdapter;
    @Inject
    private DailyRecordAdapter dailyRecordAdapter;
    @Inject
    private HandlingOfCriterionAmountRepository handlingOfCriterionAmountRepo;
    @Inject
    private CriterionAmountUsageSettingRepository criterionAmountUsageSettingRepo;
    @Inject
    private CriterionAmountForCompanyRepository criterionAmountForCompanyRepo;
    @Inject
    private CriterionAmountForEmploymentRepository criterionAmountForEmploymentRepo;
    @Inject
    private EmploymentHisAdapter employmentHisAdapter;
    @Inject
    private WorkTypeRepository workTypeRepo;

    @Inject
    private AggregateNumberOfTimesQuery aggregateNumberOfTimesQuery;

    /**
     * 集計する
     * @param employeeIds
     * @param period 期間
     * @param closureDate 締め日
     * @param personalCounterCategories 個人計カテゴリ
     * @param integrationOfDailyList List<日別勤怠>
     * @return 個人計集計結果
     */
    public <T> Map<PersonalCounterCategory, Map<String, T>> get(List<String> employeeIds, DatePeriod period, GeneralDate closureDate, List<PersonalCounterCategory> personalCounterCategories, List<IntegrationOfDaily> integrationOfDailyList) {
        String companyId = AppContexts.user().companyId();
        Map<PersonalCounterCategory, Map<String, T>> result = new HashMap<>();
        //個人計カテゴリ == 労働時間
        if (personalCounterCategories.contains(PersonalCounterCategory.WORKING_HOURS)) {
            Map<EmployeeId, Map<AttendanceTimesForAggregation, BigDecimal>> workingHoursMap = WorkingTimeCounterService.get(integrationOfDailyList);
            result.put(PersonalCounterCategory.WORKING_HOURS, (Map<String, T>) workingHoursMap.entrySet().stream().collect(Collectors.toMap((e) -> e.getKey().v(), (e) -> e.getValue())));
        }

        if (personalCounterCategories.contains(PersonalCounterCategory.MONTHLY_EXPECTED_SALARY) || personalCounterCategories.contains(PersonalCounterCategory.CUMULATIVE_ESTIMATED_SALARY)) {
            EstimatedSalaryAggregationService.Require require = new EstimatedSalaryAggregationService.Require() {
                @Override
                public Optional<HandlingOfCriterionAmount> getHandling() {
                    return handlingOfCriterionAmountRepo.get(companyId);
                }
                @Override
                public Optional<CriterionAmountUsageSetting> getUsageSetting() {
                    return criterionAmountUsageSettingRepo.get(companyId);
                }
                @Override
                public Optional<CriterionAmountForCompany> getCriterionAmountForCompany() {
                    return criterionAmountForCompanyRepo.get(companyId);
                }
                @Override
                public Optional<CriterionAmountForEmployment> getCriterionAmountForEmployment(EmploymentCode employmentCode) {
                    return criterionAmountForEmploymentRepo.get(companyId, employmentCode);
                }
                @Override
                public Optional<EmploymentPeriodImported> getEmploymentHistory(EmployeeId empId, GeneralDate date) {
                    List<EmploymentPeriodImported> tmp = employmentHisAdapter.getEmploymentPeriod(Arrays.asList(empId.v()), DatePeriod.oneDay(date));
                    return tmp.stream().findFirst();
                }
                @Override
                public List<IntegrationOfDaily> getSchduleList(List<EmployeeId> empIds, DatePeriod period) {
                    return workScheduleAdapter.getList(empIds.stream().map(PrimitiveValueBase::v).collect(Collectors.toList()), period);
                }
                @Override
                public List<IntegrationOfDaily> getRecordList(List<EmployeeId> empIds, DatePeriod period) {
                    return dailyRecordAdapter.getDailyRecordByScheduleManagement(empIds.stream().map(PrimitiveValueBase::v).collect(Collectors.toList()), period);
                }
            };
            // 個人計カテゴリ == 月間想定給与額
            if (personalCounterCategories.contains(PersonalCounterCategory.MONTHLY_EXPECTED_SALARY)) {
                Map<EmployeeId, EstimatedSalary> monthlyEstimatedSalaryMap = EstimatedSalaryAggregationService.aggregateByMonthly(
                        require,
                        period.end().yearMonth(),
                        new DateInMonth(closureDate.day(), closureDate.equals(closureDate.lastGeneralDate())),
                        integrationOfDailyList
                );
                result.put(PersonalCounterCategory.MONTHLY_EXPECTED_SALARY, (Map<String, T>) monthlyEstimatedSalaryMap.entrySet().stream().collect(Collectors.toMap((e) -> e.getKey().v(), (e) -> e.getValue())));
            }

            // 個人計カテゴリ == 年間想定給与額
            if (personalCounterCategories.contains(PersonalCounterCategory.CUMULATIVE_ESTIMATED_SALARY)) {
                Map<EmployeeId, EstimatedSalary> yearlyEstimatedSalaryMap = EstimatedSalaryAggregationService.aggregateByYearly(
                        require,
                        period.end(),
                        employeeIds.stream().map(EmployeeId::new).collect(Collectors.toList())
                );
                result.put(PersonalCounterCategory.CUMULATIVE_ESTIMATED_SALARY, (Map<String, T>) yearlyEstimatedSalaryMap.entrySet().stream().collect(Collectors.toMap((e) -> e.getKey().v(), (e) -> e.getValue())));
            }
        }

        // 個人計カテゴリ == 回数集計１ or 回数集計２ or 回数集計３
        if (personalCounterCategories.contains(PersonalCounterCategory.TIMES_COUNTING_1)) {
            // 回数集計を集計する
            Map<String, Map<Integer, BigDecimal>> timesCountMap = aggregateNumberOfTimesQuery.get(PersonalCounterCategory.TIMES_COUNTING_1, integrationOfDailyList);
            result.put(PersonalCounterCategory.TIMES_COUNTING_1, (Map<String, T>) timesCountMap);
        }
        if (personalCounterCategories.contains(PersonalCounterCategory.TIMES_COUNTING_2)) {
            // 回数集計を集計する
            Map<String, Map<Integer, BigDecimal>> timesCountMap = aggregateNumberOfTimesQuery.get(PersonalCounterCategory.TIMES_COUNTING_2, integrationOfDailyList);
            result.put(PersonalCounterCategory.TIMES_COUNTING_2, (Map<String, T>) timesCountMap);
        }
        if (personalCounterCategories.contains(PersonalCounterCategory.TIMES_COUNTING_3)) {
            // 回数集計を集計する
            Map<String, Map<Integer, BigDecimal>> timesCountMap = aggregateNumberOfTimesQuery.get(PersonalCounterCategory.TIMES_COUNTING_3, integrationOfDailyList);
            result.put(PersonalCounterCategory.TIMES_COUNTING_3, (Map<String, T>) timesCountMap);
        }

        // 個人計カテゴリ == 出勤・休日日数
        if (personalCounterCategories.contains(PersonalCounterCategory.ATTENDANCE_HOLIDAY_DAYS)) {
            Map<EmployeeId, Map<WorkClassificationAsAggregationTarget, BigDecimal>> holidayWorkMap = WorkdayHolidayCounterService.count(
                    new WorkdayHolidayCounterService.Require() {
                        @Override
                        public Optional<WorkType> getWorkType(WorkTypeCode workTypeCd) {
                            return workTypeRepo.findByPK(companyId, workTypeCd.v());
                        }
                    },
                    integrationOfDailyList
            );
            result.put(PersonalCounterCategory.ATTENDANCE_HOLIDAY_DAYS, (Map<String, T>) holidayWorkMap.entrySet().stream().collect(Collectors.toMap((e) -> e.getKey().v(), (e) -> e.getValue())));
        }
        return result;
    }
}
