/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.ac.employment;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.company.pub.employment.EmploymentPub;
import nts.uk.ctx.bs.employee.dom.access.employment.SyEmploymentAdapter;
import nts.uk.ctx.bs.employee.dom.access.employment.dto.AcEmploymentDto;

/**
 * The Class EmploymentAdapterImpl.
 */
@Stateless
public class EmploymentAdapterImpl implements SyEmploymentAdapter {

	/** The employment pub. */
	@Inject
	private EmploymentPub employmentPub;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.dom.access.employment.EmploymentAdapter#
	 * findByEmpCodes(java.util.List)
	 */
	@Override
	public List<AcEmploymentDto> findByEmpCodes(List<String> employmentCodes) {
		return employmentPub.findByEmpCodes(employmentCodes).stream()
				.map(item -> new AcEmploymentDto(item.getCompanyId(), item.getWorkClosureId(),
						item.getSalaryClosureId(), item.getEmploymentCode(),
						item.getEmploymentName()))
				.collect(Collectors.toList());
	}

}
