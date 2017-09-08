/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.overtime.premium.extra;

import java.util.List;

/**
 * The Interface PremiumExtra60HRateRepository.
 */
public interface PremiumExtra60HRateRepository {

	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	public List<PremiumExtra60HRate> findAll(String companyId);
}
