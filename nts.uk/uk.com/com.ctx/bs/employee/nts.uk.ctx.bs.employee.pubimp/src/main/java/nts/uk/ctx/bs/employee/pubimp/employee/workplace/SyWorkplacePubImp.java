/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pubimp.employee.workplace;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.company.organization.employee.workplace.AffWorkplaceHistory;
import nts.uk.ctx.basic.dom.company.organization.employee.workplace.AffWorkplaceHistoryRepository;
import nts.uk.ctx.bs.employee.dom.access.workplace.SyWorkplaceAdapter;
import nts.uk.ctx.bs.employee.dom.access.workplace.dto.WorkplaceImport;
import nts.uk.ctx.bs.employee.pub.employee.workplace.SyWorkplacePub;

/**
 * The Class EmployeePubImp.
 */
@Stateless
public class SyWorkplacePubImp implements SyWorkplacePub {

	/** The workplace adapter. */
	@Inject
	private SyWorkplaceAdapter workplaceAdapter;

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
	public String getWorkplaceId(String companyId, String employeeId, GeneralDate baseDate) {
		// Query
		List<AffWorkplaceHistory> affWorkplaceHistories = workplaceHistoryRepository
				.searchWorkplaceHistoryByEmployee(employeeId, baseDate);

		List<String> wkpIds = affWorkplaceHistories.stream().map(item -> item.getWorkplaceId().v())
				.collect(Collectors.toList());

		List<WorkplaceImport> acWorkplaceDtos = workplaceAdapter.findByWkpIds(wkpIds);

		Map<String, String> comWkpMap = acWorkplaceDtos.stream().collect(
				Collectors.toMap(WorkplaceImport::getCompanyId, WorkplaceImport::getWorkplaceId));

		// Return workplace id
		return comWkpMap.get(companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.pub.employee.EmployeePub#findWpkIdsBySCode(java.
	 * lang.String, java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<String> findWpkIdsBySid(String companyId, String employeeId, GeneralDate baseDate) {
		// Query
		List<AffWorkplaceHistory> affWorkplaceHistories = workplaceHistoryRepository
				.searchWorkplaceHistoryByEmployee(employeeId, baseDate);

		// Return
		return affWorkplaceHistories.stream().map(item -> {
			return item.getWorkplaceId().v();
		}).collect(Collectors.toList());
	}

}
