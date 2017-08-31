/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pubimp.employee;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.company.organization.employee.workplace.AffWorkplaceHistory;
import nts.uk.ctx.basic.dom.company.organization.employee.workplace.AffWorkplaceHistoryRepository;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeDto;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;

/**
 * The Class SyEmployeePubImp.
 */
@Stateless
public class SyEmployeePubImp implements SyEmployeePub {

	/** The employee repository. */
	@Inject
	private EmployeeRepository employeeRepository;

	/** The workplace history repository. */
	@Inject
	private AffWorkplaceHistoryRepository workplaceHistoryRepository;

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

		List<Employee> employeeList = employeeRepository.findByListEmployeeId(companyId,
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
