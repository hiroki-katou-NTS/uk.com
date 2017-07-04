/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.beginningmonth;

import java.util.Optional;

/**
 * The Interface BeginningMonthRepository.
 */

public interface BeginningMonthRepository {

	/**
	 * Find.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	Optional<BeginningMonth> find(String companyId);

}
