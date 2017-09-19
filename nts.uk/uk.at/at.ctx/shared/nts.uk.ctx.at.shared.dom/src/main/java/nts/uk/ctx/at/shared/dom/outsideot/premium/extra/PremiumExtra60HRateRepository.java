/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.outsideot.premium.extra;

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
	
	
	/**
	 * Save all.
	 *
	 * @param premiumExtras the premium extras
	 * @param companyId the company id
	 */
	public void saveAll(List<PremiumExtra60HRate> premiumExtras, String companyId);
}
