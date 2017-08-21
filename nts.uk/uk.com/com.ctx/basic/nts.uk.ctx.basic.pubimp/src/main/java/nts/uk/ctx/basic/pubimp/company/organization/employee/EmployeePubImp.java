/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.pubimp.company.organization.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.basic.dom.company.organization.employee.Employee;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeRepository;
import nts.uk.ctx.basic.dom.company.organization.employee.workplace.AffWorkplaceHistory;
import nts.uk.ctx.basic.dom.company.organization.employee.workplace.AffWorkplaceHistoryRepository;
import nts.uk.ctx.basic.dom.company.organization.workplace.Workplace;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceRepository;
import nts.uk.ctx.basic.pub.company.organization.employee.EmployeeDto;
import nts.uk.ctx.basic.pub.company.organization.employee.EmployeePub;

/**
 * The Class EmployeePubImp.
 */
@Stateless
public class EmployeePubImp implements EmployeePub {

	private final int FIRST_INDEX = 0;

	// @Inject
	// private EmployeeSearchQueryProcessor employeeSearchQueryProcessor;

	/** The employee repo. */
	@Inject
	private EmployeeRepository employeeRepo;

	/** The workplace repository. */
	@Inject
	private WorkplaceRepository workplaceRepository;

	/** The workplace history repository. */
	@Inject
	private AffWorkplaceHistoryRepository workplaceHistoryRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.pub.company.organization.employee.EmployeePub#find(java.
	 * lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public Optional<EmployeeDto> find(String employeeId, GeneralDate baseDate) {
		Optional<Employee> optEmployee = employeeRepo.findBySid(employeeId);

		List<AffWorkplaceHistory> affWorkplaceHistories = workplaceHistoryRepository
				.searchWorkplaceHistoryByEmployee(employeeId, baseDate);

		List<Workplace> workplaces = new ArrayList<>();
		if (!CollectionUtil.isEmpty(affWorkplaceHistories)) {
			workplaces = workplaceRepository
					.findAllWorkplace(affWorkplaceHistories.get(FIRST_INDEX).getWorkplaceId().v());
		}

		if (!optEmployee.isPresent() || CollectionUtil.isEmpty(affWorkplaceHistories)
				|| CollectionUtil.isEmpty(workplaces)) {
			return Optional.empty();
		}

		Employee employee = optEmployee.get();
		Workplace workplace = workplaces.get(FIRST_INDEX);

		// Collect info
		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setCompanyId(employee.getCompanyId());
		employeeDto.setPId(employee.getPId());
		employeeDto.setSId(employee.getSId());
		employeeDto.setSCd(employee.getSCd().v());
		employeeDto.setSMail(employee.getSMail().v());
		employeeDto.setRetirementDate(employee.getRetirementDate());
		employeeDto.setJoinDate(employee.getJoinDate());
		employeeDto.setWkpCode(workplace.getWorkplaceCode().v());
		employeeDto.setWkpId(workplace.getWorkplaceId().v());
		employeeDto.setWkpName(workplace.getWorkplaceName().v());

		// Return
		return Optional.of(employeeDto);
	}

}
