/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.company.pub.employment;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.basic.dom.company.organization.employment.EmploymentRepository;

/**
 * The Class EmploymentPubImp.
 */
@Stateless
public class EmploymentPubImp implements EmploymentPub {

	/** The employment repository. */
	@Inject
	private EmploymentRepository employmentRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.company.pub.employment.EmploymentPub#findByEmpCodes(java.
	 * util.List)
	 */
	@Override
	public List<EmploymentExport> findByEmpCodes(List<String> employmentCodes) {
		return employmentRepository.findByEmpCodes(employmentCodes).stream()
				.map(item -> new EmploymentExport(item.getCompanyId().v(), item.getWorkClosureId(),
						item.getSalaryClosureId(), item.getEmploymentCode().v(),
						item.getEmploymentName().v()))
				.collect(Collectors.toList());
	}

}
