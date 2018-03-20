package nts.uk.ctx.at.record.pubimp.workrecord.erroralarm.recordcheck;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.service.workrecord.erroralarm.recordcheck.ErAlWorkRecordCheckService;
import nts.uk.ctx.at.record.dom.adapter.query.employee.RegulationInfoEmployeeQueryR;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.recordcheck.RegulationInfoEmployeeQueryResult;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.recordcheck.ErAlWorkRecordCheckServicePub;

@Stateless
public class ErAlWorkRecordCheckServicePubImpl implements ErAlWorkRecordCheckServicePub {

	@Inject
	private ErAlWorkRecordCheckService checkService;

	@Override
	public List<RegulationInfoEmployeeQueryResult> filterEmployees(GeneralDate workingDate,
			Collection<String> employeeIds, String EACheckID) {
		return this.checkService.filterEmployees(workingDate, employeeIds, EACheckID).stream()
				.map(r -> mapTo(r))
				.collect(Collectors.toList());
	}

	@Override
	public Map<String, Boolean> check(GeneralDate workingDate, Collection<String> employeeIds, String EACheckID) {
		return this.checkService.check(workingDate, employeeIds, EACheckID);
	}

	@Override
	public Map<String, List<RegulationInfoEmployeeQueryResult>> filterEmployees(GeneralDate workingDate,
			Collection<String> employeeIds, List<String> EACheckIDs) {
		
		return this.checkService.filterEmployees(workingDate, employeeIds, EACheckIDs)
									.entrySet().stream().collect(Collectors.toMap(
											c -> c.getKey(), 
											c -> c.getValue().stream().map(r -> mapTo(r)).collect(Collectors.toList())));
	}

	@Override
	public Map<String, Map<String, Boolean>> check(GeneralDate workingDate, Collection<String> employeeIds,
			List<String> EACheckIDs) {
		return this.checkService.check(workingDate, employeeIds, EACheckIDs);
	}

	private RegulationInfoEmployeeQueryResult mapTo(RegulationInfoEmployeeQueryR r) {
		return RegulationInfoEmployeeQueryResult.builder().employeeCode(r.getEmployeeCode())
				.employeeId(r.getEmployeeId()).employeeName(r.getEmployeeName())
				.workplaceCode(r.getWorkplaceCode()).workplaceId(r.getWorkplaceId())
				.workplaceName(r.getWorkplaceName()).build();
	}

}
