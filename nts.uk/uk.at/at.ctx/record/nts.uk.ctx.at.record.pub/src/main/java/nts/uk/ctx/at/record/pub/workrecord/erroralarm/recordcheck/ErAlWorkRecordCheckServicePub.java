package nts.uk.ctx.at.record.pub.workrecord.erroralarm.recordcheck;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import nts.arc.time.GeneralDate;

public interface ErAlWorkRecordCheckServicePub {

	public List<RegulationInfoEmployeeQueryResult> filterEmployees(GeneralDate workingDate,
			Collection<String> employeeIds, String EACheckID);
	
	public Map<String, List<RegulationInfoEmployeeQueryResult>> filterEmployees(GeneralDate workingDate,
			Collection<String> employeeIds, List<String> EACheckIDs);

	public Map<String, Boolean> check(GeneralDate workingDate, Collection<String> employeeIds, String EACheckID);

	public Map<String, Map<String, Boolean>> check(GeneralDate workingDate, Collection<String> employeeIds, List<String> EACheckIDs);
}
