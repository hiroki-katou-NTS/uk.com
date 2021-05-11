package nts.uk.screen.at.app.ksu001.aggreratepersonaltotal;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import mockit.Injectable;
import nts.arc.time.calendar.DateInMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personcounter.EstimatedSalary;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personcounter.EstimatedSalaryAggregationService;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personcounter.WorkClassificationAsAggregationTarget;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personcounter.WorkdayHolidayCounterService;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personcounter.WorkingTimeCounterService;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.PersonalCounterCategory;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.AttendanceTimesForAggregation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimes;

/**
 * 個人計を集計する
 * @author hoangnd
 *
 */

@Stateless
public class ScreenQueryAggreratePersonal {

	@Inject 
	private ScreenQueryAggrerateNumberTimePs screenQueryAggrerateNumberTime;
	
	@Injectable
	EstimatedSalaryAggregationService.Require require;
	
	public AggreratePersonalDto aggreratePersonal(
			PersonalCounterCategory personalCounter, // 個人計カテゴリ
			List<IntegrationOfDaily> aggrerateintegrationOfDaily, // 日別勤怠リスト
			DatePeriod datePeriod, // 期間
			DateInMonth closeDate // 締め日
			) {
		AggreratePersonalDto output = new AggreratePersonalDto();
		if (personalCounter == PersonalCounterCategory.WORKING_HOURS) {
			// 1: 集計する(List<日別勤怠(Work)>)
			Map<EmployeeId, Map<AttendanceTimesForAggregation, BigDecimal>> dailyWorks = WorkingTimeCounterService.get(aggrerateintegrationOfDaily);
			
			Map<String, Map<Integer, BigDecimal>> timeCount = dailyWorks.entrySet()
					  .stream()
					  .collect(Collectors.toMap(
							  e -> ((EmployeeId)e.getKey()).v(),
							  e -> (HashMap<Integer, BigDecimal>)e.getValue()
							  	.entrySet()
							  	.stream()
							  	.collect(Collectors.toMap(x -> ((AttendanceTimesForAggregation)x.getKey()).getValue(),
							  			x -> (BigDecimal)x.getValue())))
							  );
			output.timeCount = timeCount;
			
		}
	
		if (personalCounter == PersonalCounterCategory.MONTHLY_EXPECTED_SALARY) {
			
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
												e -> ((EmployeeId)e.getKey()).v(),
												e -> EstimatedSalaryDto.fromDomain(((EstimatedSalary)e.getValue()))));
			
		}
		if (personalCounter == PersonalCounterCategory.CUMULATIVE_ESTIMATED_SALARY) { // 個人計カテゴリ == 年間想定給与額
			
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
							e -> ((EmployeeId)e.getKey()).v(),
							e -> EstimatedSalaryDto.fromDomain(((EstimatedSalary)e.getValue()))));
		}
		
		// 4: 個人計カテゴリ == 回数集計１ or 回数集計２ or 回数集計３
		if (personalCounter == PersonalCounterCategory.TIMES_COUNTING_1
				|| personalCounter == PersonalCounterCategory.TIMES_COUNTING_2
				|| personalCounter == PersonalCounterCategory.TIMES_COUNTING_3) {
			
			// 回数集計を集計する
			Map<EmployeeId, Map<TotalTimes, BigDecimal>> timeCount = screenQueryAggrerateNumberTime.aggrerate(personalCounter, aggrerateintegrationOfDaily);
			
		}
		
		// 5: 個人計カテゴリ == 出勤・休日日数
		if (personalCounter == PersonalCounterCategory.ATTENDANCE_HOLIDAY_DAYS) {
			
			// 集計する(Require, List<日別勤怠(Work)>)
			Map<EmployeeId, Map<WorkClassificationAsAggregationTarget, BigDecimal>> attendenceHoliday = WorkdayHolidayCounterService.count(null, aggrerateintegrationOfDaily);
		}
		
		return output;
		
	}
}
