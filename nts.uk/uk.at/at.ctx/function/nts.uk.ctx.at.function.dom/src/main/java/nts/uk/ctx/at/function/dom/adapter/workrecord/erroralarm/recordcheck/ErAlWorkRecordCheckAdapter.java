package nts.uk.ctx.at.function.dom.adapter.workrecord.erroralarm.recordcheck;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckTargetCondition;

public interface ErAlWorkRecordCheckAdapter {
	
	public Map<String, Map<String, Boolean>> check(GeneralDate workingDate, Collection<String> employeeIds, List<String> EACheckIDs);
	
	public Map<String, List<RegulationInfoEmployeeResult>> filterEmployees(GeneralDate workingDate,
			Collection<String> employeeIds, List<String> EACheckIDs);
	
	public List<RegulationInfoEmployeeResult> filterEmployees(GeneralDate workingDate,
			Collection<String> employeeIds, AlarmCheckTargetCondition condition);

}
