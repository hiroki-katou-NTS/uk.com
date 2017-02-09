/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.healthrate.service;

import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRate;

/**
 * The Interface UnemployeeInsuranceRateService.
 */
public interface HealthInsuranceRateService {

	/**
	 * Validate required item.
	 *
	 * @param rate the rate
	 */
	void validateRequiredItem(HealthInsuranceRate rate);

	/**
	 * Validate date range.
	 *
	 * @param rate the rate
	 */
	void validateDateRange(HealthInsuranceRate rate);

}
