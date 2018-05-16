/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.pubimp.employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDateTime;
import nts.uk.query.app.employee.RegulationInfoEmpQueryDto;
import nts.uk.query.app.employee.RegulationInfoEmployeeDto;
import nts.uk.query.app.employee.RegulationInfoEmployeeFinder;
import nts.uk.query.model.employee.RegulationInfoEmployee;
import nts.uk.query.model.employee.RegulationInfoEmployeeRepository;
import nts.uk.query.pub.employee.EmployeeSearchQueryDto;
import nts.uk.query.pub.employee.RegulationInfoEmployeeExport;
import nts.uk.query.pub.employee.RegulationInfoEmployeePub;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class RegulationInfoEmployeePubImpl.
 */
@Stateless
public class RegulationInfoEmployeePubImpl implements RegulationInfoEmployeePub {

	/** The Constant GENERAL_DATE_FORMAT. */
	private final static String GENERAL_DATE_FORMAT = "yyyy-MM-dd";

	/** The emp finder. */
	@Inject
	private RegulationInfoEmployeeFinder empFinder;
	
	/** The employee info repository. */
	@Inject
	private RegulationInfoEmployeeRepository employeeInfoRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see employee.RegulationInfoEmployeePub#find(nts.uk.query.app.employee.
	 * EmployeeSearchQueryDto)
	 */
	@Override
	public List<RegulationInfoEmployeeExport> find(EmployeeSearchQueryDto query) {
		List<RegulationInfoEmployeeDto> resultList = new ArrayList<>();
		try {
			resultList = this.empFinder.find(this.toQueryModel(query));
		} catch (RuntimeException e) {
			// When search only me.
			if (e.getMessage().equals("Unable to search")) {
				// Find login employee info.
				String loginEmployeeId = AppContexts.user().employeeId();
				String companyId = AppContexts.user().companyId();
				RegulationInfoEmployee loginEmployee = this.employeeInfoRepository.findBySid(companyId, loginEmployeeId,
						GeneralDateTime.now());
				return Arrays
						.asList(RegulationInfoEmployeeExport.builder().employeeCode(loginEmployee.getEmployeeCode())
								.employeeId(loginEmployee.getEmployeeID()).employeeName(loginEmployee.getName().get())
								.workplaceCode(loginEmployee.getWorkplaceCode().get())
								.workplaceId(loginEmployee.getWorkplaceId().get())
								.workplaceName(loginEmployee.getWorkplaceName().get()).build());
			}
		}
		return resultList.stream().map(item -> RegulationInfoEmployeeExport.builder()
				.employeeCode(item.getEmployeeCode())
				.employeeId(item.getEmployeeId())
				.employeeName(item.getEmployeeName())
				.workplaceCode(item.getWorkplaceCode())
				.workplaceId(item.getWorkplaceId())
				.workplaceName(item.getWorkplaceName())
				.build())
				.collect(Collectors.toList());
	}

	/**
	 * To query model.
	 *
	 * @param query the query
	 * @return the employee search query
	 */
	private RegulationInfoEmpQueryDto toQueryModel(EmployeeSearchQueryDto query) {
		return RegulationInfoEmpQueryDto.builder()
			.baseDate(query.getBaseDate().toString(GENERAL_DATE_FORMAT))
			.classificationCodes(query.getClassificationCodes())
			.departmentCodes(query.getDepartmentCodes())
			.employmentCodes(query.getEmploymentCodes())
			.filterByClassification(query.getFilterByClassification())
			.filterByDepartment(query.getFilterByDepartment())
			.filterByEmployment(query.getFilterByEmployment())
			.filterByJobTitle(query.getFilterByJobTitle())
			.filterByWorkplace(query.getFilterByWorkplace())
			.includeIncumbents(query.getIncludeIncumbents())
			.includeOccupancy(query.getIncludeOccupancy())
			.includeRetirees(query.getIncludeRetirees())
			.includeWorkersOnLeave(query.getIncludeWorkersOnLeave())
			.includeAreOnLoan(query.getIncludeAreOnLoan())
			.includeGoingOnLoan(query.getIncludeGoingOnLoan())
			.jobTitleCodes(query.getJobTitleCodes())
			.filterByWorktype(query.getFilterByWorktype())
			.worktypeCodes(query.getWorktypeCodes())
			.filterByClosure(query.getFilterByClosure())
			.closureIds(query.getClosureIds())
			.nameType(query.getNameType())
			.periodEnd(query.getPeriodEnd().toString(GENERAL_DATE_FORMAT))
			.periodStart(query.getPeriodStart().toString(GENERAL_DATE_FORMAT))
			.referenceRange(query.getReferenceRange())
			.retireEnd(query.getRetireEnd().toString(GENERAL_DATE_FORMAT))
			.retireStart(query.getRetireStart().toString(GENERAL_DATE_FORMAT))
			.sortOrderNo(query.getSortOrderNo())
			.workplaceCodes(query.getWorkplaceCodes())
			.systemType(query.getSystemType())
			.build();
	}
}
