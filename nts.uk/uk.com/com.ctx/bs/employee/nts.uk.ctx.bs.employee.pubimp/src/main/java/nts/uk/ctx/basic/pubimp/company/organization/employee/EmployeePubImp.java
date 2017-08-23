/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.pubimp.company.organization.employee;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.basic.dom.company.organization.employee.Employee;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeRepository;
import nts.uk.ctx.basic.dom.company.organization.employee.employment.AffEmploymentHistory;
import nts.uk.ctx.basic.dom.company.organization.employee.employment.AffEmploymentHistoryRepository;
import nts.uk.ctx.basic.dom.company.organization.employee.workplace.AffWorkplaceHistory;
import nts.uk.ctx.basic.dom.company.organization.employee.workplace.AffWorkplaceHistoryRepository;
import nts.uk.ctx.bs.employee.dom.access.workplace.WorkplaceAdapter;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeDto;
import nts.uk.ctx.bs.employee.pub.employee.EmployeePub;

/**
 * The Class EmployeePubImp.
 */
@Stateless
public class EmployeePubImp implements EmployeePub {

	/** The first index. */
	private final int FIRST_INDEX = 0;

	/** The workplace adapter. */
	@Inject
	private WorkplaceAdapter workplaceAdapter;

	/** The employee repository. */
	@Inject
	private EmployeeRepository employeeRepository;

	/** The workplace history repository. */
	@Inject
	private AffWorkplaceHistoryRepository workplaceHistoryRepository;

	/** The employment history repository. */
	@Inject
	private AffEmploymentHistoryRepository employmentHistoryRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.pub.company.organization.employee.EmployeePub#find(java.
	 * lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public String getWorkplaceId(String companyId, String employeeId, GeneralDate baseDate) {
		// Query
		List<AffWorkplaceHistory> affWorkplaceHistories = workplaceHistoryRepository
				.searchWorkplaceHistoryByEmployee(employeeId, baseDate);

		// TODO: Import ac
		// workplaceAdapter.findAllWorkplace(companyId, baseDate)

		// Check exist
		if (CollectionUtil.isEmpty(affWorkplaceHistories)) {
			return null;
		}

		// Return
		return affWorkplaceHistories.get(FIRST_INDEX).getWorkplaceId().v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.pub.company.organization.employee.EmployeePub#
	 * getEmployeeCode(java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public String getEmploymentCode(String companyId, String employeeId, GeneralDate baseDate) {
		// Query
		List<AffEmploymentHistory> affEmploymentHistories = employmentHistoryRepository
				.searchEmploymentOfSids(Arrays.asList(employeeId), baseDate);

		// TODO: Import ac

		// Check exist
		if (CollectionUtil.isEmpty(affEmploymentHistories)) {
			return null;
		}

		// Return
		return affEmploymentHistories.get(FIRST_INDEX).getEmploymentCode().v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.pub.company.organization.employee.EmployeePub#
	 * findByWpkIds(java.lang.String, java.util.List, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<EmployeeDto> findByWpkIds(String companyId, List<String> workplaceIds,
			GeneralDate baseDate) {
		// Query
		List<AffWorkplaceHistory> affWorkplaceHistories = workplaceHistoryRepository
				.searchWorkplaceHistory(baseDate, workplaceIds);

		List<String> employeeIds = affWorkplaceHistories.stream()
				.map(AffWorkplaceHistory::getEmployeeId).collect(Collectors.toList());

		List<Employee> employeeList = employeeRepository.getListPersonByListEmployeeId(companyId,
				employeeIds);

		// Return
		return employeeList.stream().map(item -> {
			EmployeeDto dto = new EmployeeDto();
			dto.setCompanyId(item.getCompanyId());
			dto.setPId(item.getPId());
			dto.setSId(item.getSId());
			dto.setSCd(item.getSCd().v());
			dto.setSMail(item.getSMail().v());
			dto.setRetirementDate(item.getRetirementDate());
			dto.setJoinDate(item.getJoinDate());
			return dto;
		}).collect(Collectors.toList());
	}

}
