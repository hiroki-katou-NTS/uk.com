package nts.uk.ctx.at.function.dom.alarm.alarmlist.aggregationprocess;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.function.dom.adapter.workplace.WorkplaceImport;
import nts.uk.ctx.at.function.dom.alarm.alarmdata.ValueExtractAlarm;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.EmployeeSearchDto;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.PeriodByAlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.aggregationprocess.daily.dailyaggregationprocess.DailyAggregationProcessService;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.CheckCondition;
import nts.uk.ctx.at.function.dom.alarm.w4d4alarm.W4D4AlarmService;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class ExtractAlarmForEmployeeService {
	
	@Inject 
	private DailyAggregationProcessService dailyAggregationProcessService;
	
	@Inject
	private W4D4AlarmService w4D4AlarmService;
		
	@Inject
	private WorkplaceAdapter workplaceAdapter;
	
	public List<ValueExtractAlarm> process(List<CheckCondition> checkConList, List<PeriodByAlarmCategory> listPeriodByCategory, EmployeeSearchDto employee){
		
		List<ValueExtractAlarm> result = new ArrayList<>();
		List<Integer> listCategory = listPeriodByCategory.stream().map( x->x.getCategory()).collect(Collectors.toList());
		checkConList.removeIf( e->!listCategory.contains(e.getAlarmCategory().value) || e.isMonthly());
		
		// 次のチェック条件コードで集計する(loop list by category)
		for (CheckCondition checkCondition : checkConList) {
			// get Period by category
			Optional<PeriodByAlarmCategory> optPeriodByAlarmCategory = listPeriodByCategory.stream()
					.filter(c -> c.getCategory() == checkCondition.getAlarmCategory().value).findFirst();
			if (!optPeriodByAlarmCategory.isPresent())
				throw new RuntimeException("Can't find category: " + checkCondition.getAlarmCategory().value + " from UI");
			PeriodByAlarmCategory periodByAlarmCategory = optPeriodByAlarmCategory.get();
			DatePeriod period = new DatePeriod(periodByAlarmCategory.getStartDate(), periodByAlarmCategory.getEndDate());
			
			Optional<WorkplaceImport>  optWorkplaceImport =workplaceAdapter.getWorlkplaceHistory(employee.getId(), periodByAlarmCategory.getEndDate());
			
			if(optWorkplaceImport.isPresent()) {
				WorkplaceImport workplaceImport =optWorkplaceImport.get();
				employee.setWorkplaceId(workplaceImport.getWorkplaceId());
				employee.setWorkplaceCode(workplaceImport.getWorkplaceCode());
				employee.setWorkplaceName(workplaceImport.getWorkplaceName());
			}else {
				employee.setWorkplaceId(null);
				employee.setWorkplaceCode(null);
				employee.setWorkplaceName(null);
			}
			
			// カテゴリ：日次のチェック条件(daily)
			if (checkCondition.isDaily()) {
				for (String checkConditionCode : checkCondition.getCheckConditionList()) {						
					// アルゴリズム「日次の集計処理」を実行する
					List<ValueExtractAlarm> dailyAlarmList = dailyAggregationProcessService.dailyAggregationProcess(checkConditionCode, periodByAlarmCategory, employee);
					result.addAll(dailyAlarmList);
				}
			}
			// カテゴリ：4週4休のチェック条件(4 week 4 day)
			else if (checkCondition.is4W4D()) {
				// アルゴリズム「4週4休の集計処理」を実行する
				for (String checkConditionCode : checkCondition.getCheckConditionList()) {
					List<ValueExtractAlarm> w4d4AlarmList = w4D4AlarmService.calculateTotal4W4D(employee, period, checkConditionCode);
					result.addAll(w4d4AlarmList);
				}
			}
			// カテゴリ：月次のチェック条件 (monthly)
			else if (checkCondition.isMonthly()) {
				// tạm thời chưa làm
			}
			


		}
		return result;
	}
	
	public List<ValueExtractAlarm> process(String comId, List<CheckCondition> checkConList, List<PeriodByAlarmCategory> listPeriodByCategory, List<EmployeeSearchDto> employee){
		
		List<ValueExtractAlarm> result = new ArrayList<>();
		List<Integer> listCategory = listPeriodByCategory.stream().map( x->x.getCategory()).collect(Collectors.toList());
		checkConList.removeIf( e->!listCategory.contains(e.getAlarmCategory().value) || e.isMonthly());
		List<String> employeeIds = employee.stream().map(c -> c.getId()).collect(Collectors.toList());
		// 次のチェック条件コードで集計する(loop list by category)
		for (CheckCondition checkCondition : checkConList) {
			// get Period by category
			listPeriodByCategory.stream().filter(c -> c.getCategory() == checkCondition.getAlarmCategory().value).findFirst().ifPresent(periodByAlarmCategory -> {
				DatePeriod period = new DatePeriod(periodByAlarmCategory.getStartDate(), periodByAlarmCategory.getEndDate());
				
				List<WorkplaceImport>  optWorkplaceImport = workplaceAdapter.getWorlkplaceHistory(employeeIds, periodByAlarmCategory.getEndDate());
				
				optWorkplaceImport.stream().forEach(wp -> {
					employee.stream().filter(e -> e.getId().equals(wp.getEmployeeId())).findFirst().ifPresent(e -> {
						e.setWorkplaceId(wp.getWorkplaceId());
						e.setWorkplaceCode(wp.getWorkplaceCode());
						e.setWorkplaceName(wp.getWorkplaceName());
					});
				});
				
				// カテゴリ：日次のチェック条件(daily)
				if (checkCondition.isDaily()) {
					for (String checkConditionCode : checkCondition.getCheckConditionList()) {						
						// アルゴリズム「日次の集計処理」を実行する
						result.addAll(dailyAggregationProcessService.dailyAggregationProcess(comId, checkConditionCode, 
								periodByAlarmCategory, employee, period, employeeIds));
					}
				}
				// カテゴリ：4週4休のチェック条件(4 week 4 day)
				else if (checkCondition.is4W4D()) {
					// アルゴリズム「4週4休の集計処理」を実行する
					for (String checkConditionCode : checkCondition.getCheckConditionList()) {
						List<ValueExtractAlarm> w4d4AlarmList = w4D4AlarmService.calculateTotal4W4D(employee, period, checkConditionCode, employeeIds);
						result.addAll(w4d4AlarmList);
					}
				}
				// カテゴリ：月次のチェック条件 (monthly)
				else if (checkCondition.isMonthly()) {
					// tạm thời chưa làm
				}
			});
		}
		return result;
	}
}
