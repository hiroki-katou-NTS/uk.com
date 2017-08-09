/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.adapter;

import java.util.List;

/**
 * The Interface CompanyInformationAdapter.
 */
public interface CompanyInformationAdapter {
	
	/**
	 * Find by contract code.
	 *
	 * @param contractCode the contract code
	 * @return the list
	 */
	List<CompanyInformationDto> findByContractCode(String contractCode);
}
