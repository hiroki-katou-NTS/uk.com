/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.service;

import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRate;

/**
 * The Interface UnemployeeInsuranceRateService.
 */
public interface PensionRateService {

	/**
	 * Validate required item.
	 *
	 * @param rate the rate
	 */
	void validateRequiredItem(PensionRate rate);

	/**
	 * Validate date range.
	 *
	 * @param rate the rate
	 */
	void validateDateRange(PensionRate rate);

}
