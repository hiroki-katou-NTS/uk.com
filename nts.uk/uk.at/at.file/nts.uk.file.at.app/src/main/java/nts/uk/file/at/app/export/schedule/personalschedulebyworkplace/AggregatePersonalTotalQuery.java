package nts.uk.file.at.app.export.schedule.personalschedulebyworkplace;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.DateInMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personcounter.*;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.*;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.PersonalCounterCategory;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.IntegrationOfDailyGetter;
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
    private IntegrationOfDailyGetter integrationOfDailyGetter;
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

    /**
     * 集計する
     * @param employeeIds
     * @param period 期間
     * @param closureDate 締め日
     * @param pesonalTotalCategories 個人計カテゴリ
     * @param integrationOfDailyList List<日別勤怠>
     * @return 個人計集計結果
     */
    public Map<PersonalCounterCategory, Object> get(List<String> employeeIds, DatePeriod period, GeneralDate closureDate, List<PersonalCounterCategory> pesonalTotalCategories, List<IntegrationOfDaily> integrationOfDailyList) {
        String companyId = AppContexts.user().companyId();
        Map<PersonalCounterCategory, Object> result = new HashMap<>();
        //個人計カテゴリ == 労働時間
        if (pesonalTotalCategories.contains(PersonalCounterCategory.WORKING_HOURS)) {
            Map<EmployeeId, Map<AttendanceTimesForAggregation, BigDecimal>> workingHoursMap = WorkingTimeCounterService.get(integrationOfDailyList);
            result.put(PersonalCounterCategory.WORKING_HOURS, workingHoursMap);
        }

        if (pesonalTotalCategories.contains(PersonalCounterCategory.MONTHLY_EXPECTED_SALARY) || pesonalTotalCategories.contains(PersonalCounterCategory.CUMULATIVE_ESTIMATED_SALARY)) {
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
                    List<IntegrationOfDaily> result = new ArrayList<>();
                    empIds.forEach(sid -> {
                        List<IntegrationOfDaily> tmp = integrationOfDailyGetter.getIntegrationOfDaily(sid.v(), period);
                        result.addAll(tmp);
                    });
                    return result;
                }

                @Override
                public List<IntegrationOfDaily> getRecordList(List<EmployeeId> empIds, DatePeriod period) {
                    List<IntegrationOfDaily> result = new ArrayList<>();
                    empIds.forEach(sid -> {
                        List<IntegrationOfDaily> tmp = integrationOfDailyGetter.getIntegrationOfDaily(sid.v(), period);
                        result.addAll(tmp);
                    });
                    return result;
                }
            };
            // 個人計カテゴリ == 月間想定給与額
            if (pesonalTotalCategories.contains(PersonalCounterCategory.MONTHLY_EXPECTED_SALARY)) {
                Map<EmployeeId, EstimatedSalary> monthlyEstimatedSalaryMap = EstimatedSalaryAggregationService.aggregateByMonthly(
                        require,
                        period.end().yearMonth(),
                        new DateInMonth(closureDate.day(), closureDate.equals(closureDate.lastGeneralDate())),
                        integrationOfDailyList
                );
                result.put(PersonalCounterCategory.MONTHLY_EXPECTED_SALARY, monthlyEstimatedSalaryMap);
            }

            // 個人計カテゴリ == 年間想定給与額
            if (pesonalTotalCategories.contains(PersonalCounterCategory.CUMULATIVE_ESTIMATED_SALARY)) {
                Map<EmployeeId, EstimatedSalary> yearlyEstimatedSalaryMap = EstimatedSalaryAggregationService.aggregateByYearly(
                        require,
                        period.end(),
                        employeeIds.stream().map(EmployeeId::new).collect(Collectors.toList())
                );
                result.put(PersonalCounterCategory.CUMULATIVE_ESTIMATED_SALARY, yearlyEstimatedSalaryMap);
            }
        }

        // 個人計カテゴリ == 回数集計１ or 回数集計２ or 回数集計３
        if (pesonalTotalCategories.contains(PersonalCounterCategory.TIMES_COUNTING_1)
                || pesonalTotalCategories.contains(PersonalCounterCategory.TIMES_COUNTING_2)
                || pesonalTotalCategories.contains(PersonalCounterCategory.TIMES_COUNTING_3)) {
            // 回数集計を集計する

        }

        // 個人計カテゴリ == 出勤・休日日数
        if (pesonalTotalCategories.contains(PersonalCounterCategory.ATTENDANCE_HOLIDAY_DAYS)) {
            Map<EmployeeId, Map<WorkClassificationAsAggregationTarget, BigDecimal>> holidayWorkMap = WorkdayHolidayCounterService.count(
                    new WorkdayHolidayCounterService.Require() {
                        @Override
                        public Optional<WorkType> getWorkType(WorkTypeCode workTypeCd) {
                            return workTypeRepo.findByPK(companyId, workTypeCd.v());
                        }
                    },
                    integrationOfDailyList
            );
            result.put(PersonalCounterCategory.ATTENDANCE_HOLIDAY_DAYS, holidayWorkMap);
        }
        return result;
    }
}
