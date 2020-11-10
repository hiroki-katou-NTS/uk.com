/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.login.adapter;

import java.util.List;

import nts.uk.ctx.sys.gateway.dom.login.dto.CompanyInforImport;
import nts.uk.ctx.sys.gateway.dom.login.dto.CompanyInformationImport;

/**
 * The Interface CompanyInformationAdapter.
 */
public interface CompanyInformationAdapter {
	
	/**
	 * Create CompanyId.
	 *
	 * @param companyCode
	 * @param tenantCode
	 * @return String
	 */
	String createCompanyId(String tenantCode, String companyCode);
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	List<CompanyInformationImport> findLstCompany(String contractCode);
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	List<CompanyInformationImport> findByContract(String contractCode);
	
	/**
	 * Find by id.
	 *
	 * @param companyId the company id
	 * @return the company information import
	 */
	CompanyInformationImport findById(String companyId);
	
	/**
	 * Find com by id.
	 *
	 * @param companyId the company id
	 * @return the company infor import
	 */
	CompanyInforImport findComById(String companyId);
}
