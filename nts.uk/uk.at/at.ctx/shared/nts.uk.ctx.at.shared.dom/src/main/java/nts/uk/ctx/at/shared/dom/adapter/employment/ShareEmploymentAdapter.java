/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.adapter.employment;

import java.util.List;


public interface ShareEmploymentAdapter {

	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	// RequestList #89
	List<EmpCdNameImport> findAll(String companyId);
	
	/**
	 * Find by emp codes.
	 *
	 * @param companyId the company id
	 * @param empCodes the emp codes
	 * @return the list
	 */
	List<BsEmploymentImport> findByEmpCodes(String companyId, List<String> empCodes);
}
