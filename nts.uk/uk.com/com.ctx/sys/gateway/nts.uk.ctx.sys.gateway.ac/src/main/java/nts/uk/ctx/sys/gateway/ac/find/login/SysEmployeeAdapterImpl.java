/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.ac.find.login;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.sys.gateway.dom.adapter.SysEmployeeAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.EmployeeDto;

/**
 * The Class EmployeeAdapterImpl.
 */
@Stateless
public class SysEmployeeAdapterImpl implements SysEmployeeAdapter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.gateway.dom.adapter.EmployeeAdapter#getByEmployeeCode(java
	 * .lang.String, java.lang.String)
	 */
	@Override
	public Optional<EmployeeDto> getByEmployeeCode(String companyId, String employeeCode) {
		// mock data
		if (employeeCode.equals("123000000000")) {
			EmployeeDto em = new EmployeeDto("busiName", "personalId", "111111111111111111111111111111111111", employeeCode);
			return Optional.of(em);
		} else {
			return Optional.empty();
		}
	}
}
