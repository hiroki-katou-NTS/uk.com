/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.ac.find.login;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.sys.gateway.dom.adapter.CompanyInformationAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.CompanyInformationDto;

/**
 * The Class CompanyInformationAdapterImpl.
 */
@Stateless
public class CompanyInformationAdapterImpl implements CompanyInformationAdapter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.gateway.dom.adapter.CompanyInformationAdapter#
	 * findByContractCode(java.lang.String)
	 */
	@Override
	public List<CompanyInformationDto> findByContractCode(String contractCode) {
		List<CompanyInformationDto> lst = new ArrayList<>();
		CompanyInformationDto ci1 = new CompanyInformationDto("10000000-1234", "1234", "会社1");
		CompanyInformationDto ci2 = new CompanyInformationDto("10000000-1234", "00000", "会社2");
		
		lst.add(ci1);
		lst.add(ci2);
		return lst;
	}

}
