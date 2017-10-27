/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.ac.find.login;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmployeeInfoDtoExport;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmployeeInfoPub;
import nts.uk.ctx.sys.gateway.dom.login.adapter.SysEmployeeAdapter;
import nts.uk.ctx.sys.gateway.dom.login.dto.EmployeeImport;

/**
 * The Class SysEmployeeAdapterImpl.
 */
@Stateless
public class SysEmployeeAdapterImpl implements SysEmployeeAdapter {

	/** The employee info pub. */
	@Inject
	private EmployeeInfoPub employeeInfoPub;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.gateway.dom.adapter.EmployeeAdapter#getByEmployeeCode(java
	 * .lang.String, java.lang.String)
	 */
	@Override
	public Optional<EmployeeImport> getCurrentInfoByScd(String companyId, String employeeCode) {
		Optional<EmployeeInfoDtoExport> opEmployee = employeeInfoPub.getEmployeeInfo(companyId,
				employeeCode, GeneralDate.today());

		// Check exist
		if (opEmployee.isPresent()) {
			EmployeeInfoDtoExport employee = opEmployee.get();
			// convert dto
			EmployeeImport em = new EmployeeImport(employee.getCompanyId(), employee.getPersonId(),
					employee.getEmployeeId(), employee.getEmployeeCode());
			return Optional.of(em);
		}

		// Return
		return Optional.empty();
	}
}
