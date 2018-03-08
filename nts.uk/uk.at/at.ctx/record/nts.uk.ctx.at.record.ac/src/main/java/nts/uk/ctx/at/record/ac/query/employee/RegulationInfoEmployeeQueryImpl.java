package nts.uk.ctx.at.record.ac.query.employee;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.adapter.query.employee.RegulationInfoEmployeeQuery;
import nts.uk.ctx.at.record.dom.adapter.query.employee.RegulationInfoEmployeeQueryAdapter;
import nts.uk.ctx.at.record.dom.adapter.query.employee.RegulationInfoEmployeeQueryR;
import nts.uk.query.pub.employee.EmployeeSearchQueryDto;
import nts.uk.query.pub.employee.RegulationInfoEmployeePub;

@Stateless
public class RegulationInfoEmployeeQueryImpl implements RegulationInfoEmployeeQueryAdapter {

	@Inject
	private RegulationInfoEmployeePub employeePub;

	@Override
	public List<RegulationInfoEmployeeQueryR> search(RegulationInfoEmployeeQuery query) {
		return employeePub.find(createQueryToFilterEmployees(query)).stream()
				.map(r -> RegulationInfoEmployeeQueryR.builder()
														.employeeCode(r.getEmployeeCode())
														.employeeId(r.getEmployeeId())
														.employeeName(r.getEmployeeName())
														.workplaceCode(r.getWorkplaceCode())
														.workplaceId(r.getWorkplaceId())
														.workplaceName(r.getWorkplaceName())
														.build())
				.collect(Collectors.toList());
	}

	private EmployeeSearchQueryDto createQueryToFilterEmployees(RegulationInfoEmployeeQuery queryX) {
		String workingDate = queryX.getBaseDate().toString();
		EmployeeSearchQueryDto query = new EmployeeSearchQueryDto();
		query.setBaseDate(workingDate);
		query.setFilterByEmployment(true);
		query.setEmploymentCodes(queryX.getEmploymentCodes());
		query.setFilterByDepartment(false);
		query.setFilterByWorkplace(false);
		query.setFilterByClassification(true);
		query.setClassificationCodes(queryX.getClassificationCodes());
		query.setFilterByJobTitle(true);
		query.setJobTitleCodes(queryX.getJobTitleCodes());
		query.setFilterByWorktype(true);
		query.setWorktypeCodes(queryX.getWorktypeCodes());
		query.setPeriodStart(workingDate);
		query.setPeriodEnd(workingDate);
		query.setIncludeIncumbents(true);
		query.setIncludeWorkersOnLeave(true);
		query.setIncludeOccupancy(true);
		query.setIncludeAreOnLoan(true);
		query.setIncludeGoingOnLoan(false);
		query.setIncludeRetirees(false);
		return query;
	}
}
