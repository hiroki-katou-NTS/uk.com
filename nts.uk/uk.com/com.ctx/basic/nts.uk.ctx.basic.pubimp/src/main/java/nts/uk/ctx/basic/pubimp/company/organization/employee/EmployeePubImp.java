/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.pubimp.company.organization.employee;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.basic.dom.company.organization.employee.employment.AffEmploymentHistory;
import nts.uk.ctx.basic.dom.company.organization.employee.employment.AffEmploymentHistoryRepository;
import nts.uk.ctx.basic.dom.company.organization.employee.workplace.AffWorkplaceHistory;
import nts.uk.ctx.basic.dom.company.organization.employee.workplace.AffWorkplaceHistoryRepository;
import nts.uk.ctx.basic.pub.company.organization.employee.EmployeePub;

/**
 * The Class EmployeePubImp.
 */
@Stateless
public class EmployeePubImp implements EmployeePub {

	/** The first index. */
	private final int FIRST_INDEX = 0;

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
	public String getWorkplaceId(String employeeId, GeneralDate baseDate) {
		// Query
		List<AffWorkplaceHistory> affWorkplaceHistories = workplaceHistoryRepository
				.searchWorkplaceHistoryByEmployee(employeeId, baseDate);

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
	public String getEmploymentCode(String employeeId, GeneralDate baseDate) {
		// Query
		List<AffEmploymentHistory> affEmploymentHistories = employmentHistoryRepository
				.searchEmploymentOfSids(Arrays.asList(employeeId), baseDate);

		// Check exist
		if (CollectionUtil.isEmpty(affEmploymentHistories)) {
			return null;
		}

		// Return
		return affEmploymentHistories.get(FIRST_INDEX).getEmploymentCode().v();
	}

}
