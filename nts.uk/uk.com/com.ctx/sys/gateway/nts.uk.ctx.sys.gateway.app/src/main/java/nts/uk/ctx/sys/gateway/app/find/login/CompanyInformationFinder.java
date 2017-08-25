/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.find.login;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.gateway.dom.adapter.CompanyInformationAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.CompanyInformationDto;

/**
 * The Class CompanyFinder.
 */
@Stateless
public class CompanyInformationFinder {

	/** The company information adapter. */
	@Inject
	CompanyInformationAdapter companyInformationAdapter;

	/**
	 * Find all by contract code.
	 *
	 * @param contractCode the contract code
	 * @return the list
	 */
	public List<CompanyInformationDto> findAllByContractCode(String contractCode) {
		return companyInformationAdapter.findByContractCode(contractCode);
	}
}
