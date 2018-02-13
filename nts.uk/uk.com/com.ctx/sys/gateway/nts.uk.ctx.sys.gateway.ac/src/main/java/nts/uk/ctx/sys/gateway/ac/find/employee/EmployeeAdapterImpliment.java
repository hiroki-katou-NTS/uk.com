/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.ac.find.employee;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmployeeInfoPub;
import nts.uk.ctx.sys.gateway.dom.adapter.employee.EmployeeInfoAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.employee.EmployeeInfoDtoImport;

/**
 * The Class EmployeeAdapterImpliment.
 */
@Stateless
public class EmployeeAdapterImpliment implements EmployeeInfoAdapter {

	/** The employee info pub. */
	@Inject
	private EmployeeInfoPub employeeInfoPub;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.adapter.employee.EmployeeInfoAdapter#getEmployeesAtWorkByBaseDate(java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<EmployeeInfoDtoImport> getEmployeesAtWorkByBaseDate(String companyId, GeneralDate baseDate) {

		return this.employeeInfoPub.getEmployeesAtWorkByBaseDate(companyId, baseDate).stream()
				.map(f -> {
					return new EmployeeInfoDtoImport(f.getCompanyId(), f.getEmployeeCode(), f.getEmployeeId(), f.getPersonId());
				})
				.collect(Collectors.toList());	
	}
}
