/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.find.login;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.gateway.dom.login.adapter.CompanyInformationAdapter;
import nts.uk.ctx.sys.gateway.dom.login.dto.CompanyInformationImport;

/**
 * The Class CompanyFinder.
 */
@Stateless
public class CompanyInformationFinder {

	/** The company information adapter. */
	@Inject
	private CompanyInformationAdapter companyInformationAdapter;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<CompanyInformationImport> findAll() {
		return companyInformationAdapter.findAll();
	}
	
	/**
	 * Gets the company infor by code.
	 *
	 * @param companyId the company id
	 * @return the company infor by code
	 */
	public CompanyInformationImport getCompanyInforByCode(String companyId) {
		return companyInformationAdapter.findById(companyId);
	}
}
