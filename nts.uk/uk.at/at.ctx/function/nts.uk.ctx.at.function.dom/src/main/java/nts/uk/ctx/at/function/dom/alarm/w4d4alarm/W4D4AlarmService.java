package nts.uk.ctx.at.function.dom.alarm.w4d4alarm;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.function.dom.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategoryRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.fourweekfourdayoff.AlarmCheckCondition4W4D;
import nts.uk.ctx.at.function.dom.alarm.alarmdata.ValueExtractAlarm;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.EmployeeSearchDto;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class W4D4AlarmService {
	
	@Inject
	private AlarmCheckConditionByCategoryRepository alarmCheckConditionByCategoryRepository;
	
	@Inject
	private WorkplaceAdapter workplaceAdapter;
		
	public List<ValueExtractAlarm> calculateTotal4W4D(EmployeeSearchDto employee, DatePeriod period, String checkConditionCode) {
		String companyID = AppContexts.user().companyId();
		
		val optAlarmCheckConditionByCategory = alarmCheckConditionByCategoryRepository.find(companyID, AlarmCategory.SCHEDULE_4WEEK.value, checkConditionCode);
		if (!optAlarmCheckConditionByCategory.isPresent())
			throw new RuntimeException("Can't find AlarmCheckConditionByCategory with category: 4W4D and code: " + checkConditionCode);
		
		// TODO: Narrow down the target audience
		
		// Acquire company employee's work place history
		val workplaceImport = workplaceAdapter.getWorlkplaceHistory(employee.getId(), period.end());
		
		val alarmCheckConditionByCategory = optAlarmCheckConditionByCategory.get();
		AlarmCheckCondition4W4D fourW4DCheckCond = (AlarmCheckCondition4W4D) alarmCheckConditionByCategory.getExtractionCondition();
		if (fourW4DCheckCond.isForActualResultsOnly()) {
			this.checkWithActualResults(employee, period);
		}
		
		return null;
	}
	
	public void checkWithActualResults(EmployeeSearchDto employee, DatePeriod period) {
		
	}
}