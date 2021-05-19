package nts.uk.screen.at.app.ksu001.aggreratepersonaltotal;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.DateInMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personcounter.EstimatedSalary;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personcounter.EstimatedSalaryAggregationService;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personcounter.WorkdayHolidayCounterService;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personcounter.WorkingTimeCounterService;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForCompany;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForEmployment;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountUsageSetting;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.HandlingOfCriterionAmount;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.PersonalCounterCategory;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkScheduleRepository;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.AttendanceTimesForAggregation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentPeriodImported;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * 個人計を集計する
 * @author hoangnd
 *
 */

@Stateless
public class ScreenQueryAggreratePersonal {

	@Inject 
	private ScreenQueryAggrerateNumberTimePs screenQueryAggrerateNumberTime;
	
	
	public AggreratePersonalDto aggreratePersonal(
			PersonalCounterCategory personalCounter, // 個人計カテゴリ
			List<IntegrationOfDaily> aggrerateintegrationOfDaily, // 日別勤怠リスト
			DatePeriod datePeriod, // 期間
			DateInMonth closeDate // 締め日
			) {
		
		Require require = new Require(null);
		
		Require2 require2 = new Require2();
		
		AggreratePersonalDto output = new AggreratePersonalDto();
		if (personalCounter == PersonalCounterCategory.WORKING_HOURS) {
			// 1: 集計する(List<日別勤怠(Work)>)
			Map<EmployeeId, Map<AttendanceTimesForAggregation, BigDecimal>> dailyWorks = 
					WorkingTimeCounterService.get(aggrerateintegrationOfDaily);
			
			Map<String, Map<Integer, BigDecimal>> timeCount = dailyWorks.entrySet()
					  .stream()
					  .collect(Collectors.toMap(
							  e -> e.getKey().v(),
							  e -> e.getValue()
							  	.entrySet()
							  	.stream()
							  	.collect(Collectors.toMap(
							  			x -> x.getKey().getValue(),
							  			x -> x.getValue())))
							  );
			output.setTimeCount(timeCount);
			
		}
	
		else if (personalCounter == PersonalCounterCategory.MONTHLY_EXPECTED_SALARY) {
			
			// 2: 月間想定給与額を集計する(Require, 年月, 日付, List<日別勤怠(Work)>)
			Map<EmployeeId, EstimatedSalary> etimatedMonthSalarys = EstimatedSalaryAggregationService.aggregateByMonthly(
					require,
					null,
					closeDate,
					aggrerateintegrationOfDaily
					);
			output.estimatedSalary = etimatedMonthSalarys.entrySet()
										.stream()
										.collect(Collectors.toMap(
												e -> e.getKey().v(),
												e -> EstimatedSalaryDto.fromDomain(e.getValue())));
			
		}
		else if (personalCounter == PersonalCounterCategory.CUMULATIVE_ESTIMATED_SALARY) { // 個人計カテゴリ == 年間想定給与額
			
			// 3: 年間想定給与額を集計する(Require, 年月日, List<社員ID>)
			Map<EmployeeId, EstimatedSalary> etimatedYearSalarys = EstimatedSalaryAggregationService.aggregateByYearly(
					require, // require
					datePeriod.end(), // 基準日 = Input.期間.終了日
					aggrerateintegrationOfDaily.stream() // 社員IDリスト = Input.日別勤怠リスト : map $.社員ID distinct
					.map(x -> x.getEmployeeId())
					.distinct()
					.map(x -> new EmployeeId(x))
					.collect(Collectors.toList())
					); 
			
			output.estimatedSalary = etimatedYearSalarys.entrySet()
					.stream()
					.collect(Collectors.toMap(
							e -> e.getKey().v(),
							e -> EstimatedSalaryDto.fromDomain(e.getValue())));
		}
		
		// 4: 個人計カテゴリ == 回数集計１ or 回数集計２ or 回数集計３
		else if (personalCounter == PersonalCounterCategory.TIMES_COUNTING_1
				|| personalCounter == PersonalCounterCategory.TIMES_COUNTING_2
				|| personalCounter == PersonalCounterCategory.TIMES_COUNTING_3) {
			
			// 回数集計を集計する
			Map<String, Map<Integer, BigDecimal>> timeCount = 
					screenQueryAggrerateNumberTime.aggrerate(
							personalCounter,
							aggrerateintegrationOfDaily)
					.entrySet()
					.stream()
					.collect(
							Collectors.toMap(
							e -> e.getKey().v(),
							e -> e.getValue().entrySet()
											 .stream()
											 .collect(
												 Collectors.toMap(
														 f -> f.getKey().getTotalCountNo(),
														 f -> f.getValue())
											 )
							 )
					);
			output.setTimeCount(timeCount);
		}
		
		// 5: 個人計カテゴリ == 出勤・休日日数
		else if (personalCounter == PersonalCounterCategory.ATTENDANCE_HOLIDAY_DAYS) {
			
			// 集計する(Require, List<日別勤怠(Work)>)
			Map<String, Map<Integer, BigDecimal>> attendenceHoliday = 
					WorkdayHolidayCounterService.count(
							require2,
							aggrerateintegrationOfDaily)
					.entrySet()
					.stream()
					.collect(
							Collectors.toMap(
							e -> e.getKey().v(),
							e -> e.getValue().entrySet()
											 .stream()
											 .collect(
												 Collectors.toMap(
														 f -> f.getKey().value,
														 f -> f.getValue())
											 )
							 )
					);
			output.setTimeCount(attendenceHoliday);
		}
		
		return output;
		
	}
	
	@AllArgsConstructor
	private static class Require2 implements WorkdayHolidayCounterService.Require {

		@Override
		public Optional<WorkType> getWorkType(WorkTypeCode workTypeCd) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	@AllArgsConstructor
	private static class Require implements EstimatedSalaryAggregationService.Require {

		@Inject
		private WorkScheduleRepository workScheduleRepository;
		
		@Override
		public List<IntegrationOfDaily> getSchduleList(List<EmployeeId> empIds, DatePeriod period) {
			
			return workScheduleRepository.getList(
											empIds.stream()
													.map(x -> x.v())
													.collect(Collectors.toList()),
											period)
										 .stream()
										 .map(WorkSchedule::convertToIntegrationOfDaily)
										 .collect(Collectors.toList());
		}

		@Override
		public List<IntegrationOfDaily> getRecordList(List<EmployeeId> empIds, DatePeriod period) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<CriterionAmountUsageSetting> getUsageSetting() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<CriterionAmountForCompany> getCriterionAmountForCompany() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<CriterionAmountForEmployment> getCriterionAmountForEmployment(EmploymentCode employmentCode) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<EmploymentPeriodImported> getEmploymentHistory(EmployeeId empId, GeneralDate date) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<HandlingOfCriterionAmount> getHandling() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
