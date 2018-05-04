package nts.uk.ctx.at.record.ac.query.employee;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDateTime;
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
				.map(r -> RegulationInfoEmployeeQueryR.builder().employeeCode(r.getEmployeeCode())
						.employeeId(r.getEmployeeId()).employeeName(r.getEmployeeName())
						.workplaceCode(r.getWorkplaceCode()).workplaceId(r.getWorkplaceId())
						.workplaceName(r.getWorkplaceName()).build())
				.collect(Collectors.toList());
	}

	private EmployeeSearchQueryDto createQueryToFilterEmployees(RegulationInfoEmployeeQuery queryX) {
		GeneralDateTime workingDate = GeneralDateTime.localDateTime(queryX.getBaseDate().localDate().atStartOfDay());
		return EmployeeSearchQueryDto.builder().baseDate(workingDate)
				            .referenceRange(queryX.getReferenceRange())
							.filterByEmployment(queryX.getFilterByEmployment())
							.employmentCodes(queryX.getEmploymentCodes())
							.filterByDepartment(queryX.getFilterByDepartment())
							.filterByWorkplace(queryX.getFilterByWorkplace())
							.filterByClassification(queryX.getFilterByClassification())
							.classificationCodes(queryX.getClassificationCodes())
							.filterByJobTitle(queryX.getFilterByJobTitle())
							.jobTitleCodes(queryX.getJobTitleCodes())
							.filterByWorktype(queryX.getFilterByWorktype())
							.worktypeCodes(queryX.getWorktypeCodes())
							.periodStart(workingDate)
							.periodEnd(workingDate)
							.includeIncumbents(queryX.getIncludeIncumbents())
							.includeWorkersOnLeave(queryX.getIncludeWorkersOnLeave())
							.includeOccupancy(queryX.getIncludeOccupancy())
							.includeAreOnLoan(queryX.getIncludeAreOnLoan())
							.includeGoingOnLoan(queryX.getIncludeGoingOnLoan())
							.systemType(2)
//							.sortOrderNo(1)
//							.filterByClosure(false)
							.includeRetirees(queryX.getIncludeRetirees()).build();
	}
}
