/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.pubimp.employee;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDateTime;
import nts.uk.query.app.employee.RegulationInfoEmpQueryDto;
import nts.uk.query.app.employee.RegulationInfoEmployeeDto;
import nts.uk.query.app.employee.RegulationInfoEmployeeFinder;
import nts.uk.query.model.employee.RegularSortingType;
import nts.uk.query.model.employee.RegulationInfoEmployeeRepository;
import nts.uk.query.model.employee.SortingConditionOrder;
import nts.uk.query.pub.employee.EmployeeSearchQueryDto;
import nts.uk.query.pub.employee.RegulationInfoEmployeeExport;
import nts.uk.query.pub.employee.RegulationInfoEmployeePub;
import nts.uk.query.pub.employee.SortingConditionOrderDto;

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

	/** The reg inf emp repo. */
	@Inject
	private RegulationInfoEmployeeRepository regInfEmpRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see employee.RegulationInfoEmployeePub#find(nts.uk.query.app.employee.
	 * EmployeeSearchQueryDto)
	 */
	@Override
	public List<RegulationInfoEmployeeExport> find(EmployeeSearchQueryDto query) {
		List<RegulationInfoEmployeeDto> resultList = this.empFinder.find(this.toQueryModel(query));
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.query.pub.employee.RegulationInfoEmployeePub#sortEmployee(java.
	 * lang.String, java.util.List, java.lang.Integer, java.lang.Integer,
	 * java.lang.Integer, nts.arc.time.GeneralDateTime)
	 */
	@Override
	public List<String> sortEmployee(String comId, List<String> sIds, Integer systemType, Integer orderNo,
			Integer nameType, GeneralDateTime referenceDate) {
		return this.regInfEmpRepo.sortEmployees(comId, sIds, systemType, orderNo, nameType, referenceDate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.query.pub.employee.RegulationInfoEmployeePub#sortEmployee(java.
	 * lang.String, java.util.List, java.util.List,
	 * nts.arc.time.GeneralDateTime)
	 */
	@Override
	public List<String> sortEmployee(String comId, List<String> sIds, List<SortingConditionOrderDto> orders,
			GeneralDateTime referenceDate) {
		return this.regInfEmpRepo.sortEmployees(comId, sIds, this.convertToQueryModel(orders), referenceDate);
	}

	/**
	 * Convert to query model.
	 *
	 * @param orders the orders
	 * @return the list
	 */
	private List<SortingConditionOrder> convertToQueryModel(List<SortingConditionOrderDto> orders) {
		return orders.stream().map(dto -> {
			SortingConditionOrder order = new SortingConditionOrder();
			order.setOrder(dto.getOrder());
			order.setType(RegularSortingType.valueOf(dto.getType().value));
			return order;
		}).collect(Collectors.toList());
	}
}
