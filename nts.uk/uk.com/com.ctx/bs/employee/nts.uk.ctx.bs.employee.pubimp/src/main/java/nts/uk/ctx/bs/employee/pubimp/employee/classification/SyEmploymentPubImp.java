/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pubimp.employee.classification;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.company.organization.employee.employment.AffEmploymentHistory;
import nts.uk.ctx.basic.dom.company.organization.employee.employment.AffEmploymentHistoryRepository;
import nts.uk.ctx.bs.employee.dom.access.employment.SyEmploymentAdapter;
import nts.uk.ctx.bs.employee.dom.access.employment.dto.EmploymentImport;
import nts.uk.ctx.bs.employee.pub.employee.employment.SyEmploymentPub;

/**
 * The Class SyEmploymentPubImp.
 */
@Stateless
public class SyEmploymentPubImp implements SyEmploymentPub {

	/** The employment adapter. */
	@Inject
	private SyEmploymentAdapter employmentAdapter;

	/** The employment history repository. */
	@Inject
	private AffEmploymentHistoryRepository employmentHistoryRepository;

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

		List<String> employmentCodes = affEmploymentHistories.stream()
				.map(item -> item.getEmploymentCode().v()).collect(Collectors.toList());

		List<EmploymentImport> acEmploymentDtos = employmentAdapter.findByEmpCodes(employmentCodes);

		Map<String, String> comEmpMap = acEmploymentDtos.stream().collect(Collectors
				.toMap(EmploymentImport::getCompanyId, EmploymentImport::getEmploymentCode));

		// Return EmploymentCode
		return comEmpMap.get(companyId);
	}

}
