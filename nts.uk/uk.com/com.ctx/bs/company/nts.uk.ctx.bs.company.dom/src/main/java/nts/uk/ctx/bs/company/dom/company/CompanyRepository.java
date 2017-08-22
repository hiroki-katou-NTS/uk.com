/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.company.dom.company;

import java.util.Optional;

/**
 * The Interface CompanyRepository.
 */

public interface CompanyRepository {
	
	/**
	 * Gets the comany id.
	 *
	 * @param companyId the company id
	 * @return the comany id
	 */
	Optional<Company> getComanyById(String companyId);
	
}

