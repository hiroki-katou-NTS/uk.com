/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.ac.find.login;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.sys.gateway.dom.adapter.EmployeeAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.EmployeeDto;

/**
 * The Class EmployeeAdapterImpl.
 */
@Stateless
public class EmployeeAdapterImpl implements EmployeeAdapter{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.adapter.EmployeeAdapter#getByEmployeeCode(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<EmployeeDto> getByEmployeeCode(String companyId, String employeeCode) {
		
		EmployeeDto em = new EmployeeDto("busiName", "personalId", 1111, employeeCode);
		//mock data
		return Optional.of(em);
	}
}
