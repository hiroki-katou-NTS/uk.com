/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.ac.find.login;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.sys.gateway.dom.adapter.EmployeeCodeSettingAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.EmployeeCodeSettingDto;

/**
 * The Class EmployeeCodeSettingAdapterImpl.
 */
@Stateless
public class EmployeeCodeSettingAdapterImpl implements EmployeeCodeSettingAdapter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.gateway.dom.adapter.EmployeeCodeSettingAdapter#
	 * getbyCompanyId(java.lang.String)
	 */
	@Override
	public Optional<EmployeeCodeSettingDto> getbyCompanyId(String companyId) {
		// mock data
		if (companyId.equals("0-1234")) {
			EmployeeCodeSettingDto fake = new EmployeeCodeSettingDto("000000000000-1234", 12, 1);
			return Optional.of(fake);
		} else {
			return Optional.empty();
		}
	}
}
