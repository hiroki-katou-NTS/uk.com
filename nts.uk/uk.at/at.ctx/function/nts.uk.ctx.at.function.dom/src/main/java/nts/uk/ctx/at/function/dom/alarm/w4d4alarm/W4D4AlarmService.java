package nts.uk.ctx.at.function.dom.alarm.w4d4alarm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.workrecord.alarmlist.fourweekfourdayoff.W4D4CheckAdapter;
import nts.uk.ctx.at.function.dom.adapter.workrecord.erroralarm.recordcheck.ErAlWorkRecordCheckAdapter;
import nts.uk.ctx.at.function.dom.adapter.workrecord.erroralarm.recordcheck.RegulationInfoEmployeeResult;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.alarmdata.ValueExtractAlarm;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.EmployeeSearchDto;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategoryRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.fourweekfourdayoff.AlarmCheckCondition4W4D;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class W4D4AlarmService {
	
	@Inject
	private AlarmCheckConditionByCategoryRepository alarmCheckConditionByCategoryRepository;	
	
	@Inject
	private W4D4CheckAdapter w4D4CheckAdapter;
	
	@Inject
	private ErAlWorkRecordCheckAdapter erAlWorkRecordCheckAdapter;
		
	public List<ValueExtractAlarm> calculateTotal4W4D(EmployeeSearchDto employee, DatePeriod period, String checkConditionCode) {
		String companyID = AppContexts.user().companyId();
		List<ValueExtractAlarm> result = new ArrayList<ValueExtractAlarm>();
		
		Optional<AlarmCheckConditionByCategory> optAlarmCheckConditionByCategory = alarmCheckConditionByCategoryRepository.find(companyID, AlarmCategory.SCHEDULE_4WEEK.value, checkConditionCode);
		if (!optAlarmCheckConditionByCategory.isPresent())
			throw new RuntimeException("Can't find AlarmCheckConditionByCategory with category: 4W4D and code: " + checkConditionCode);
		
		// TODO: Narrow down the target audience
		List<RegulationInfoEmployeeResult> listTarget = erAlWorkRecordCheckAdapter.filterEmployees(period.end(), Arrays.asList(employee.getId()), optAlarmCheckConditionByCategory.get().getExtractTargetCondition());
		if(!listTarget.isEmpty()) {
			for(RegulationInfoEmployeeResult target : listTarget) {
				if(target.getEmployeeId().equals(employee.getId())) {
					AlarmCheckConditionByCategory alarmCheckConditionByCategory = optAlarmCheckConditionByCategory.get();
					AlarmCheckCondition4W4D fourW4DCheckCond = (AlarmCheckCondition4W4D) alarmCheckConditionByCategory.getExtractionCondition();
					
					if (fourW4DCheckCond.isForActualResultsOnly()) {
						Optional<ValueExtractAlarm> optAlarm = this.checkWithActualResults(employee, period);
						if (optAlarm.isPresent())
							result.add(optAlarm.get());
					}
					break;
				}
			}
		}
	
		return result;

	}
	
	public List<ValueExtractAlarm> calculateTotal4W4D(List<EmployeeSearchDto> employees, DatePeriod period, String checkConditionCode) {
		
		List<String> empIds = employees.stream().map( e->e.getId()).collect(Collectors.toList());
		
		String companyID = AppContexts.user().companyId();
		List<ValueExtractAlarm> result = new ArrayList<ValueExtractAlarm>();
		
		Optional<AlarmCheckConditionByCategory> optAlarmCheckConditionByCategory = alarmCheckConditionByCategoryRepository.find(companyID, AlarmCategory.SCHEDULE_4WEEK.value, checkConditionCode);
		if (!optAlarmCheckConditionByCategory.isPresent())
			throw new RuntimeException("Can't find AlarmCheckConditionByCategory with category: 4W4D and code: " + checkConditionCode);
		
		// TODO: Narrow down the target audience
		List<RegulationInfoEmployeeResult> listTarget = erAlWorkRecordCheckAdapter.filterEmployees(period.end(), empIds, optAlarmCheckConditionByCategory.get().getExtractTargetCondition());
		if(!listTarget.isEmpty()) {
			for(RegulationInfoEmployeeResult target : listTarget) {
				Optional<EmployeeSearchDto> emOp = employees.stream().filter(e -> e.getId().equals(target.getEmployeeId())).findFirst();
				if(emOp.isPresent()) {
					AlarmCheckConditionByCategory alarmCheckConditionByCategory = optAlarmCheckConditionByCategory.get();
					AlarmCheckCondition4W4D fourW4DCheckCond = (AlarmCheckCondition4W4D) alarmCheckConditionByCategory.getExtractionCondition();
					
					if (fourW4DCheckCond.isForActualResultsOnly()) {
						Optional<ValueExtractAlarm> optAlarm = this.checkWithActualResults(emOp.get(), period);
						if (optAlarm.isPresent())
							result.add(optAlarm.get());
					}
					
				}
			}
		}
	
		return result;

	}
	
	public Optional<ValueExtractAlarm> checkWithActualResults(EmployeeSearchDto employee, DatePeriod period) {
		return w4D4CheckAdapter.checkHoliday(employee.getWorkplaceId(), employee.getId(), period);
	}
}