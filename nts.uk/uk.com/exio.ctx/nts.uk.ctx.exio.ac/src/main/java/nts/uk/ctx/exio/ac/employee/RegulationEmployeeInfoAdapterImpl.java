package nts.uk.ctx.exio.ac.employee;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmployeeInfoPub;
import nts.uk.ctx.exio.dom.exo.exoutsummaryservice.EmployeeInforExoImport;
import nts.uk.ctx.exio.dom.exo.exoutsummaryservice.RegulationInfoEmployeeAdapter;
import nts.uk.ctx.exio.dom.exo.exoutsummaryservice.RegulationInfoEmployeeImport;
import nts.uk.ctx.exio.dom.exo.exoutsummaryservice.RegulationInfoEmployeeQueryImport;
import nts.uk.query.pub.employee.EmployeeSearchQueryDto;
import nts.uk.query.pub.employee.RegulationInfoEmployeeExport;
import nts.uk.query.pub.employee.RegulationInfoEmployeePub;

@Stateless
public class RegulationEmployeeInfoAdapterImpl implements RegulationInfoEmployeeAdapter {
	
	@Inject
	private RegulationInfoEmployeePub pub;
	@Inject
	private EmployeeInfoPub empPub;

	@Override
	public List<RegulationInfoEmployeeImport> findEmployees(RegulationInfoEmployeeQueryImport importQuery) {
		EmployeeSearchQueryDto query = this.convertQueryFromImport(importQuery);
		return this.pub.find(query).stream()
				.map(this::convertDataFromExport)
				.collect(Collectors.toList());
	}
	
	public EmployeeSearchQueryDto convertQueryFromImport(RegulationInfoEmployeeQueryImport importQuery) {
		return EmployeeSearchQueryDto.builder()
				.baseDate(importQuery.getBaseDate())
				.classificationCodes(importQuery.getClassificationCodes())
				.closureIds(importQuery.getClosureIds())
				.departmentCodes(importQuery.getDepartmentCodes())
				.employmentCodes(importQuery.getEmploymentCodes())
				.filterByClassification(importQuery.getFilterByClassification())
				.filterByClosure(importQuery.getFilterByClosure())
				.filterByDepartment(importQuery.getFilterByDepartment())
				.filterByEmployment(importQuery.getFilterByEmployment())
				.filterByJobTitle(importQuery.getFilterByJobTitle())
				.filterByWorkplace(importQuery.getFilterByWorkplace())
				.filterByWorktype(importQuery.getFilterByWorktype())
				.includeAreOnLoan(importQuery.getIncludeAreOnLoan())
				.includeGoingOnLoan(importQuery.getIncludeGoingOnLoan())
				.includeIncumbents(importQuery.getIncludeIncumbents())
				.includeOccupancy(importQuery.getIncludeOccupancy())
				.includeRetirees(importQuery.getIncludeRetirees())
				.includeWorkersOnLeave(importQuery.getIncludeWorkersOnLeave())
				.jobTitleCodes(importQuery.getJobTitleCodes())
				.nameType(importQuery.getNameType())
				.periodEnd(importQuery.getPeriodEnd())
				.periodStart(importQuery.getPeriodStart())
				.referenceRange(importQuery.getReferenceRange())
				.retireEnd(importQuery.getRetireEnd())
				.retireStart(importQuery.getRetireStart())
				.sortOrderNo(importQuery.getSortOrderNo())
				.systemType(importQuery.getSystemType())
				.workplaceCodes(importQuery.getWorkplaceCodes())
				.worktypeCodes(importQuery.getWorktypeCodes())
				.build();
	}
	
	public RegulationInfoEmployeeImport convertDataFromExport(RegulationInfoEmployeeExport export) {
		return RegulationInfoEmployeeImport.builder()
				.employeeCode(export.getEmployeeCode())
				.employeeId(export.getEmployeeId())
				.employeeName(export.getEmployeeName())
				.workplaceCode(export.getWorkplaceCode())
				.workplaceId(export.getWorkplaceId())
				.workplaceName(export.getWorkplaceName())
				.build();
	}

	@Override
	public Optional<EmployeeInforExoImport> getEmployeeInforByCid(String cid, String sid) {
		Optional<EmployeeInforExoImport> result = empPub.getEmployeeInfo(cid, sid)
				.map(x -> new EmployeeInforExoImport(x.getCompanyId(), x.getEmployeeId(), x.getPersonId(), x.getEmployeeCode()));
		return result;
	}

}
