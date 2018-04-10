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
		GeneralDateTime baseDate = GeneralDateTime.localDateTime(queryX.getBaseDate().localDate().atStartOfDay());
		GeneralDateTime periodStart = GeneralDateTime.localDateTime(queryX.getPeriodStart().localDate().atStartOfDay());
		GeneralDateTime periodEnd = GeneralDateTime.localDateTime(queryX.getPeriodEnd().localDate().atStartOfDay());
		return EmployeeSearchQueryDto.builder().baseDate(baseDate).referenceRange(queryX.getReferenceRange())
				.filterByEmployment(queryX.getFilterByEmployment()).employmentCodes(queryX.getEmploymentCodes())
				.filterByDepartment(queryX.getFilterByDepartment()).departmentCodes(queryX.getDepartmentCodes())
				.filterByWorkplace(queryX.getFilterByWorkplace()).workplaceCodes(queryX.getWorkplaceCodes())
				.filterByClassification(queryX.getFilterByClassification()).classificationCodes(queryX.getClassificationCodes())
				.filterByJobTitle(queryX.getFilterByJobTitle()).jobTitleCodes(queryX.getJobTitleCodes())
				.filterByWorktype(queryX.getFilterByWorktype()).worktypeCodes(queryX.getWorktypeCodes())
				.periodStart(periodStart).periodEnd(periodEnd)
				.includeIncumbents(queryX.getIncludeIncumbents())
				.includeWorkersOnLeave(queryX.getIncludeWorkersOnLeave())
				.includeOccupancy(queryX.getIncludeOccupancy())
				.systemType(2).sortOrderNo(1)
				.includeRetirees(queryX.getIncludeRetirees()).build();
	}

}
