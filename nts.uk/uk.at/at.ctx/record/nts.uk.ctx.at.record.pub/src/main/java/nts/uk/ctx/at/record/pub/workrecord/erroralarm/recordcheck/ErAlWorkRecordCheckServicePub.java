package nts.uk.ctx.at.record.pub.workrecord.erroralarm.recordcheck;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.query.employee.RegulationEmployeeInfoR;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface ErAlWorkRecordCheckServicePub {

	public List<RegulationInfoEmployeeQueryResult> filterEmployees(GeneralDate workingDate,
			Collection<String> employeeIds, String EACheckID);

	public Map<String, List<RegulationInfoEmployeeQueryResult>> filterEmployees(GeneralDate workingDate,
			Collection<String> employeeIds, List<String> EACheckIDs);

	public Map<String, Map<String, Boolean>> check(GeneralDate workingDate, Collection<String> employeeIds,
			List<String> EACheckIDs);
	
	public List<ErrorRecordExport> check(List<String> EACheckIDs, DatePeriod workingDate, Collection<String> employeeIds);
	
	public List<ErrorRecordExport> check(List<String> EACheckIDs, GeneralDate workingDate, Collection<String> employeeIds);

	public List<RegulationInfoEmployeeQueryResult> filterEmployees(GeneralDate workingDate,
			Collection<String> employeeIds, ErAlSubjectFilterConditionDto condition);
	//
	public Map<String, List<RegulationEmployeeInfoR>> filterEmployees(DatePeriod targetPeriod,
			Collection<String> employeeIds, List<ErAlSubjectFilterConditionDto> conditions);

	public Map<ErAlSubjectFilterConditionDto, List<RegulationInfoEmployeeQueryResult>> filterEmployees(Collection<String> employeeIds,
			List<ErAlSubjectFilterConditionDto> condition, GeneralDate workingDate);
	
	public class ErrorRecordExport {
		private GeneralDate date;
		private String employeeId;
		private String erAlId;
		private final boolean error = true;
		
		public ErrorRecordExport(GeneralDate date, String employeeId, String erAlId) {
			super();
			this.date = date;
			this.employeeId = employeeId;
			this.erAlId = erAlId;
		}

		public GeneralDate getDate() {
			return date;
		}

		public String getEmployeeId() {
			return employeeId;
		}

		public String getErAlId() {
			return erAlId;
		}

		public boolean isError() {
			return error;
		}
	}
}
