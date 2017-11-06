/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.login.adapter;

import java.util.List;

import nts.uk.ctx.sys.gateway.dom.login.dto.CompanyInformationImport;

/**
 * The Interface CompanyInformationAdapter.
 */
public interface CompanyInformationAdapter {
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	List<CompanyInformationImport> findAll();
}
